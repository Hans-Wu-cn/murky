<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="systemParameterManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'systemParameter:add'">{{ $t('systemParameter.button.add') }}</t-button>
        <t-button @click="refresh" v-auth="['systemParameter:add', 'systemParameter:edit', 'systemParameter:remove']">
          <template #icon><Refresh-icon /></template>
          {{ $t('common.button.refreshCache') }}</t-button>
      </div>
      <t-table stripe :data="roleData" :columns="columns" row-key="roleId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="systemParameteFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ systemParameteFromTitle }}</template>
      <systemParamete ref="systemParameteFromRef" @submit-hook="onSubmitHook"></systemParamete>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { refreshCache, removeSystemParameter, systemParameterPage } from '@/api/systemSetting/systemParameter';
import { SystemParameter, SystemParameterPageParams } from '@/api/systemSetting/systemParameter/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import systemParamete from './components/systemParameterFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import {
  RefreshIcon,
} from 'tdesign-icons-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const page = ref<SystemParameterPageParams>({
  pageNumber: 1,
  pageSize: 10,
  value: undefined
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<SystemParameter>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'key',
    title: () => i18n.global.t('systemParameter.attribute.key'),
    minWidth: 100,
  },
  {
    colKey: 'value',
    title: () => i18n.global.t('systemParameter.attribute.value'),
    minWidth: 100,
    ellipsis: {
      theme: 'light',
      placement: 'bottom',
    },
  },
  {
    colKey: 'describe',
    title: () => i18n.global.t('common.attribute.describe'),
    minWidth: 100,
    ellipsis: {
      theme: 'light',
      placement: 'bottom',
    },
  },
  {
    colKey: 'createTime',
    title: () => i18n.global.t('common.attribute.createTime'),
    minWidth: 100,
  },
  {
    colKey: 'updateTime',
    title: () => i18n.global.t('common.attribute.updateTime'),
    minWidth: 100,
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: () => i18n.global.t('common.operate'),
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          {
            useAuth('systemParameter:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
              {i18n.global.t('common.button.edit')}
            </t-link>)
          }
          {
            useAuth('systemParameter:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
              <t-link variant="text" hover="color" theme="danger">
                {i18n.global.t('common.button.delete')}
              </t-link>
            </t-popconfirm>)
          }
        </t-space>
      </div>
    ),
  },
];

const roleData = ref<SystemParameter[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const systemParameteFromTitle = ref('');
const systemParameteFromRef = ref();
const datascopeRef = ref()
//控制角色表单dialog是否显示
const systemParameteFromVisible = ref(false)

const settingStore = useSettingStore();

const refresh = async () => {
  const { code } = await refreshCache()
  MessagePlugin.success(i18n.global.t('common.message.refreshSuccess'))
}

/**
 * 添加角色表单适配器
 */
const onAddHander = () => {
  systemParameteFromTitle.value = i18n.global.t('systemParameter.button.add')
  systemParameteFromRef.value.initFromData()
  systemParameteFromVisible.value = true
}

/**
 * 修改角色表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: SystemParameter) => {
  systemParameteFromTitle.value = i18n.global.t('systemParameter.label.edit')
  systemParameteFromRef.value.initFromData(row.id)
  systemParameteFromVisible.value = true
}

/**
 * 数据权限表单适配器
 * @param row 当前行数据
 */
const onDatascopeHander = (row: SystemParameter) => {
  datascopeRef.value.initFromData(row.id)
  systemParameteFromVisible.value = true
}

/**
 * 删除角色
 * @param row 
 */
const onDelHander = async (row: SystemParameter) => {
  const { code } = await removeSystemParameter(row.id)
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
  page.value.pageNumber = pageInfo.current;
  page.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async (params?: {}) => {
  tableLoading.value = true;
  const { code, result, message } = await systemParameterPage({ ...page.value, ...params })
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
  systemParameteFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
  {
    name: 'key',
    value: '',
    label: () => i18n.global.t('systemParameter.label.key'),
    placeholder: computed(() => i18n.global.t('systemParameter.label.pl.key')),
    type: 'input',
  },
])

onMounted(async () => {
  loadData();
});

const searchSubmit = (params: any) => {
  console.log(params)
  loadData(params)
}
</script>
<style scoped lang="less">
.systemParameterManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>