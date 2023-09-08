<template>
  <div class="roleManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander">添加角色</t-button>
      </div>
      <t-table stripe :data="roleData" :columns="columns" row-key="roleId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="visible" :footer="false" width="500px">
      <template #header>{{ roleFromTitle }}</template>
      <roleFrom ref="roleFromRef" @submit-hook="onSubmitHook"></roleFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { delPoemRole, rolePage } from '@/api/role';
import { PageRole, PoemRole } from '@/api/role/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import roleFrom from './compoments/roleFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';

const PageRoleParams = ref<PageRole>({
  roleName: '',
  roleCode: '',
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemRole>> = [
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
  {
    colKey: 'roleName',
    title: '角色名',
    minWidth: 100,
  },
  {
    colKey: 'roleCode',
    title: '角色码',
    minWidth: 100,
  },
  {
    colKey: 'describe',
    title: '描述',
    minWidth: 100,
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: '操作',
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            编辑
          </t-link>
          <t-popconfirm content="确认删除吗？" onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              删除
            </t-link>
          </t-popconfirm>
        </t-space>
      </div>
    ),
  },
];

const roleData = ref<PoemRole[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const roleFromTitle = ref('');
const roleFromRef = ref();

const currentRow = ref({})
//控制dialog是否显示
const visible = ref(false)
const settingStore = useSettingStore();

/**
 * 添加角色表单适配器
 */
const onAddHander = () => {
  roleFromTitle.value = '添加角色'
  roleFromRef.value.initFromData()
  visible.value = true
}

/**
 * 修改角色表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemRole) => {
  roleFromTitle.value = '编辑角色'
  roleFromRef.value.initFromData(row.roleId)
  visible.value = true
}

/**
 * 删除角色
 * @param row 
 */
const onDelHander = async (row: PoemRole) => {
  const { code } = await delPoemRole(row)
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success('删除成功');
    loadData();
  }
}

// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.log('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  PageRoleParams.value.pageNumber = pageInfo.current;
  PageRoleParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async () => {
  tableLoading.value = true;
  const { code, result, message } = await rolePage(PageRoleParams.value)
  if (code === ResultEnum.SUCCESS) {
    roleData.value = result.records
    pagination.total = +result.totalRow
  } else {
    MessagePlugin.error(message);
  }
  tableLoading.value = false;
}

/**
 * 新增/修改成功后的回调事件
 */
const onSubmitHook = () => {
  visible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

onMounted(async () => {
  loadData();
});

</script>
<style scoped lang="less">
.roleManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>