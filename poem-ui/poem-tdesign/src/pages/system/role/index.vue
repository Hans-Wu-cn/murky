<template>
  <div>

    <t-form ref="form" :data="PageRoleParams" reset-type="initial" colon @reset="onReset" @submit="loadData">
      <t-space>
        <t-form-item label=" 角色名" name="roleName" initial-data="TDesign">
          <t-input v-model="PageRoleParams.roleName" placeholder="请输入内容" />
        </t-form-item>
        <t-form-item label="角色code" name="roleCode" initial-data="TDesign">
          <t-input v-model="PageRoleParams.roleCode" placeholder="请输入内容" />
        </t-form-item>
        <t-button theme="primary" type="submit">搜索</t-button>
        <t-button theme="default" variant="base" type="reset">重置</t-button>

      </t-space>

    </t-form>
    <t-table :data="data" :columns="columns" :row-key="rowKey" :loading="tableLoading" :pagination="pagination"
      :selected-row-keys="selectedRowKeys" stripe @change="rehandleChange" @page-change="onPageChange"
      @select-change="onSelectChange" />
    <roleFrom ref="roleFromRef" :title="roleFromTitle"></roleFrom>
  </div>
</template>
<script setup lang="tsx">
import { ErrorCircleFilledIcon, CheckCircleFilledIcon, CloseCircleFilledIcon } from 'tdesign-icons-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { rolePage } from '@/api/role';
import { PageRole, PoemRole } from '@/api/role/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import roleFrom from './compoments/roleFrom.vue'

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
    cell: (h, { row, rowIndex }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            编辑
          </t-link>
        </t-space>
      </div>
    ),
  },
];

const data = ref<PoemRole[]>([]);
//表格loading标记
const tableLoading = ref(false);
const roleFromTitle = ref('');
const selectedRowKeys = ref([]);
const roleFromRef = ref();

const onEditHander = (row: PoemRole) => {
  const { showDialog } = roleFromRef.value;
  console.log(showDialog)
  showDialog()
}


/**
 * 重置搜索框
 */
const onReset = () => {
};


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
const loadData = () => {
  tableLoading.value = true;
  rolePage(PageRoleParams).then(({ code, result }) => {
    if (code === ResultEnum.SUCCESS) {
      result.records.forEach(item => {
        data.value.push(item);
      })
      pagination.total = Number(result.totalRow)
    }
    tableLoading.value = false;
  });

}

onMounted(async () => {
  loadData();
});

const onSelectChange = (value: never[], params: any) => {
  selectedRowKeys.value = value;
  console.log(value, params);
};

const rowKey = 'phone';
</script>
<style lang="less">
.tdesign-table-demo__table-operations .t-link {
  padding: 0 8px;
}
</style>