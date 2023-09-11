<template>
  <div class="user">
    <t-card :bordered="false" title="部门树">
      <t-tree activeMultiple checkStrictly hover lazy :expandLevel="0" :data="deptData" :keys="deptTreeKeys"
        @click="deptTreeNodeClick" />
    </t-card>
    <t-card :bordered="false" title="用户列表">
      <t-table stripe :data="userData" :columns="columns" row-key="roleId" :loading="tableLoading"
        :pagination="pagination" @page-change="onPageChange" />
    </t-card>
  </div>
</template>
<script setup lang="tsx">
import { useSettingStore } from '@/store';
import { PaginationProps, PrimaryTableCol, TreeNodeModel } from 'tdesign-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';
import { getDeptList } from '@/api/dept';
import { PoemDeptTree } from '@/api/dept/types';
import { ResultEnum } from '@/enums/httpEnum';
import { userPage } from '@/api/user'
import { PageUser } from '@/api/user/types'
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
// 部门数据
const deptData = ref<PoemDeptTree[]>([]);
// 用户列表
const userData = ref([])
// 表格loading标记
const tableLoading = ref(false);
const pagination: PaginationProps = reactive({
  total: 0
})
// 部门树配置
const deptTreeKeys = ref({ value: 'deptId', label: 'deptName', children: 'children' })
// 表格字段
const columns: Array<PrimaryTableCol> = [
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
  {
    colKey: 'userName',
    title: '用户名',
    minWidth: 100,
  },
  {
    colKey: 'account',
    title: '账号',
    minWidth: 100,
  },
  {
    colKey: 'sex',
    title: '性别',
    minWidth: 100,
  },
  {
    colKey: 'email',
    title: '邮箱',
    minWidth: 100,
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: '操作',
  },
];
// 用户列表条件
const userQuery = ref<PageUser>({
  userName: '',
  deptId: '',
  pageNumber: 1,
  pageSize: 10
})

/**
* 加载列表数据
*/
const getdeptTreeData = async () => {
  let data: PoemDeptTree[] = [];
  const { code, result } = await getDeptList();

  if (ResultEnum.SUCCESS === code) {
    data = result
  }
  return data;
}

/**
* load tree data
*/
const loadUserData = async () => {
  // 需要更新数据地址空间
  tableLoading.value = true;
  const { code, result } = await userPage(userQuery.value);
  if (ResultEnum.SUCCESS === code) {
    userData.value = result.records
    pagination.total = +result.totalRow
  }
  tableLoading.value = false;
};

const deptTreeNodeClick = (context: { node: TreeNodeModel<PoemDeptTree>; e: MouseEvent }) => {
  console.log(context)
}

const onPageChange = (pageInfo: PaginationProps) => {
  userQuery.value.pageNumber = pageInfo.current;
  userQuery.value.pageSize = pageInfo.pageSize;
  loadUserData()
}

onMounted(async () => {
  // 需要更新数据地址空间
  deptData.value = await getdeptTreeData()
  await loadUserData();
});
</script>
<style scoped lang="less">
.user {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  >.t-card:first-of-type {
    margin-right: 10px;
    min-width: 200px;
  }
}
</style>