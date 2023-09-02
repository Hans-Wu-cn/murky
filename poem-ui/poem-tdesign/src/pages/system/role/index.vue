<template>
  <div class="roleManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="handleAdd">添加角色</t-button>
      </div>
      <t-table :data="roleData" :columns="columns" :row-key="rowKey" :loading="tableLoading" :pagination="pagination"
        :selected-row-keys="selectedRowKeys" stripe @change="rehandleChange" @page-change="onPageChange"
        @select-change="onSelectChange" />
    </t-card>
    <t-dialog v-model:visible="visible" :footer="false" width="500px">
      <template #header>角色</template>
        <roleFrom ref="roleFromRef" :title="roleFromTitle" @refresh="refresh"></roleFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, nextTick, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { delPoemRole, rolePage } from '@/api/role';
import { PageRole, PoemRole } from '@/api/role/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import roleFrom from './compoments/roleFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';

const PageRoleParams: PageRole = reactive({
  roleName: '',
  roleCode: '',
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  current: PageRoleParams.pageNumber,
  pageSize: PageRoleParams.pageSize,
  total: 0
})
//表格字段
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
          <t-link theme="primary" variant="text" hover="color" onClick={() => onDelHander(row)}>
            删除
          </t-link>
        </t-space>
      </div>
    ),
  },
];

const roleData = ref<PoemRole[]>([]);
//表格loading标记
const tableLoading = ref(false);
const roleFromTitle = ref('');
const selectedRowKeys = ref([]);
const roleFromRef = ref();

const currentRow = ref({})
const onEditHander = (row:PoemRole) => {
  visible.value = true
  nextTick(()=>{
    roleFromRef.value.formData = row
  })
}

// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.log('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  pagination.current = pageInfo.current;
  pagination.pageSize = pageInfo.pageSize;
};

/**
 * 加载表格数据
 */
const loadData = async() => {
  tableLoading.value = true;
  const { code, result,message } = await rolePage(PageRoleParams)
  if (code === ResultEnum.SUCCESS) {
    roleData.value = result.records
    pagination.total = Number(result.totalRow)
  } else {
    MessagePlugin.error(message);
  }
  tableLoading.value = false;
}

onMounted(async () => {
  loadData();
});

const onSelectChange = (value: never[], params: any) => {
  selectedRowKeys.value = value;
  console.log(value, params);
};

const rowKey = 'phone';
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 添加角色
const visible = ref(false)
const handleAdd = ()=>{
  visible.value = true
}

// 删除
const onDelHander = async(row:PoemRole)=>{
  const {code,result} = await delPoemRole(row)
  if(code === ResultEnum.SUCCESS){
    MessagePlugin.success('删除成功');
  }
}

// 刷新
const refresh = ()=>{
  loadData()
}
</script>
<style scoped lang="less">
.roleManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;
  .t-card{
    width: 100%;
  }
}
</style>