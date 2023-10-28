<template>
  <search v-model:options="searchOptions" @submit="searchSubmit" @reset="getI18nTagdict"></search>
  <div class="i18nManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'i18n:add'">添加编码</t-button>
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
import { PoemDictTypeRemove, dict } from '@/api/dict';
import { i18nPage } from '@/api/i18n';
import { PoemDictType, PoemDictData } from '@/api/dict/types';
import { I18nData, I18nPageParams } from '@/api/i18n/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18nFrom from './components/i18nFrom.vue'
import { i18nDictHook, i18nTagDictHook } from '@/hooks/dict';

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
const columns: Array<PrimaryTableCol<any>> = reactive([
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
]);

const i18nData = ref<any[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const i18nFromTitle = ref('');
const i18nFromRef = ref();
//控制字典数据表单dialog是否显示
const i18nFromVisible = ref(false)
const i18nDict = ref<PoemDictData[]>();
const i18nTagDict = ref<PoemDictData[]>();
const settingStore = useSettingStore();

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
const onDelHander = async (row: PoemDictType) => {
  const { code } = await PoemDictTypeRemove(row.dictTypeId)
  if (code === ResultEnum.SUCCESS) {
    MessagePlugin.success('删除成功');
    loadData();
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
  loadData()
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
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置 该配置从字典加载
const searchOptions = ref<SearchOption[]>()

const getI18ndict = async () => {
  const i18ns = await i18nDictHook();
  if (i18ns) {
    i18nDict.value = i18ns
    loadColumns(i18ns);
  }
}

const getI18nTagdict = async () => {
  const i18nTags = await i18nTagDictHook();
  if (i18nTags) {
    i18nTagDict.value = i18nTags
    searchOptions.value = [
      {
        name: 'i18nKey',
        value: '',
        label: 'i18n编码',
        type: 'input',
        placeholder: '请输入i18n编码',
      },
      {
        name: 'i18nTag',
        value: i18nTags[0].dictValue,
        label: 'i18n标签',
        type: 'dict',
        placeholder: '请选择i18n标签',
        dictOptions: i18nTags
      },
    ];
  }

}

// 加载动态字段
const loadColumns = (i18ns: PoemDictData[]) => {
  i18ns.forEach(item => {
    const column = {
      colKey: item.dictValue,
      title: item.dictValue,
      minWidth: 100,
    }
    columns.push(column)
  });
  columns.push({
    colKey: 'operate',
    minWidth: 340,
    title: '操作',
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <t-space>
        {
          useAuth('i18n:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            编辑
          </t-link>)
        }
        {
          useAuth('i18n:remove', <t-popconfirm content="确认删除吗？" onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              删除
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  })
}

onMounted(async () => {
  // 加载	i18n标签编码
  await getI18nTagdict();
  // 加载	i18n地区编码
  await getI18ndict();
  //加载数据
  loadData({ i18nTag: i18nTagDict.value[0].dictValue });
});

const searchSubmit = (params: any) => {
  console.log(params)
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