<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="permissionGroupManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'permissionGroup:add'">{{ $t('permissionGroup.button.add') }}</t-button>
      </div>
      <t-table stripe :data="permissionGroupData" :columns="columns" row-key="groupId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="permissionGroupFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ permissionGroupFromTitle }}</template>
      <permissionGroupFrom ref="permissionGroupFromRef" @submit-hook="onSubmitHook"></permissionGroupFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { delPermissionGroup, permissionGroupPage } from '@/api/tenant/permissionGroup';
import { PagePermissionGroup, PermissionGroup } from '@/api/tenant/permissionGroup/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import permissionGroupFrom from './components/permissionGroupFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const pagePermissionGroupParams = ref<PagePermissionGroup>({
  groupName: '',
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<PermissionGroup>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'groupName',
    title: () => i18n.global.t('permissionGroup.label.name'),
    minWidth: 100,
  },
  {
    colKey: 'describe',
    title: () => i18n.global.t('common.attribute.describe'),
    minWidth: 100,
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: () => i18n.global.t('common.operate'),
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          useAuth('permissionGroup:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('permissionGroup:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];

const permissionGroupData = ref<PermissionGroup[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const permissionGroupFromTitle = ref('');
const permissionGroupFromRef = ref();
//控制权限组表单dialog是否显示
const permissionGroupFromVisible = ref(false)
const settingStore = useSettingStore();

/**
 * 添加权限组表单适配器
 */
const onAddHander = () => {
  permissionGroupFromTitle.value = i18n.global.t('permissionGroup.button.add')
  permissionGroupFromRef.value.initFromData()
  permissionGroupFromVisible.value = true
}

/**
 * 修改权限组表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PermissionGroup) => {
  permissionGroupFromTitle.value = i18n.global.t('permissionGroup.label.edit')
  permissionGroupFromRef.value.initFromData(row.groupId)
  permissionGroupFromVisible.value = true
}

/**
 * 删除权限组
 * @param row 
 */
const onDelHander = async (row: PermissionGroup) => {
  const { code } = await delPermissionGroup(row.groupId)
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success(i18n.global.t('common.messages.deleteSuccess'));
    loadData();
  }
}

// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.debug('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  pagePermissionGroupParams.value.pageNumber = pageInfo.current;
  pagePermissionGroupParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async () => {
  tableLoading.value = true;
  const { code, result, message } = await permissionGroupPage({ ...pagePermissionGroupParams.value })
  if (code === ResultEnum.SUCCESS) {
    permissionGroupData.value = result.records
    pagination.total = +result.totalRow
  }
  tableLoading.value = false;
}

/**
 * 新增/修改成功后的回调事件
 */
const onSubmitHook = () => {
  permissionGroupFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
  {
    name: 'groupName',
    value: '',
    label: computed(() => i18n.global.t('permissionGroup.label.name')),
    placeholder: computed(() => i18n.global.t('permissionGroup.label.pl.name')),
    type: 'input',
  },
])

onMounted(async () => {
  loadData();
});

const searchSubmit = (params: any) => {
  pagePermissionGroupParams.value.groupName = params.groupName
  loadData()
}
</script>
<style scoped lang="less">
.permissionGroupManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>@/api/tenant/permissionGroup@/api/tenant/permissionGroup/types