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
import { userPage } from '@/api/users'
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
  },
];
// 用户列表条件
const userQuery = ref()

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
  return data;
}

/**
* load tree data
*/
const resetData = async () => {
  // 需要更新数据地址空间
  tableLoading.value = true;
  deptData.value = await getData()
  tableLoading.value = false;
};

const deptTreeNodeClick = (context: { node: TreeNodeModel<PoemDeptTree>; e: MouseEvent }) => {
  console.log(context)
}

const onPageChange = (pageInfo: PaginationProps) => {

}

onMounted(async () => {
  resetData();
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