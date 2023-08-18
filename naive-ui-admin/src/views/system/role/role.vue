<template>
  <div>
    <n-card :bordered="false" class="mt-4 proCard">
      <BasicTable :columns="columns" :request="loadDataTable" :row-key="(row) => row.roleId" ref="actionRef"
        :actionColumn="actionColumn" @update:checked-row-keys="onCheckedRow">
        <template v-if="hasPermission('role:add')"  #tableTitle>
          <n-button  type="primary" @click="handleAdd">
            <template #icon>
              <n-icon>
                <PlusOutlined />
              </n-icon>
            </template>
            添加角色
          </n-button>
        </template>

        <template #action>
          <TableAction />
        </template>
      </BasicTable>
    </n-card>
    <n-modal v-model:show="showSaveModal" :show-icon="false" preset="dialog" :title="saveModalTitle">
      <template #header>
        <div v-text="saveModalTitle"></div>
      </template>
      <div>
        <n-grid cols="1" responsive="screen">
          <n-grid-item>
            <n-form :label-width="80" :model="formValue" :on-after-leave="initFromValue" :rules="rules"
              label-placement="left" ref="formRef" class="py-8">
              <n-form-item label="角色名" path="roleName">
                <n-input v-model:value="formValue.roleName" placeholder="请输入角色名" />
              </n-form-item>
              <n-form-item label="角色码" path="roleCode">
                <n-input v-model:value="formValue.roleCode" placeholder="请输入角色码" />
              </n-form-item>
              <n-form-item label="描述" path="describe">
                <n-input v-model:value="formValue.describe" placeholder="请输入描述" />
              </n-form-item>
              <n-form-item>
                <n-tree block-line cascade checkable :virtual-scroll="true" key-field="menuId" :data="treeData"
                check-strategy='parent' :checked-keys="formValue.menuIds" @update:checked-keys="checkedTree"
                  style="max-height: 300px; overflow: hidden" />
              </n-form-item>
            </n-form>
          </n-grid-item>
        </n-grid>
      </div>
      <template #action>
        <n-button type="primary" :loading="formBtnLoading" @click="confirmForm">保存</n-button>
        <n-button type="primary" :loading="formBtnLoading" @click="showSaveModal = false">取消</n-button>
      </template>
    </n-modal>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, h, onMounted } from 'vue';
import { PageRequest } from '@/api/types'
import { useMessage } from 'naive-ui';
import { BasicTable, TableAction } from '@/components/Table';
import { getRolePage, addRole, editRole, removeRole, getRoleInfo } from '@/api/system/role';
import { usePermission } from '@/hooks/web/usePermission';
import { PoemRoleFrom } from '@/api/system/role/types';
import { getMenuList } from '@/api/system/menu';
import { columns } from './columns';
import { PlusOutlined } from '@vicons/antd';
import { ResultEnum } from '@/enums/httpEnum';
import { TreeOption } from 'naive-ui/lib/tree';
// import { useRouter } from 'vue-router';

// const router = useRouter();
const message = useMessage();
const actionRef = ref();
const formBtnLoading = ref(false);
const treeData = ref([]);
const showSaveModal = ref(false);
const saveModalTitle = ref('');
const {hasPermission} = usePermission();
//naive ui中树因为树功能的缺陷需要自己补充节点
const nodeSupplement=ref(new Set<string>());
const actionColumn = reactive({
  width: 250,
  title: '操作',
  key: 'action',
  fixed: 'right',
  render(record) {
    return h(TableAction, {
      style: 'button',
      actions: [
        {
          label: '编辑',
          onClick: handleEdit.bind(null, record),
          ifShow: () => {
            return true;
          },
          auth: ['role:edit'],
        },
        {
          label: '删除',
          onClick: handleDelete.bind(null, record),
          // 根据业务控制是否显示 isShow 和 auth 是并且关系
          ifShow: () => {
            return true;
          },
          // 根据权限控制是否显示: 有权限，会显示，支持多个
          auth: ['role:remove'],
        },
      ],
    });
  },
});

/**
 * 表单对象
 */
const formValue: PoemRoleFrom = reactive({
  roleCode: '',
  roleName: '',
  describe: '',
  menuIds: [],
})

/**
 * 表单数据校验
 */
const rules = {
  roleName: {
    required: true,
    message: '请输入角色名',
    trigger: 'blur',
  },
  roleCode: {
    required: true,
    message: '请输入角色码',
    trigger: 'blur',
  },
};

/**
 * 加载表格数据
 * @param res 返回分页信息
 */
const loadDataTable = async (res: PageRequest) => {
  const { result } = await getRolePage(res);
  console.log(result)
  return result;
};

/**
 * 查询角色详情,并赋值给表单对象
 * @param roleId 角色id
 */
const getInfo = async (roleId: number) => {
  let { code, result } = await getRoleInfo(roleId);
  if (code !== ResultEnum.SUCCESS) {
    message.error("查询失败");
  }
  Object.assign(formValue, result);
}

/**
 * 初始化表单数据
 */
const initFromValue = () => {
  formValue.roleId = undefined;
  formValue.roleCode = '';
  formValue.roleName = '';
  formValue.describe = '';
  formValue.menuIds = [];
}

/**
 * 表单数据勾选事件
 * @param rowKeys 被勾选的数据
 */
const onCheckedRow = (rowKeys: any[]) => {
  console.log(rowKeys);
}

/**
 * 重新加载表格数据
 */
function reloadTable() {
  actionRef.value.reload();
  // parentNodeSupplement.value=[];
}

/**
 * 表单添加适配
 */
const handleAdd = () => {
  initFromValue();
  saveModalTitle.value = '添加角色';
  showSaveModal.value = true;
}

/**
 * 表单修改适配
 */
const handleEdit = (record: Recordable) => {
  getInfo(record.roleId);
  if (!formValue) {
    return;
  }
  saveModalTitle.value = '编辑角色';
  showSaveModal.value = true;
}

/**
 * 删除事件
 * @param record 点击行数据
 */
const handleDelete = async (record: Recordable) => {
  console.log('点击了删除', record);
  const { code } = await removeRole(record.roleId);
  if (code === ResultEnum.SUCCESS) {
    message.success('删除成功');
    reloadTable();
  }
}
/**
 * 表单提交事件
 */
const confirmForm = async () => {
  formBtnLoading.value = true;
  let form=formValue;
  // nodeSupplement.value.forEach(item =>{
  //   form.menuIds.push(item);
  // })
  if (formValue.roleId) {
    const { code } = await editRole(formValue);
    if (code === ResultEnum.SUCCESS) {
      message.success('修改成功');
      showSaveModal.value = false;
      reloadTable();
    }
  } else {
    const { code } = await addRole(formValue);
    if (code === ResultEnum.SUCCESS) {
      message.success('添加成功');
      showSaveModal.value = false;
      reloadTable();
    }
  }
  formBtnLoading.value = false;

}

/**
 * 菜单树勾选事件
 * @param keys 菜单树选中menuId
 */
const checkedTree = (keys: Array<string | number>, option: Array<TreeOption | null>, meta: { node: TreeOption | null, action: 'check' | 'uncheck' }) => {
  // if(meta.action==='check'){
  //   option.forEach(item => nodeSupplement.value.add(item.))
  //   nodeSupplement.value.push(meta.node?.parentMenuId as string);
  // }else if(meta.action==='uncheck'){
  //   parentNodeSupplement.value=parentNodeSupplement.value.filter(item =>{
  //     if(item != meta.node?.parentMenuId){
  //       return true;
  //     }
  //   })
  // }
  formValue.menuIds = keys as Array<string>;
  console.log(option)
  console.log(nodeSupplement)
}




onMounted(async () => {
  const treeMenuList = await getMenuList();
  treeData.value = treeMenuList.result;
});
</script>

<style lang="less" scoped></style>