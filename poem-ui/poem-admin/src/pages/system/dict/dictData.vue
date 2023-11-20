<template>
  <search v-model:options="searchOptions" @submit="searchSubmit(PagePoemDictDataParams)" @reset="searchRest"></search>
  <div class="dictDataManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'dict:add'">{{ $t('dictData.button.add') }}</t-button>
      </div>
      <t-table stripe :data="dictData" :columns="columns" row-key="dictCode" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="dictDataFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ dictDataFromTitle }}</template>
      <dictDataFrom ref="dictDataFromRef" @submit-hook="onSubmitHook"></dictDataFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { dictDataPage, PoemDictDataRemove } from '@/api/system/dict';
import { PagePoemDictData, PoemDictData } from '@/api/system/dict/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import { status } from '@/constants';
import dictDataFrom from './components/dictDataFrom.vue'
import { useRoute } from 'vue-router';
import i18n from '@/i18n';

const route = useRoute();
const PagePoemDictDataParams = ref<PagePoemDictData>({
  dictType: '',
  status: undefined,
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemDictData>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'dictLabel',
    title: () => i18n.global.t('dictData.attribute.dictLabel'),
    minWidth: 100,
  },
  {
    colKey: 'dictValue',
    title: () => i18n.global.t('dictData.attribute.dictValue'),
    minWidth: 100,
  },
  {
    colKey: 'status',
    title: () => i18n.global.t('common.label.status'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          status[row.status]
        }
      </div>
    ),
  },
  {
    colKey: 'remark',
    title: () => i18n.global.t('common.attribute.describe'),
    ellipsis: {
      theme: 'light',
      placement: 'bottom',
    },
    minWidth: 100,
    cell: (h, { row }) => (
      <t-space>{row.remark}</t-space>
    )
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: () => i18n.global.t('common.operate'),
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          useAuth('dict:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('dict:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];

const dictData = ref<PoemDictData[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const dictDataFromTitle = ref('');
const dictDataFromRef = ref();
//控制字典数据表单dialog是否显示
const dictDataFromVisible = ref(false)

const settingStore = useSettingStore();

/**
 * 添加字典数据表单适配器
 */
const onAddHander = () => {
  dictDataFromTitle.value = i18n.global.t('dict.label.add')
  dictDataFromRef.value.initFromData(undefined, PagePoemDictDataParams.value.dictType)
  dictDataFromVisible.value = true
}

/**
 * 修改字典数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemDictData) => {
  dictDataFromTitle.value = i18n.global.t('dict.label.edit')
  dictDataFromRef.value.initFromData(row.dictCode, row.dictType)
  dictDataFromVisible.value = true
}

/**
 * 删除字典数据
 * @param row 
 */
const onDelHander = async (row: PoemDictData) => {
  const { code } = await PoemDictDataRemove(row.dictCode)
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
  PagePoemDictDataParams.value.pageNumber = pageInfo.current;
  PagePoemDictDataParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async (params?: {}) => {
  tableLoading.value = true;
  const { code, result, message } = await dictDataPage({ ...PagePoemDictDataParams.value, ...params })
  if (code === ResultEnum.SUCCESS) {
    dictData.value = result.records
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
  dictDataFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
  {
    name: 'dictLabel',
    value: '',
    label: () => i18n.global.t('dictData.attribute.dictLabel'),
    type: 'input',
    placeholder: computed(() => i18n.global.t('dictData.label.pl.dictLabel')),
  },
  {
    name: 'dictType',
    value: '',
    label: () => i18n.global.t('dict.label.dictType'),
    type: 'input',
    placeholder: computed(() => i18n.global.t('dict.label.pl.dictType')),
  },
  {
    name: 'status',
    value: '',
    label: () => i18n.global.t('common.label.status'),
    type: 'select',
    placeholder: computed(() => i18n.global.t('dict.label.pl.status')),
    radioOptions: status
  },
])

const querydictType = () => {
  const dictType = route.query.dictType as string
  PagePoemDictDataParams.value.dictType = dictType
}

onMounted(async () => {
  querydictType()
  loadData();
});

const searchSubmit = (params: any) => {
  console.log(params)
  loadData(params)
}

const searchRest = () => {
  querydictType()
}
</script>
<style scoped lang="less">
.dictDataManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>