<template>
    <div class="user">
      <t-card :bordered="false" title="部门树">
        <t-tree :data="deptData"
          activeMultiple
          checkable
          checkStrictly
          hover
          label
          lazy />
      </t-card>
      <t-card :bordered="false" title="用户列表">
        <t-table stripe :data="userData" :columns="columns" row-key="roleId" :loading="tableLoading"
          :pagination="pagination" @page-change="onPageChange" />
      </t-card>
    </div>
</template>
<script setup lang="tsx">
import { useSettingStore } from '@/store';
import { PaginationProps, PrimaryTableCol } from 'tdesign-vue-next';
import { computed, reactive, ref } from 'vue';
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
// 部门数据
const deptData = ref([])
// 用户列表
const userData = ref([])
// 表格loading标记
const tableLoading = ref(false);
const pagination: PaginationProps = reactive({
  total: 0
})
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

const onPageChange = (pageInfo: PaginationProps)=>{

}
</script>
<style scoped lang="less">
.user{
    min-height: calc(100% - v-bind(showBreadcrumbHeight));
    display: flex;
  >.t-card:first-of-type{
    margin-right: 10px;
    min-width: 200px;
  }
}
</style>