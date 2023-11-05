<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="dictManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'dict:add'">添加字典</t-button>
        <t-button @click="refershDict" v-auth="['dict:add', 'dict:edit', 'dict:remove']">
          <template #icon><Refresh-icon /></template>
          刷新缓存</t-button>
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
import { dictTypePage, PoemDictTypeRemove, refershDict } from '@/api/system/dict';
import { PoemDictType, PagePoemDictType } from '@/api/system/dict/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import { status } from './constants';
import dictTypeFrom from './components/dictTypeFrom.vue'
import { dictConfig } from './config';
import { useRoute, useRouter } from 'vue-router';
import {
  RefreshIcon,
} from 'tdesign-icons-vue-next';
const route = useRoute();
const router = useRouter();
const PagePoemDictTypeParams = ref<PagePoemDictType>({
  dictName: '',
  dictType: '',
  status: 0,
  pageNumber: 1,
  pageSize: 10
})
const pagination: PaginationProps = reactive({
  total: 0
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemDictType>> = [
  {
    colKey: 'serial-number',
    title: '序号',
    minWidth: 50,
  },
  {
    colKey: 'dictName',
    title: '字典名称',
    minWidth: 100,
  },
  {
    colKey: 'dictType',
    title: '字典类型',
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
    ),
  },
];

const dictTypeData = ref<PoemDictType[]>([]);
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
  dictTypeFromTitle.value = '添加字典'
  dictTypeFromRef.value.initFromData()
  dictTypeFromVisible.value = true
}

/**
 * 修改字典数据表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemDictType) => {
  dictTypeFromTitle.value = '编辑字典'
  dictTypeFromRef.value.initFromData(row.dictTypeId)
  dictTypeFromVisible.value = true
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

/**
 * 查询字典分类下对应得字典数据
 * @param row 
 */
const onDictData = (row: PoemDictType) => {
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
  const { code, result, message } = await dictTypePage({ ...PagePoemDictTypeParams.value, ...params })
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

onMounted(async () => {
  loadData();
});

const searchSubmit = (params: any) => {
  console.log(params)
  loadData(params)
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