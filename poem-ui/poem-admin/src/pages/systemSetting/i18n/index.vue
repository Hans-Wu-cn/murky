<template>
  <search v-model:options="searchOptions" @submit="searchSubmit" @reset="getI18ndict"></search>
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
import { i18nDictHook, i18nTagDictHook } from '@/hooks/dict';
import i18n from '@/i18n'

const PagePoemDictTypeParams = ref<I18nPageParams>({
  i18nTag: '',
  i18nKey: '',
  pageNumber: 1,
  pageSize: 10
})
const pagination: PaginationProps = reactive({
  total: 0
})
// 表格字段
const columns = ref<Array<PrimaryTableCol<any>>>([
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
  {
    colKey: 'i18nKey',
    title: '编码',
    minWidth: 100,
  },
]);

const i18nData = ref<any[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const i18nFromTitle = ref('');
const i18nFromRef = ref();
//控制字典数据表单dialog是否显示
const i18nFromVisible = ref(false)
// const i18nDict = ref<PoemDictData[]>();
const i18nTagDict = ref<PoemDictData[]>();
const settingStore = useSettingStore();
const searchi18nTag = ref('')
/**
 * 添加字典数据表单适配器
 */
const onAddHander = () => {
  i18nFromTitle.value = '添加数据'
  i18nFromRef.value.initFromData()
  i18nFromVisible.value = true
}

/**
 * 修改字典数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: any) => {
  console.log(row['i18nKey']);
  i18nFromTitle.value = '编辑数据'
  i18nFromRef.value.initFromData(row['i18nKey'], row['i18nTag'])
  i18nFromVisible.value = true
}

/**
 * 删除字典数据
 * @param row 
 */
const onDelHander = async (row: any) => {
  const { code } = await removeI18n(row['i18nKey'])
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success('删除成功');
    loadData({ i18nTag: searchi18nTag.value });
  }
}


// BaseTable 中只有 page-change 事件，没有 change 事件
const rehandleChange = (changeParams: any, triggerAndData: any) => {
  console.log('分页、排序、过滤等发生变化时会触发 change 事件：', changeParams, triggerAndData);
};

// BaseTable 中只有 page-change 事件，没有 change 事件
const onPageChange = async (pageInfo: PaginationProps) => {
  PagePoemDictTypeParams.value.pageNumber = pageInfo.current;
  PagePoemDictTypeParams.value.pageSize = pageInfo.pageSize;
  loadData({ i18nTag: searchi18nTag.value });

};

/**
 * 加载表格数据
 */
const loadData = async (params?: {}) => {
  tableLoading.value = true;
  const { code, result, message } = await i18nPage({ ...PagePoemDictTypeParams.value, ...params })
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
  loadData({ i18nTag: i18nTagDict.value[0].dictValue });
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置 该配置从字典加载
const searchOptions = ref<SearchOption[]>()

const getI18ndict = async () => {
  const i18ns = await i18nDictHook();
  if (i18ns) {
    // i18nDict.value = i18ns
    searchOptions.value[1].value = i18nTagDict.value[0].dictValue
    loadColumns(i18ns);
  }
}

const getI18nTagdict = async () => {
  const i18nTags = await i18nTagDictHook();
  if (i18nTags) {
    i18nTagDict.value = i18nTags
    searchi18nTag.value = i18nTags[0].dictValue
    searchOptions.value = [
      {
        name: 'i18nKey',
        value: '',
        label: computed(() => i18n.global.t('i18n.label.code')),
        type: 'input',
        placeholder: computed(() => i18n.global.t('i18n.label.pl.code')),
      },
      {
        name: 'i18nTag',
        value: i18nTags[0].dictValue,
        label: computed(() => i18n.global.t('i18n.label.tag')),
        type: 'dict',
        placeholder: computed(() => i18n.global.t('i18n.label.pl.tag')),
        dictOptions: i18nTags
      },
    ];
  }
  loadData({ i18nTag: i18nTagDict.value[0].dictValue });
}

// 加载动态字段
const loadColumns = (i18ns: PoemDictData[]) => {
  const columnList: Array<PrimaryTableCol<any>> = [
    {
      colKey: 'serial-number',
      title: i18n.global.t('common.attribute.serialNumber'),
      minWidth: 50,
    },
    {
      colKey: 'i18nKey',
      title: '编码',
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
    title: '操作',
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          useAuth('i18n:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('i18n:remove', <t-popconfirm content="确认删除吗？" onConfirm={() => onDelHander(row)}>
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
  searchi18nTag.value = i18nTagDict.value[0].dictValue
  loadData({ i18nTag: i18nTagDict.value[0].dictValue });
});

const searchSubmit = (params: any) => {
  searchi18nTag.value = params.dictValue
  loadData(params)
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