<template>
  <search v-model:options="searchOptions" @submit="searchSubmit" @reset="searchReset"></search>
  <t-card class="dept" :bordered="false">
    <div>
      <t-button @click="onAddHander('0')" v-auth="['dept:add']">{{ $t('dept.button.add') }}</t-button>
    </div>
    <!-- 第一列展开树结点，缩进为 24px，子节点字段 childrenKey 默认为 children -->
    <!-- !!! 树形结构 EnhancedTable 才支持，普通 Table 不支持 !!! -->
    <t-enhanced-table stripe :loading="tableLoading" ref="tableRef" row-key="deptId" drag-sort="row-handler"
      :table-layout="'auto'" :data="deptList" :columns="columns" :tree="treeConfig" :before-drag-sort="beforeDragSort"
      @abnormal-drag-sort="onAbnormalDragSort" @drag-sort="onDragSort" @tree-expand-change="onTreeExpandChange" />
    <t-dialog v-model:visible="visible" :footer="false" width="500px">
      <template #header>{{ deptFromTitle }}</template>
      <deptFrom ref="deptFromRef" @submit-hook="onSubmitHook"></deptFrom>
    </t-dialog>
  </t-card>
</template>
<script setup lang="tsx">
import {
  AddRectangleIcon,
  MinusRectangleIcon,
  MoveIcon,
} from 'tdesign-icons-vue-next';
import { EnhancedTable as TEnhancedTable, MessagePlugin, PrimaryTableCol, DragSortContext, TableTreeExpandChangeContext, TableAbnormalDragSortContext } from 'tdesign-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';
import { dropDept, getDeptList, removeDept } from '@/api/system/dept';
import { ResultEnum } from '@/enums/httpEnum';
import { useRouter } from 'vue-router';
import deptFrom from './compoments/deptFrom.vue';
import { PoemDept, PoemDeptTree } from '@/api/system/dept/types';
import { useSettingStore } from '@/store';
import { hasAuth, useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const router = useRouter();
const tableData = ref<PoemDeptTree[]>();
const tableRef = ref();
//菜单loading标记
const tableLoading = ref(false);
//菜单列表数据
const deptList = ref([]);
const lazyLoadingData = ref(false);
const treeConfig = reactive({ childrenKey: 'children', treeNodeColumnIndex: 1, indent: 50 });
//表格字段
const columns: Array<PrimaryTableCol<any>> = [
  {
    // 列拖拽排序必要参数
    colKey: 'drag',
    title: () => i18n.global.t('common.attribute.sort'),
    fixed: 'left',
    cell: (_h) => <MoveIcon />,
    minWidth: 60,
  },
  {
    colKey: 'deptName',
    title: () => i18n.global.t('dept.attribute.deptName'),
    minWidth: 200,
  },
  {
    colKey: 'sort',
    title: () => i18n.global.t('common.attribute.sort'),
    minWidth: 80,
  },
  {
    colKey: 'operate',
    title: () => i18n.global.t('common.operate'),
    fixed: 'right',
    minWidth: 250,
    // 增、删、改、查 等操作
    cell: (h, { row, rowIndex }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          {
            useAuth('dept:add', <t-link theme="primary" variant="text" hover="color" onClick={() => onAddHander(row.deptId)}>
              {i18n.global.t('dept.button.addsub')}
            </t-link>)
          }
          {
            useAuth('dept:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHandler(row)}>
              {i18n.global.t('common.button.edit')}
            </t-link>)
          }
          {
            (hasAuth('dept:remove') && !row.children?.length) ? <t-popconfirm content="确认删除吗" onConfirm={() => onDeleteHandler(row)}>
              <t-link variant="text" hover="color" theme="danger">
                {i18n.global.t('common.button.delete')}
              </t-link>
            </t-popconfirm> : null
          }

        </t-space>
      </div>
    ),
  }
];
// 查询组件字段
const searchOptions = ref<SearchOption[]>([
  {
    name: 'deptName',
    value: '',
    label: () => i18n.global.t('dept.label.name'),
    type: 'input',
    placeholder: computed(() => i18n.global.t('dept.label.pl.name')),
  }
])
const visible = ref(false);
const deptFromTitle = ref('');
const deptFromRef = ref();

/**
 * 搜索框提交事件
 */
const searchSubmit = (params: any) => {
  tableLoading.value = true;
  const { deptName } = params;
  console.log(params)
  const data = tableData.value.filter(item => {
    return item.deptName.includes(deptName)
  })
  tableRef.value.resetData(data)
  tableLoading.value = false;
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
  const data: PoemDeptTree[] = [];
  const { code, result } = await getDeptList();

  if (ResultEnum.SUCCESS === code) {
    result.forEach((item) => {
      data.push(item);
    });
  }
  tableData.value = data;
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
 * 添加角色表单适配器
 */
const onAddHander = (parentDept: string) => {
  deptFromTitle.value = '添加部门'
  console.log(parentDept)
  deptFromRef.value.initFromData(undefined, parentDept)
  visible.value = true
}

/**
* 跳转至表单页面
* @param row 当前行的菜单对象
*/
const onEditHandler = async (row: PoemDept) => {
  deptFromTitle.value = '编辑部门'
  deptFromRef.value.initFromData(row.deptId)
  visible.value = true
};

/**
* 删除菜单
* @param row 当前行的菜单对象
*/
const onDeleteHandler = async (row: PoemDept) => {
  const { code } = await removeDept(row.deptId);
  if (ResultEnum.SUCCESS === code) {
    tableRef.value.remove(row.deptId);
    MessagePlugin.success('删除成功');
  }
};

/**
 * 新增/修改成功后的回调事件
 */
const onSubmitHook = () => {
  visible.value = false
  resetData();
}

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

/**
 * 拖动部门事件
 * @param params 
 */
const onDragSort = async (params: DragSortContext<T>) => {
  if (errDragCode.value) {
    errDragCode.value = ''
    return false
  }
  const currentRowParentId = params.target.parentDept
  const menuIds: string[] = params.newData.filter(val => {
    return val.parentDept === currentRowParentId
  }).map(val => val.deptId)
  const { code } = await dropDept(menuIds)
  if (code === 200) {
    MessagePlugin.success('调整顺序成功！');
    return;
  }
  resetData();
};

onMounted(async () => {
  resetData();
});
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
</script>
<style scoped lang="less">
.tdesign-table-demo__table-operations .t-link {
  padding: 0 8px;
}

.menuTable {
  margin-top: 20px;
}

.dept {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  >:deep(.t-card__body) {
    flex: 1;
  }
}
</style>