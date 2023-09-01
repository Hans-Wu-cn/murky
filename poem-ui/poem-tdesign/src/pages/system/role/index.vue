<template>
  <div>

    <t-form ref="form" :data="PageRoleParams" :reset-type="resetType" colon @reset="onReset">
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
      :selected-row-keys="selectedRowKeys" bordered stripe @change="rehandleChange" @page-change="onPageChange"
      @select-change="onSelectChange" />
  </div>
</template>
<script setup lang="tsx">
import { ErrorCircleFilledIcon, CheckCircleFilledIcon, CloseCircleFilledIcon } from 'tdesign-icons-vue-next';
import { ref, reactive, onMounted } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { rolePage } from '@/api/role';
import { PageRole, PoemRole } from '@/api/role/types';
const statusNameListMap = {
  0: { label: '审批通过', theme: 'success', icon: <CheckCircleFilledIcon /> },
  1: { label: '审批失败', theme: 'danger', icon: <CloseCircleFilledIcon /> },
  2: { label: '审批过期', theme: 'warning', icon: <ErrorCircleFilledIcon /> },
};

const resetType = ref('initial');

const PageRoleParams: PageRole = reactive({
  roleName: '',
  roleCode: '',
})
const columns = [
  {
    colKey: 'serial-number',
  },
  {
    colKey: 'row-select',
    type: 'multiple',
    width: 46,
  },
  {
    colKey: 'name',
    title: '姓名',
    render(h: any, { type, row: { name } }: any) {
      if (type === 'title') return '申请人';
      return name ? `${name.first} ${name.last}` : 'UNKNOWN_USER';
    },
  },
  {
    colKey: 'status',
    title: '申请状态',
    cell: (h: any, { _row, rowIndex }: any) => {
      const status = rowIndex % 3;
      return (
        <t-tag shape="round" theme={statusNameListMap[status].theme} variant="light-outline">
          {statusNameListMap[status].icon}
          {statusNameListMap[status].label}
        </t-tag>
      );
    },
  },
  {
    colKey: 'phone',
    title: '联系方式',
    render(h: any, { row: { phone } }: any) {
      return phone;
    },
  },
  {
    colKey: 'email',
    title: '邮箱',
    ellipsis: true,
  },
];

const data = ref(Array<PoemRole>);
const tableLoading = ref(false);
const selectedRowKeys = ref([]);

const pagination = ref({
  defaultPageSize: 20,
  total: 100,
  defaultCurrent: 1,
});

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
const onPageChange = async (pageInfo: any) => {
  console.log('page-change', pageInfo);
  // pagination.current = pageInfo.current;
  // pagination.pageSize = pageInfo.pageSize;
};

const loadData = () => {
  tableLoading.value = true;
  rolePage(PageRoleParams).then(({ code, result }) => {
    if (code === ResultEnum.SUCCESS) {
      result.records.forEach(item => {
        data.push(item);
      })
    }
    tableLoading.value = false;
  });

}

onMounted(async () => {
  // await fetchData({
  //   current: pagination.value.current || pagination.value.defaultCurrent,
  //   pageSize: pagination.value.pageSize || pagination.value.defaultPageSize,
  // });
  const res = await rolePage(PageRoleParams);
  console.log(res);
});

const onSelectChange = (value: never[], params: any) => {
  selectedRowKeys.value = value;
  console.log(value, params);
};

const rowKey = 'phone';
</script>
