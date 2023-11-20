<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="scriptTableManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'saasRole:add'">{{ $t('script.table.button.add') }}</t-button>
      </div>
      <t-table stripe :data="tableData" :columns="columns" row-key="tableId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="tabelScriptFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ tabelScriptFromTitle }}</template>
      <tabelScriptFrom ref="tabelScriptFromRef" @submit-hook="onSubmitHook"></tabelScriptFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { tablePage, tableRemove } from '@/api/saas/script';
import { PoemSaasScriptTable, PoemSaasScriptTablePage } from '@/api/saas/script/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import tabelScriptFrom from './components/tabelScriptFrom.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';
import { status } from '@/constants'
import { dictKey, saasScriptTagDictHook } from '@/hooks/dict';
import { PoemDictData } from '@/api/system/dict/types';
const queryParams = ref<PoemSaasScriptTablePage>({
  tableName: '',
  tag: '',
  status: 0,
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
const scriptTag = ref<Map<string, String>>()
// 表格字段
const columns: Array<PrimaryTableCol<PoemSaasScriptTable>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'tableName',
    title: () => i18n.global.t('script.table.attribute.tableName'),
    minWidth: 100,
  },
  {
    colKey: 'tag',
    title: () => i18n.global.t('script.table.label.tag'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          scriptTag.value.get(row.tag.toString())
        }
      </div>
    ),
  },
  {
    colKey: 'status',
    title: () => i18n.global.t('common.label.status'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          i18n.global.t(status[row.status])
        }
      </div>
    ),
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
    colKey: 'operate',
    minWidth: 340,
    title: () => i18n.global.t('common.operate'),
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          useAuth('saasRole:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('saasRole:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];

const tableData = ref<PoemSaasScriptTable[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const tabelScriptFromTitle = ref('');
const tabelScriptFromRef = ref();
//控制角色表单dialog是否显示
const tabelScriptFromVisible = ref(false)
const settingStore = useSettingStore();

/**
 * 添加
 */
const onAddHander = () => {
  tabelScriptFromTitle.value = i18n.global.t('script.table.label.add')
  tabelScriptFromRef.value.initFromData()
  tabelScriptFromVisible.value = true
}

/**
 * 修改
 * @param row 当前行数据
 */
const onEditHander = (row: PoemSaasScriptTable) => {
  tabelScriptFromTitle.value = i18n.global.t('script.table.label.edit')
  tabelScriptFromRef.value.initFromData(row.tableId)
  tabelScriptFromVisible.value = true
}

/**
 * 删除
 * @param row 
 */
const onDelHander = async (row: PoemSaasScriptTable) => {
  const { code } = await tableRemove(row.tableId)
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
  queryParams.value.pageNumber = pageInfo.current;
  queryParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async (params?: {}) => {
  tableLoading.value = true;
  const { code, result, message } = await tablePage({ ...queryParams.value, ...params })
  if (code === ResultEnum.SUCCESS) {
    tableData.value = result.records
    pagination.total = +result.totalRow
  }
  tableLoading.value = false;
}

/**
 * 新增/修改成功后的回调事件
 */
const onSubmitHook = () => {
  tabelScriptFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

const searchOptions = ref<SearchOption[]>([
  {
    name: 'tableName',
    value: '',
    label: computed(() => i18n.global.t('script.table.label.tableName')),
    placeholder: computed(() => i18n.global.t('script.table.label.pl.tableName')),
    type: 'input',
  },
  {
    name: 'tag',
    label: computed(() => i18n.global.t('script.table.label.tag')),
    placeholder: computed(() => i18n.global.t('script.table.label.pl.tag')),
    type: 'dict',
    dictType: dictKey.saasScriptTag
  },
  {
    name: 'status',
    label: computed(() => i18n.global.t('common.label.status')),
    placeholder: computed(() => i18n.global.t('common.label.pl.status')),
    type: 'select',
    radioOptions: status
  },

])


onMounted(async () => {
  const saasScriptTagDict = await saasScriptTagDictHook()
  scriptTag.value = new Map();
  saasScriptTagDict.forEach(item => {
    scriptTag.value.set(item.dictValue, item.dictLabel)
  })
  loadData();
});

const searchSubmit = (params: any) => {
  loadData(params)
}
</script>
<style scoped lang="less">
.scriptTableManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>