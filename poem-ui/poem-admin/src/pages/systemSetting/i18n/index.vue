<template>
  <search v-model:options="searchOptions" @submit="searchSubmit" @reset="searchReset"></search>
  <div class="i18nManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'i18n:add'">{{ $t('i18n.button.add') }}</t-button>
      </div>
      <t-table stripe :data="i18nData" :columns="columns" row-key="dictTypeId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="i18nFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ i18nFromTitle }}</template>
      <i18nFrom ref="i18nFromRef" @submit-hook="onSubmitHook"></i18nFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { i18nPage, removeI18n } from '@/api/systemSetting/i18n';
import { PoemDictData } from '@/api/system/dict/types';
import { I18nPageParams } from '@/api/systemSetting/i18n/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18nFrom from './components/i18nFrom.vue'
import { dictKey, i18nDictHook, i18nTagDictHook } from '@/hooks/dict';
import i18n from '@/i18n'

const PagePoemDictTypeParams = ref<I18nPageParams>({
  i18nTag: '',
  i18nValue: '',
  i18nKey: '',
  pageNumber: 1,
  pageSize: 10
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns = ref<Array<PrimaryTableCol<any>>>([
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'i18nKey',
    title: () => i18n.global.t('i18n.label.code'),
    minWidth: 100,
  },
]);

const i18nData = ref<any[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const i18nFromTitle = ref('');
const i18nFromRef = ref();
//控制语言包数据表单dialog是否显示
const i18nFromVisible = ref(false)
// const i18nDict = ref<PoemDictData[]>();
const i18nTagDict = ref<PoemDictData[]>();
const settingStore = useSettingStore();
const searchi18nTag = ref('')
/**
 * 添加语言包数据表单适配器
 */
const onAddHander = () => {
  i18nFromTitle.value = i18n.global.t('i18n.button.add')
  i18nFromRef.value.initFromData()
  i18nFromVisible.value = true
}

/**
 * 修改语言包数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: any) => {
  console.log(row['i18nKey']);
  i18nFromTitle.value = i18n.global.t('i18n.label.editData')
  i18nFromRef.value.initFromData(row['i18nKey'], row['i18nTag'])
  i18nFromVisible.value = true
}

/**
 * 删除语言包数据
 * @param row 
 */
const onDelHander = async (row: any) => {
  const { code } = await removeI18n(row['i18nKey'])
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success(i18n.global.t('common.messages.deleteSuccess'));
    PagePoemDictTypeParams.value.i18nTag = searchi18nTag.value
    loadData();
  }
}


// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.debug('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  PagePoemDictTypeParams.value.pageNumber = pageInfo.current;
  PagePoemDictTypeParams.value.pageSize = pageInfo.pageSize;
  loadData();

};

/**
 * 加载表格数据
 */
const loadData = async () => {
  tableLoading.value = true;
  const { code, result, message } = await i18nPage({ ...PagePoemDictTypeParams.value })
  if (code === ResultEnum.SUCCESS) {
    i18nData.value = result.records
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
  i18nFromVisible.value = false
  PagePoemDictTypeParams.value.i18nTag = i18nTagDict.value[0].dictValue
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置 该配置从字典加载
const searchOptions = ref<SearchOption[]>()

const searchReset = async () => {
  await getI18ndict();
  PagePoemDictTypeParams.value.i18nTag = i18nTagDict.value[0].dictValue
  loadData();
}

const getI18ndict = async () => {
  const i18ns = await i18nDictHook();
  if (i18ns) {
    // i18nDict.value = i18ns
    searchOptions.value[2].value = i18nTagDict.value[0].dictValue
    loadColumns(i18ns);
  }
}

const getI18nTagdict = async () => {
  const i18nTags = await i18nTagDictHook();
  if (i18nTags) {
    i18nTagDict.value = i18nTags
    PagePoemDictTypeParams.value.i18nTag = i18nTags[0].dictValue
    searchOptions.value = [
      {
        name: 'i18nKey',
        value: '',
        label: () => i18n.global.t('i18n.label.code'),
        type: 'input',
        placeholder: computed(() => i18n.global.t('i18n.label.pl.code')),
      },
      {
        name: 'i18nValue',
        value: '',
        label: () => i18n.global.t('i18n.label.i18nValue'),
        type: 'input',
        placeholder: computed(() => i18n.global.t('i18n.label.pl.i18nValue')),
      },
      {
        name: 'i18nTag',
        label: () => i18n.global.t('i18n.label.tag'),
        type: 'dict',
        placeholder: computed(() => i18n.global.t('i18n.label.pl.tag')),
        dictType: dictKey.i18nTag
      },
    ];
  }
}

// 加载动态字段
const loadColumns = (i18ns: PoemDictData[]) => {
  const columnList: Array<PrimaryTableCol<any>> = [
    {
      colKey: 'serial-number',
      title: () => i18n.global.t('common.attribute.serialNumber'),
      minWidth: 50,
    },
    {
      colKey: 'i18nKey',
      title: () => i18n.global.t('i18n.attribute.key'),
      minWidth: 100,
    },
  ]
  i18ns.forEach(item => {
    columnList.push({
      colKey: item.dictValue,
      title: item.dictValue,
      minWidth: 100,
      ellipsis: {
        theme: 'light',
        placement: 'bottom',
      },
    })
  });
  columnList.push({
    colKey: 'operate',
    minWidth: 340,
    title: () => i18n.global.t('common.operate'),
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          <t-link theme="primary" variant="text" hover="color" onClick={() => navigator.clipboard.writeText(`i18n.global.t('${row.i18nKey}')`).then(() => {
            MessagePlugin.success(i18n.global.t('i18n.label.copySuccess'))
          }).catch(err => MessagePlugin.error(err))

          }>
            {i18n.global.t('i18n.button.copyScript')}
          </t-link>
        }{
          useAuth('i18n:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('i18n:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  })
  columns.value = columnList
}

onMounted(async () => {
  // 加载	i18n标签编码
  await getI18nTagdict();
  // 加载	i18n地区编码
  await getI18ndict();
  //加载数据
  PagePoemDictTypeParams.value.i18nTag = i18nTagDict.value[0].dictValue
  loadData();
});

const searchSubmit = (params: any) => {
  console.log(params)
  PagePoemDictTypeParams.value.i18nTag = params.dictValue
  PagePoemDictTypeParams.value.i18nKey = params.i18nKey
  PagePoemDictTypeParams.value.i18nValue = params.i18nValue
  loadData()
}
</script>
<style scoped lang="less">
.i18nManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>