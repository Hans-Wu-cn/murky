<template>
    <div class="menuTable">
        <!-- 第一列展开树结点，缩进为 24px，子节点字段 childrenKey 默认为 children -->
        <!-- !!! 树形结构 EnhancedTable 才支持，普通 Table 不支持 !!! -->
        <t-enhanced-table stripe :loading="tableLoading" ref="tableRef" row-key="menuId" drag-sort="row-handler"
            :table-layout="'auto'" :data="menuList" :columns="columns" :tree="treeConfig"
            :tree-expand-and-fold-icon="treeExpandIcon" :before-drag-sort="beforeDragSort"
            @abnormal-drag-sort="onAbnormalDragSort" @drag-sort="onDragSort" @tree-expand-change="onTreeExpandChange" />
    </div>
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
import { delMenu, dragMenu, getMenuList } from '@/api/menu';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemMenu } from '@/api/menu/types';
import { menuConfig } from './config';
import { useRouter } from 'vue-router';
import menuFrom from './menuFrom.vue';

const router = useRouter();
const tableRef = ref();
//菜单loading标记
const tableLoading = ref(false);
//菜单列表数据
const menuList = ref([]);
const lazyLoadingData = ref(null);
const treeConfig = reactive({ childrenKey: 'children', treeNodeColumnIndex: 1, indent: 50 });
//表格字段
const columns: Array<PrimaryTableCol<any>> = [
    {
        // 列拖拽排序必要参数
        colKey: 'drag',
        title: '排序',
        fixed: 'left',
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        cell: (_h) => <MoveIcon />,
        minWidth: 60,
    },
    {
        colKey: 'label',
        title: '标题',
        minWidth: 200,
    },
    {
        colKey: 'icon',
        title: '图标',
        minWidth: 80,
    },
    {
        colKey: 'path',
        title: '路径',
        minWidth: 100,
    },
    {
        colKey: 'component',
        title: '组件',
        minWidth: 100,
    },
    {
        colKey: 'auth',
        title: '权限码',
        minWidth: 100,
    },
    {
        colKey: 'operate',
        title: '操作',
        fixed: 'right',
        minWidth: 250,
        // 增、删、改、查 等操作
        cell: (h, { row, rowIndex }) => (
            <div class="tdesign-table-demo__table-operations">
                <t-space>
                    <t-link theme="primary" variant="text" hover="color" onClick={() => onAddClick(row)}>
                        新增子菜单
                    </t-link>
                    <t-link theme="primary" variant="text" hover="color" onClick={() => onEditClick(row)}>
                        编辑
                    </t-link>
                    <t-link theme="primary" variant="text" hover="color" onClick={() => onLookUp(row)}>
                        查看
                    </t-link>
                    {
                        row.children?.length ? '' : <t-popconfirm content="确认删除吗" onConfirm={() => onDeleteClick(row)}>
                            <t-link variant="text" hover="color" theme="danger">
                                删除
                            </t-link>
                        </t-popconfirm>
                    }

                </t-space>
            </div>
        ),
    }
];

/**
 * 加载列表数据
 */
const getData = async () => {
    const data: PoemMenu[] = [];
    const { code, result } = await getMenuList();

    if (ResultEnum.SUCCESS === code) {
        return result
    }
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
    tableRef.value.expandAll();
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
    // router.push(`${menuConfig.menuFromUrl}?parentMenuId=${row.menuId}`);
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
    // router.push(menuConfig.menuFromUrl + '?poemId=' + row.menuId);
};

// 表单提交成功页面
const submit = ()=>{
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
        MessagePlugin.success('删除成功');
    }
};

/**
 * 菜单详情
 * @param row 当前行的菜单对象
 */
const onLookUp = (row: PoemMenu) => {
    router.push(menuConfig.detailUrl + '?poemId=' + row.menuId)
};

const onTreeExpandChange = (context: TableTreeExpandChangeContext<T>) => {
    console.log(context.rowState.expanded ? '展开' : '收起', context);
    /**
     * 如果是懒加载，请确认自己完成了以下几个步骤
     * 1. 提前设置 children 值为 true；
     * 2. 在 onTreeExpandChange 事件中处理异步数据；
     * 3. 自定义展开图标渲染 lazyLoadingTreeIconRender
     */
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
        MessagePlugin.warning('不同层级的元素，不允许调整顺序');
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
        MessagePlugin.success('调整顺序成功！');
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
</style>