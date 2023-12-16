<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="dictManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'dict:add'">{{ $t('dict.button.add') }}</t-button>
        <t-button @click="refershDict" v-auth="['dict:add', 'dict:edit', 'dict:remove']">
          <template #icon><Refresh-icon /></template>
          {{ $t('common.button.refreshCache') }}</t-button>
      </div>
      <t-table stripe :data="dictTypeData" :columns="columns" row-key="dictTypeId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="dictTypeFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ dictTypeFromTitle }}</template>
      <dictTypeFrom ref="dictTypeFromRef" @submit-hook="onSubmitHook"></dictTypeFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { dictTypePage, dictTypeRemove, refershDict } from '@/api/system/dict';
import { DictType, PageDictType } from '@/api/system/dict/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import { status, tableStatus } from '@/constants';
import dictTypeFrom from './components/dictTypeFrom.vue'
import { dictConfig } from './config';
import { useRoute, useRouter } from 'vue-router';
import {
  RefreshIcon,
} from 'tdesign-icons-vue-next';
import i18n from '@/i18n';
const route = useRoute();
const router = useRouter();
const PageDictTypeParams = ref<PageDictType>({
  dictName: '',
  dictType: '',
  status: 0,
  pageNumber: 1,
  pageSize: 10
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<DictType>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'dictName',
    title: () => i18n.global.t('dict.label.dictName'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          i18n.global.t(row.dictName)
        }
      </div>
    ),
  },
  {
    colKey: 'dictType',
    title: () => i18n.global.t('dict.label.dictType'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <t-space>
        {
          <t-link theme="primary" variant="text" hover="color" onClick={() => onDictData(row)}>
            {row.dictType}
          </t-link>
        }
      </t-space>
    )
  },
  {
    colKey: 'status',
    title: () => i18n.global.t('common.label.status'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          tableStatus[row.status]()
        }
      </div>
    ),
  },
  {
    colKey: 'remark',
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

const dictTypeData = ref<DictType[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const dictTypeFromTitle = ref('');
const dictTypeFromRef = ref();
//控制字典数据表单dialog是否显示
const dictTypeFromVisible = ref(false)

const settingStore = useSettingStore();

/**
 * 添加字典数据表单适配器
 */
const onAddHander = () => {
  dictTypeFromTitle.value = i18n.global.t('dict.label.add')
  dictTypeFromRef.value.initFromData()
  dictTypeFromVisible.value = true
}

/**
 * 修改字典数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: DictType) => {
  dictTypeFromTitle.value = i18n.global.t('dict.label.edit')
  dictTypeFromRef.value.initFromData(row.dictTypeId)
  dictTypeFromVisible.value = true
}

/**
 * 删除字典数据
 * @param row 
 */
const onDelHander = async (row: DictType) => {
  const { code } = await dictTypeRemove(row.dictTypeId)
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success(i18n.global.t('common.messages.deleteSuccess'));
    loadData();
  }
}

/**
 * 查询字典分类下对应得字典数据
 * @param row 
 */
const onDictData = (row: DictType) => {
  // router.push(`${dictConfig.dictUrl}?dictTypeId=${row.dictTypeId}`)
  router.push({
    name: dictConfig.dictData,
    query: {
      dictType: row.dictType
    }
  })

}

// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.debug('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  PageDictTypeParams.value.pageNumber = pageInfo.current;
  PageDictTypeParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async () => {
  tableLoading.value = true;
  const { code, result, message } = await dictTypePage({ ...PageDictTypeParams.value })
  if (code === ResultEnum.SUCCESS) {
    dictTypeData.value = result.records
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
  dictTypeFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
  {
    name: 'dictName',
    value: '',
    label: computed(() => i18n.global.t('dict.label.dictName')),
    type: 'input',
    placeholder: computed(() => i18n.global.t('dict.label.pl.dictName')),
  },
  {
    name: 'dictType',
    value: '',
    label: computed(() => i18n.global.t('dict.label.dictType')),
    type: 'input',
    placeholder: computed(() => i18n.global.t('dict.label.pl.dictType')),
  },
  {
    name: 'status',
    value: '',
    label: computed(() => i18n.global.t('common.label.status')),
    type: 'select',
    placeholder: computed(() => i18n.global.t('dict.label.pl.status')),
    radioOptions: status
  },
])

onMounted(async () => {
  loadData();
});

const searchSubmit = (params: any) => {
  console.debug(params)
  PageDictTypeParams.value.dictName = params.dictName
  PageDictTypeParams.value.dictType = params.dictType
  PageDictTypeParams.value.status = params.status
  loadData()
}
</script>
<style scoped lang="less">
.dictManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>