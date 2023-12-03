<template>
    <search v-model:options="searchOptions" @submit="searchSubmit" @reset="searchReset"></search>

    <t-card class="menuTable">
        <slot></slot>
        <!-- 第一列展开树结点，缩进为 24px，子节点字段 childrenKey 默认为 children -->
        <!-- !!! 树形结构 EnhancedTable 才支持，普通 Table 不支持 !!! -->
        <t-enhanced-table stripe :loading="tableLoading" ref="tableRef" row-key="menuId" drag-sort="row-handler"
            :table-layout="'auto'" :data="menuList" :columns="columns" :tree="treeConfig"
            :tree-expand-and-fold-icon="treeExpandIcon" :before-drag-sort="beforeDragSort"
            v-model:expandedTreeNodes="expandedTableTreeNodes" @abnormal-drag-sort="onAbnormalDragSort"
            @drag-sort="onDragSort" @expanded-tree-nodes-change="onExpandedTreeNodesChange">
            <template #icon-slot="{ row }">
                <t-icon v-if="row.iconType" :name="row.icon" />
                <img class="icon-options" v-else @error="row.iconType = true" :src="row.icon" alt="icon">
            </template>
        </t-enhanced-table>
    </t-card>
    <t-dialog v-model:visible="menuVisible" v-if="menuVisible" :footer="false" width="600px" top="10">
        <menuFrom :poemId="poemId" :parentMenuId="parentMenuId" @submit="submit"></menuFrom>
    </t-dialog>
</template>
<script setup lang="tsx">
import {
    AddRectangleIcon,
    MinusRectangleIcon,
    MoveIcon,
} from 'tdesign-icons-vue-next';
import { EnhancedTable as TEnhancedTable, Loading, MessagePlugin, PrimaryTableCol, DragSortContext, TableTreeExpandChangeContext, TableAbnormalDragSortContext } from 'tdesign-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';
import { delMenu, dragMenu, getMenuList } from '@/api/system/menu';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemMenu } from '@/api/system/menu/types';
import { menuConfig } from '../config';
import { useRouter } from 'vue-router';
import menuFrom from './menuFrom.vue';
import { hasAuth, useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const router = useRouter();
const tableRef = ref();
const tableData = ref<PoemMenu[]>();
//菜单loading标记
const tableLoading = ref(false);
//菜单列表数据
const menuList = ref([]);
const lazyLoadingData = ref(null);
const treeConfig = reactive({ childrenKey: 'children', treeNodeColumnIndex: 1, indent: 50 });
//表格字段
const columns: Array<PrimaryTableCol<any>> = [
    useAuth('menu:edit', {
        // 列拖拽排序必要参数
        colKey: 'drag',
        title: () => i18n.global.t('common.attribute.sort'),
        fixed: 'left',
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        cell: (_h: any) => <MoveIcon />,
        minWidth: 60,
    }),
    {
        colKey: 'label',
        title: () => i18n.global.t('menu.label.title'),
        minWidth: 200,
    },
    {
        colKey: 'name',
        title: () => i18n.global.t('menu.label.name'),
        minWidth: 200,
        ellipsis: {
            theme: 'light',
            placement: 'bottom',
        },
    },
    {
        colKey: 'icon',
        cell: 'icon-slot',
        title: () => i18n.global.t('common.icon'),
        minWidth: 80,
    },
    {
        colKey: 'path',
        title: () => i18n.global.t('menu.attribute.path'),
        minWidth: 100,
    },
    {
        colKey: 'component',
        title: () => i18n.global.t('menu.attribute.component'),
        minWidth: 100,
    },
    {
        colKey: 'auth',
        title: () => i18n.global.t('menu.attribute.auth'),
        minWidth: 100,
    },
    {
        colKey: 'operate',
        title: i18n.global.t('common.operate'),
        fixed: 'right',
        minWidth: 250,
        // 增、删、改、查 等操作
        cell: (h, { row, rowIndex }) => (
            <t-space>
                {
                    useAuth('menu:add', <t-link theme="primary" variant="text" hover="color" onClick={() => onAddClick(row)}>
                        {i18n.global.t('menu.button.addSub')}
                    </t-link>)
                }
                {
                    useAuth('menu:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditClick(row)}>
                        {i18n.global.t('common.button.edit')}
                    </t-link>)
                }
                <t-link theme="primary" variant="text" hover="color" onClick={() => onLookUp(row)}>
                    {i18n.global.t('common.button.view')}
                </t-link>
                {
                    (hasAuth('menu:remove') && !row.children?.length) ? <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDeleteClick(row)}>
                        <t-link variant="text" hover="color" theme="danger">
                            {i18n.global.t('common.button.delete')}
                        </t-link>
                    </t-popconfirm> : null
                }

            </t-space>
        ),
    }
];

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
    {
        name: 'label',
        value: '',
        label: computed(() => i18n.global.t('menu.label.title')),
        type: 'input',
        placeholder: computed(() => i18n.global.t('menu.label.pl.title'))
    },
    {
        name: 'name',
        value: '',
        label: computed(() => i18n.global.t('menu.label.name')),
        type: 'input',
        placeholder: computed(() => i18n.global.t('menu.label.pl.name')),
    },
    {
        name: 'path',
        value: '',
        label: computed(() => i18n.global.t('menu.label.path')),
        type: 'input',
        placeholder: computed(() => i18n.global.t('menu.label.pl.path')),
    },
    {
        name: 'auth',
        value: '',
        label: computed(() => i18n.global.t('menu.label.auth')),
        type: 'input',
        placeholder: computed(() => i18n.global.t('menu.label.pl.auth')),
    },

])

/**
 * 搜索框提交事件
 */
const searchSubmit = (params: any) => {
    tableLoading.value = true;
    const { label, name, path, auth } = params;
    const data = recursion(tableData.value, label, name, path, auth)
    tableRef.value.resetData(data)
    tableLoading.value = false;
}

/**
 * 递归过滤数据
 * @param data 需要过滤的数据
 * @param label 菜单标题
 * @param name 菜单名
 * @param path 菜单路径
 * @param auth 菜单权限码
 */
const recursion = (data: PoemMenu[], label: string, name: string, path: string, auth: string): PoemMenu[] => {
    function searchStr(source: string, target: string) {
        if (target && source) {
            debugger
            return source.includes(target)
        }
        return false
    }
    return data.filter(item => {
        debugger
        if (item.children.length > 0) {
            item.children = recursion(item.children, label, name, path, auth)
        }
        return item.children.length > 0 || (searchStr(item.label, label) || searchStr(item.name, name) || searchStr(item.path, path) || item.auth.includes(auth))
    })
}

/**
 * 搜索框重置事件
 */
const searchReset = () => {
    tableLoading.value = true;
    tableRef.value.resetData(tableData.value)
    tableLoading.value = false;
}


/**
 * 加载列表数据
 */
const getData = async () => {
    let data: PoemMenu[] = [];
    const { code, result } = await getMenuList();

    if (ResultEnum.SUCCESS === code) {
        data = result
    }
    tableData.value = data
    return data;
}

/**
 * load tree data
 */
const resetData = async () => {
    // 需要更新数据地址空间
    tableLoading.value = true;
    const res = await getData()
    tableRef.value.resetData(res)
    tableLoading.value = false;
};
/**
 * 打开表单页面
 * @param row 当前行的菜单对象
 */
const menuVisible = ref(false);
const parentMenuId = ref('');
const onAddClick = async (row: PoemMenu) => {
    menuVisible.value = true;
    parentMenuId.value = row.menuId;
    poemId.value = ''
};

/**
 * 跳转至表单页面
 * @param row 当前行的菜单对象
 */
const poemId = ref('')
const onEditClick = async (row: PoemMenu) => {
    menuVisible.value = true;
    parentMenuId.value = '';
    poemId.value = row.menuId
};

// 表单提交成功页面
const submit = () => {
    menuVisible.value = false;
    resetData();
}
/**
 * 删除菜单
 * @param row 当前行的菜单对象
 */
const onDeleteClick = async (row: PoemMenu) => {
    const { code } = await delMenu(row.menuId);
    if (ResultEnum.SUCCESS === code) {
        tableRef.value.remove(row.menuId);
        MessagePlugin.success(i18n.global.t('common.messages.deleteSuccess'));
    }
};

/**
 * 菜单详情
 * @param row 当前行的菜单对象
 */
const onLookUp = (row: PoemMenu) => {
    router.push(menuConfig.detailUrl + '?menuId=' + row.menuId)
};
const expandedTableTreeNodes = ref<Array<string | number>>(['1']); // 存储展开的数据
const onExpandedTreeNodesChange = (expandedTreeNodes: Array<string | number>, context: any) => {
    console.log(expandedTreeNodes, context);
    expandedTableTreeNodes.value = expandedTreeNodes
};
type T = /*unresolved*/ any
// 应用于需要阻止拖拽排序的场景。如：当子节点存在时，则不允许调整顺序。
// 返回值为 true，允许拖拽排序；返回值 为 false，则阻止拖拽排序
const beforeDragSort = (params: DragSortContext<T>) => {
    console.log('beforeDragSort:', params);
    return true;
};

//异常拖拽排序时触发
const errDragCode = ref()
const onAbnormalDragSort = (params: TableAbnormalDragSortContext<T>) => {
    console.log(params);
    errDragCode.value = params.code
    // MessagePlugin.warning(params.reason);
    if (params.code === 1001) {
        MessagePlugin.warning(i18n.global.t('common.label.dragSort.error'));
    }
};

const onDragSort = async (params: DragSortContext<T>) => {
    if (errDragCode.value) {
        errDragCode.value = ''
        return false
    }
    const currentRowParentId = params.target.parentMenuId
    const menuIds: string[] = params.newData.filter(val => {
        return val.parentMenuId === currentRowParentId
    }).map(val => val.menuId)
    const { code } = await dragMenu({
        parentMenuId: currentRowParentId,
        menuIds
    })
    if (code === 200) {
        MessagePlugin.success(i18n.global.t('common.label.dragSort.success'));
    }
    console.log('onDragSort:', params);
};

// 懒加载图标渲染
const lazyLoadingTreeIconRender = (h: any, params: { type: any; row: any; }) => {
    const { type, row } = params;
    if (lazyLoadingData.value && lazyLoadingData.value.id === row?.id) {
        return <Loading size="14px" />;
    }
    return type === 'expand' ? <AddRectangleIcon /> : <MinusRectangleIcon />;
};

const treeExpandIcon = computed(() => {
    return lazyLoadingTreeIconRender;
});

onMounted(async () => {
    resetData();
});
</script>
<style scoped lang="less">
.tdesign-table-demo__table-operations .t-link {
    padding: 0 8px;
}

.menuTable {
    margin-top: 20px;
}

img.icon-options {
    width: 14px;
    height: 14px;
    vertical-align: middle;
}
</style>