<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="角色名" name="roleName">
        <t-input v-model="formData.roleName" placeholder="请输入角色名"></t-input>
      </t-form-item>
      <t-form-item label="权限码" name="roleCode">
        <t-input v-model="formData.roleCode" placeholder="请输入角色权限码"></t-input>
      </t-form-item>
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
      </t-form-item>
      <t-form-item label="菜单权限" name="menuIds">
        <div class="treeBox">
          <t-tree ref="deptTreeRef" hover expand-all v-model="formData.menuIds" :data="menuTree" :keys="menuTreeKeys"
            checkable value-mode="all" @change="treeOnChange" checkStrictly/>
        </div>
      </t-form-item>
      <t-form-item>
        <t-space size="small">
          <t-button theme="primary" type="submit" :loading="loading">提交</t-button>
          <t-button theme="default" variant="base" type="reset" :loading="loading">重置</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole, roleInfo } from '@/api/role';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemMenu } from '@/api/menu/types';
import { getMenuList } from '@/api/menu';

const emit = defineEmits(['submit-hook'])
const menuTree = ref<Array<PoemMenu>>();
const menuTreeKeys = { value: 'menuId', label: 'label', children: 'children' }
const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色权限码', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemRole>({
  roleCode: '',
  roleName: '',
  describe: '',
  dataScope: 0,
  menuIds: []
});

const roleFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (roleFromId.value) {
    initFromData(roleFromId.value)
  }
  loading.value = false

};

/**
 * 树组件勾选事件
 * @param value 
 * @param context 
 */
const treeOnChange = (value: Array<TreeNodeValue>, context: { node: TreeNodeModel<PoemMenu>; e?: any; trigger: 'node-click' | 'setItem' }) => {
  const menuIds = Array<string>();
  value.forEach(item => menuIds.push(item as string))
  formData.value.menuIds = menuIds
}

/**
 * 初始化表单
 * @param roleId 角色id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (roleId: string) => {
  if (!roleId) {
    formData.value = {
      roleCode: '',
      roleName: '',
      describe: '',
      dataScope: 0,
      menuIds: []
    }
    roleFromId.value = undefined
    return
  }
  roleFromId.value = roleId;
  const { code, result } = await roleInfo(roleId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemRole>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.roleId ? updatePoemRole : addPoemRole
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success('提交成功');
      emit('submit-hook');
    } else {
      MessagePlugin.error(res.message);
    }
    loading.value = false
  }
};

onMounted(async () => {
  const { code, result } = await getMenuList();
  if (code === ResultEnum.SUCCESS) {
    menuTree.value = result
  }
});

defineExpose({
  initFromData
})



</script>
<style lang="less">
.treeBox {
  border: 1px solid #ddd;
  overflow: auto;
  height: 300px;
  width: 100%;
}
</style>