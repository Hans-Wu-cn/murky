<template>
  <search v-model:options="searchOptions" @submit="searchSubmit(PagePoemDictDataParams)" @reset="searchRest"></search>
  <div class="roleManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'dict:add'">添加字典数据</t-button>
      </div>
      <t-table stripe :data="dictData" :columns="columns" row-key="roleId" :loading="tableLoading"
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
import { dictDataPage, PoemDictDataRemove } from '@/api/dict';
import { PagePoemDictData, PoemDictData } from '@/api/dict/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import { status } from './constants';
import dictDataFrom from './components/dictDataFrom.vue'
import { useRoute } from 'vue-router';

const route = useRoute();
const PagePoemDictDataParams = ref<PagePoemDictData>({
  dictType: '',
  status: undefined,
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemDictData>> = [
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
  {
    colKey: 'dictLabel',
    title: '字典标签',
    minWidth: 100,
  },
  {
    colKey: 'dictValue',
    title: '字典值',
    minWidth: 100,
  },
  {
    colKey: 'status',
    title: '状态',
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
    title: '描述',
    minWidth: 100,
  },
  {
    colKey: 'operate',
    minWidth: 340,
    title: '操作',
    // 增、删、改、查 等操作
    cell: (h, { row }) => (
      <div class="tdesign-table-demo__table-operations">
        <t-space>
          {
            useAuth('dict:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
              编辑
            </t-link>)
          }
          {
            useAuth('dict:remove', <t-popconfirm content="确认删除吗？" onConfirm={() => onDelHander(row)}>
              <t-link variant="text" hover="color" theme="danger">
                删除
              </t-link>
            </t-popconfirm>)
          }
        </t-space>
      </div>
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
  dictDataFromTitle.value = '添加字典'
  dictDataFromRef.value.initFromData(undefined, PagePoemDictDataParams.value.dictType)
  dictDataFromVisible.value = true
}

/**
 * 修改字典数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemDictData) => {
  dictDataFromTitle.value = '编辑字典'
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
    name: 'dictName',
    value: '',
    label: '字典名称',
    type: 'input',
    placeholder: '请输入字典名称',
  },
  {
    name: 'dictType',
    value: '',
    label: '字典类型',
    type: 'input',
    placeholder: '请输入字典类型',
  },
  {
    name: 'status',
    value: '',
    label: '状态',
    type: 'select',
    placeholder: '请选择字典状态',
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
.roleManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>