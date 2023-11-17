<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="roleManage">
    <t-card :bordered="false">
      <div>
        <t-button @click="onAddHander" v-auth="'role:add'">{{ $t('role.button.add') }}</t-button>
      </div>
      <t-table stripe :data="roleData" :columns="columns" row-key="roleId" :loading="tableLoading"
        :pagination="pagination" @change="rehandleChange" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="roleFromVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ roleFromTitle }}</template>
      <roleFrom ref="roleFromRef" @submit-hook="onSubmitHook"></roleFrom>
    </t-dialog>
    <t-dialog v-model:visible="datascopeVisible" :footer="false" width="500px">
      <template #header>{{ $t('role.button.power') }}</template>
      <datascope ref="datascopeRef" @submit-hook="onSubmitHook">
      </datascope>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { computed, onMounted, reactive, ref } from 'vue';
import { ResultEnum } from '@/enums/httpEnum'
import { delPoemRole, rolePage } from '@/api/system/role';
import { PageRole, PoemRole } from '@/api/system/role/types';
import { PrimaryTableCol } from 'tdesign-vue-next/es/table/type';
import { PaginationProps } from 'tdesign-vue-next/es/pagination';
import roleFrom from './components/roleFrom.vue'
import datascope from './components/datascope.vue'
import { useSettingStore } from '@/store';
import { MessagePlugin } from 'tdesign-vue-next';
import { useAuth } from '@/hooks/auth';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const PageRoleParams = ref<PageRole>({
  roleName: '',
  roleCode: '',
  pageNumber: 1,
  pageSize: 10,
})
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 表格字段
const columns: Array<PrimaryTableCol<PoemRole>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'roleName',
    title: () => i18n.global.t('role.label.name'),
    minWidth: 100,
  },
  {
    colKey: 'roleCode',
    title: () => i18n.global.t('role.label.code'),
    minWidth: 100,
  },
  {
    colKey: 'describe',
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
          useAuth('role:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {
          useAuth('role:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onDatascopeHander(row)}>
            {i18n.global.t('role.button.power')}
          </t-link>)
        }
        {
          useAuth('role:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];

const roleData = ref<PoemRole[]>([]);
// 表格loading标记
const tableLoading = ref(false);
const roleFromTitle = ref('');
const roleFromRef = ref();
const datascopeRef = ref()
//控制角色表单dialog是否显示
const roleFromVisible = ref(false)
//控制数据权限dialog是否显示
const datascopeVisible = ref(false)

const settingStore = useSettingStore();

/**
 * 添加角色表单适配器
 */
const onAddHander = () => {
  roleFromTitle.value = '添加角色'
  roleFromRef.value.initFromData()
  roleFromVisible.value = true
}

/**
 * 修改角色表单适配器
 * @param row 当前行数据
 */
const onEditHander = (row: PoemRole) => {
  roleFromTitle.value = '编辑角色'
  roleFromRef.value.initFromData(row.roleId)
  roleFromVisible.value = true
}

/**
 * 数据权限表单适配器
 * @param row 当前行数据
 */
const onDatascopeHander = (row: PoemRole) => {
  datascopeRef.value.initFromData(row.roleId)
  datascopeVisible.value = true
}

/**
 * 删除角色
 * @param row 
 */
const onDelHander = async (row: PoemRole) => {
  const { code } = await delPoemRole(row.roleId)
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
  PageRoleParams.value.pageNumber = pageInfo.current;
  PageRoleParams.value.pageSize = pageInfo.pageSize;
  loadData()
};

/**
 * 加载表格数据
 */
const loadData = async (params?: {}) => {
  tableLoading.value = true;
  const { code, result, message } = await rolePage({ ...PageRoleParams.value, ...params })
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
  roleFromVisible.value = false
  loadData();
}

const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})

// 查询组件配置
const searchOptions = ref<SearchOption[]>([
  {
    name: 'roleName',
    value: '',
    label: computed(() => i18n.global.t('role.label.name')),
    placeholder: computed(() => i18n.global.t('role.label.pl.name')),
    type: 'input',
  },
  {
    name: 'roleCode',
    value: '',
    label: computed(() => i18n.global.t('role.label.code')),
    placeholder: computed(() => i18n.global.t('role.label.pl.code')),
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
.roleManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  .t-card {
    width: 100%;
  }
}
</style>