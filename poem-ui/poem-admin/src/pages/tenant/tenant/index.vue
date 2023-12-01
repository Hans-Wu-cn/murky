<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="permissionGroupManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'tenant:add'">{{ $t('tenant.label.add') }}</t-button>
      </div>
      <t-table stripe :data="tenantData" :columns="columns" row-key="tenantId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="permissionGroupFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ permissionGroupFromTitle }}</template>
      <tenantFrom ref="tenantFromRef" @submit-hook="onSubmitHook"></tenantFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { poemTenantPage, deactivatePoemTenant } from '@/api/tenant/tenant';
import { PagePoemTenant, PoemTenant } from '@/api/tenant/tenant/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import tenantFrom from './components/tenantFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const pagePermissionGroupParams = ref<PagePoemTenant>({
  tenantName: '',
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemTenant>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'tenantName',
    title: () => i18n.global.t('tenant.label.name'),
    minWidth: 100,
  },
  {
    colKey: 'groupName',
    title: () => i18n.global.t('permissionGroup.label.name'),
    minWidth: 100,
  },
  {
    colKey: 'status',
    title: () => i18n.global.t('common.label.status'),
    minWidth: 100,
  },
  {
    colKey: 'createTime',
    title: () => i18n.global.t('common.attribute.createTime'),
    minWidth: 100,
  },
  {
    colKey: 'expires',
    title: () => i18n.global.t('common.attribute.expires'),
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
          useAuth('tenant:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {row.status === 0 ? i18n.global.t('	common.label.status.1') : i18n.global.t('	common.label.status.0')}
          </t-link>)
        }
        {
          useAuth('permissionGroup:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.view')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];

const tenantData = ref<PoemTenant[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const permissionGroupFromTitle = ref('');
const tenantFromRef = ref();
//控制权限组表单dialog是否显示
const permissionGroupFromVisible = ref(false)
const settingStore = useSettingStore();

/**
 * 添加权限组表单适配器
 */
const onAddHander = () => {
  permissionGroupFromTitle.value = i18n.global.t('permissionGroup.button.add')
  tenantFromRef.value.initFromData()
  permissionGroupFromVisible.value = true
}

/**
 * 修改权限组表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemTenant) => {
  permissionGroupFromTitle.value = i18n.global.t('permissionGroup.label.edit')
  tenantFromRef.value.initFromData(row.tenantId)
  permissionGroupFromVisible.value = true
}

/**
 * 删除权限组
 * @param row 
 */
const onDelHander = async (row: PoemTenant) => {
  const { code } = await deactivatePoemTenant(row.tenantId)
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
  const { code, result, message } = await poemTenantPage({ ...pagePermissionGroupParams.value })
  if (code === ResultEnum.SUCCESS) {
    tenantData.value = result.records
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
    name: 'tenantName',
    value: '',
    label: computed(() => i18n.global.t('tenant.label.name')),
    placeholder: computed(() => i18n.global.t('tenant.label.pl.name')),
    type: 'input',
  },
])

onMounted(async () => {
  loadData();
});

const searchSubmit = (params: any) => {
  pagePermissionGroupParams.value.tenantName = params.groupName
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
</style>