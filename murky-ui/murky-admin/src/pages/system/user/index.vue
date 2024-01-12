<template>
  <search v-model:options="searchOptions" @submit="searchSubmit"></search>
  <div class="user">
    <t-card :bordered="false" :title="$t('user.label.deptTree')">
      <t-tree activeMultiple checkStrictly hover lazy :expandLevel="0" :data="deptData" :keys="deptTreeKeys"
        @click="deptTreeNodeClick" />
    </t-card>
    <t-card :bordered="false" :title="$t('user.label.userList')">
      <div>
        <t-button @click="onAddHander" v-auth="'user:add'">{{ $t('user.button.add') }}</t-button>
      </div>
      <t-table stripe :data="userData" :columns="columns" row-key="userId" :loading="tableLoading"
        :pagination="pagination" @page-change="onPageChange" />
    </t-card>
    <t-dialog v-model:visible="userVisible" :footer="false" width="500px" top="20px">
      <template #header>{{ userDialogTitle }}</template>
      <userFrom ref="userFromRef" @submit-hook="onUserSubmit"></userFrom>
    </t-dialog>
    <t-dialog v-model:visible="restPasswdVisible" :footer="false" width="600px" top="20px">
      <template #header>{{ $t('user.button.resetPassword') }}</template>
      <restPasswdFrom ref="restPasswdFromRef" @submit-hook="onRestPasswdSubmit"></restPasswdFrom>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { useSettingStore } from '@/store';
import { MessagePlugin, PaginationProps, PrimaryTableCol, TreeNodeModel } from 'tdesign-vue-next';
import { computed, nextTick, onMounted, reactive, ref } from 'vue';
import { getDeptList } from '@/api/system/dept';
import { DeptTree } from '@/api/system/dept/types';
import { ResultEnum } from '@/enums/httpEnum';
import { userPage, delUserInfo } from '@/api/system/user'
import { PageUser, User } from '@/api/system/user/types'
import userFrom from './components/userFrom.vue'
import restPasswdFrom from './components/restPasswdFrom.vue'
import { useAuth } from '@/hooks/auth';
import { gender } from './constants';
import search, { SearchOption } from '@/components/search/index.vue';
import i18n from '@/i18n';

const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
// 用户列表条件
const userQuery = ref<PageUser>({
  userName: '',
  deptId: '',
  pageNumber: 1,
  pageSize: 10
})

// 部门数据
const deptData = ref<DeptTree[]>([]);
// 用户列表
const userData = ref([])
// 表格loading标记
const tableLoading = ref(false);
const pagination: PaginationProps = reactive({
  total: 0,
  pageEllipsisMode: 'mid',
  maxPageBtn: 5
})
// 部门树配置
const deptTreeKeys = ref({ value: 'deptId', label: 'deptName', children: 'children' })
// 表格字段
const columns: Array<PrimaryTableCol<User>> = [
  {
    colKey: 'serial-number',
    title: () => i18n.global.t('common.attribute.serialNumber'),
    minWidth: 50,
  },
  {
    colKey: 'userName',
    title: () => i18n.global.t('user.label.userName'),
    minWidth: 100,
  },
  {
    colKey: 'account',
    title: () => i18n.global.t('user.label.account'),
    minWidth: 100,
  },
  {
    colKey: 'deptName',
    title: () => i18n.global.t('user.label.deptName'),
    minWidth: 100,
  },
  {
    colKey: 'sex',
    title: () => i18n.global.t('user.label.sex'),
    minWidth: 100,
    cell: (h, { col, row }) => (
      <div>
        {
          gender[row[col.colKey]]
        }
      </div>
    ),
  },
  {
    colKey: 'email',
    title: () => i18n.global.t('user.label.email'),
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

          useAuth('user:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onEditHander(row)}>
            {i18n.global.t('common.button.edit')}
          </t-link>)
        }
        {

          useAuth('user:edit', <t-link theme="primary" variant="text" hover="color" onClick={() => onRestPasswdHander(row)}>
            {i18n.global.t('user.button.resetPassword')}
          </t-link>)
        }
        {
          useAuth('user:remove', <t-popconfirm content={() => i18n.global.t('common.label.sureDelete')} onConfirm={() => onDelHander(row)}>
            <t-link variant="text" hover="color" theme="danger">
              {i18n.global.t('common.button.delete')}
            </t-link>
          </t-popconfirm>)
        }
      </t-space>
    ),
  },
];
const userVisible = ref(false);
const restPasswdVisible = ref(false);
const userDialogTitle = ref('');
const userFromRef = ref();
const restPasswdFromRef = ref();


/**
 * 添加事件弹窗适配
 */
const onAddHander = async () => {
  userDialogTitle.value = i18n.global.t('user.button.add')
  userVisible.value = true;
  await nextTick();
  userFromRef.value.initFromData();
}

/**
 * 修改事件弹窗适配
 */
const onEditHander = async (row: User) => {
  userDialogTitle.value = i18n.global.t('user.label.edit')
  userVisible.value = true;
  await nextTick();
  userFromRef.value.initFromData(row.id);
}

/**
 * 重置密码事件
 */
const onRestPasswdHander = async (row: User) => {
  restPasswdVisible.value = true;
  restPasswdFromRef.value.initFromData(row.id);
}

/**
 * 删除事件
 */
const onDelHander = async (row: User) => {
  const { code } = await delUserInfo(row.id);
  if (ResultEnum.SUCCESS === code) {
    MessagePlugin.success(i18n.global.t('common.messages.deleteSuccess'));
    loadUserData();
  }
}
/**
 * 用户表单提交事件
 */
const onUserSubmit = () => {
  restPasswdVisible.value = false;
  loadUserData();
}

/**
 * 重置密码提交事件
 */
const onRestPasswdSubmit = () => {
  restPasswdVisible.value = false;
}

/**
* 加载列表数据
*/
const getdeptTreeData = async () => {
  let data: DeptTree[] = [];
  const { code, result } = await getDeptList();

  if (ResultEnum.SUCCESS === code) {
    data = result
  }
  return data;
}

/**
* load tree data
*/
const loadUserData = async () => {
  // 需要更新数据地址空间
  tableLoading.value = true;
  const { code, result } = await userPage({ ...userQuery.value });
  if (ResultEnum.SUCCESS === code) {
    userData.value = result.records
    pagination.total = +result.totalRow
  }
  tableLoading.value = false;
};

/**
 * 部门树点击事件
 */
const deptTreeNodeClick = (context: { node: TreeNodeModel<DeptTree>; e: MouseEvent }) => {
  console.log(context)
  userQuery.value.deptId = context.node.value as string
  loadUserData()
}

/**
 * 分页事件
 * @param pageInfo 分页参数
 */
const onPageChange = (pageInfo: PaginationProps) => {
  userQuery.value.pageNumber = pageInfo.current;
  userQuery.value.pageSize = pageInfo.pageSize;
  loadUserData()
}
onMounted(async () => {
  // 需要更新数据地址空间
  deptData.value = await getdeptTreeData()
  await loadUserData();
});

// 查询组件
const searchOptions = ref<SearchOption[]>([
  {
    name: 'userName',
    value: '',
    label: computed(() => i18n.global.t('user.label.userName')),
    type: 'input',
    placeholder: computed(() => i18n.global.t('user.label.pl.userName')),
  },
  {
    name: 'email',
    value: '',
    label: computed(() => i18n.global.t('user.label.email')),
    type: 'input',
    placeholder: computed(() => i18n.global.t('user.label.pl.email')),
  },
  {
    name: 'sex',
    value: '',
    label: computed(() => i18n.global.t('user.label.sex')),
    type: 'select',
    placeholder: computed(() => i18n.global.t('user.label.pl.sex')),
    radioOptions: gender
  },
])
const searchSubmit = (params: any) => {
  console.log(params)
  userQuery.value.userName = params.userName
  userQuery.value.email = params.email
  userQuery.value.sex = params.sex
  loadUserData()
}
</script>
<style scoped lang="less">
.user {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;

  >.t-card:first-of-type {
    margin-right: 10px;
    min-width: 200px;
  }
}
</style>
