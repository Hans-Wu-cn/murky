<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="角色名" name="saasRoleName">
        <t-input v-model="formData.saasRoleName" placeholder="请输入角色名"></t-input>
      </t-form-item>
      <t-form-item label="权限码" name="saasRoleCode">
        <t-input v-model="formData.saasRoleCode" placeholder="请输入角色权限码"></t-input>
      </t-form-item>
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
      </t-form-item>
      <t-form-item label="菜单权限" name="menuIds">
        <div class="treeBox">
          <t-tree ref="saasMenuTreeRef" hover expand-all v-model="formData.saasMenuIds" :data="menuTree"
            :keys="menuTreeKeys" checkable value-mode="all" @change="treeOnChange" />
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
import { PoemSaasRole } from '@/api/saas/role/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemSaasRole, updatePoemSaasRole, saasRoleInfo } from '@/api/saas/role';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemSaasMenu } from '@/api/saas/menu/types';
import { getSaasMenuList } from '@/api/saas/menu';

const emit = defineEmits(['submit-hook'])
const menuTree = ref<Array<PoemSaasMenu>>();
const menuTreeKeys = { value: 'saasMenuId', label: 'label', children: 'children' }
const FORM_RULES = ref<FormRules>({
  saasRoleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
  saasRoleCode: [{ required: true, message: '请输入角色权限码', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemSaasRole>({
  saasRoleCode: '',
  saasRoleName: '',
  describe: '',
  saasMenuIds: []
});
const saasRoleFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (saasRoleFromId.value) {
    initFromData(saasRoleFromId.value)
  }
  loading.value = false

};

/**
 * 树组件勾选事件
 * @param value 
 * @param context 
 */
const treeOnChange = (value: Array<TreeNodeValue>, context: { node: TreeNodeModel<PoemSaasMenu>; e?: any; trigger: 'node-click' | 'setItem' }) => {
  const menuIds = Array<string>();
  value.forEach(item => menuIds.push(item as string))
  formData.value.saasMenuIds = menuIds
}

/**
 * 初始化表单
 * @param saasRoleId 角色id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (saasRoleId: string) => {
  if (!saasRoleId) {
    formData.value = {
      saasRoleCode: '',
      saasRoleName: '',
      describe: '',
      saasMenuIds: []
    }
    saasRoleFromId.value = undefined
    return
  }
  saasRoleFromId.value = saasRoleId;
  const { code, result } = await saasRoleInfo(saasRoleId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemSaasRole>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.saasRoleId ? updatePoemSaasRole : addPoemSaasRole
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
  const { code, result } = await getSaasMenuList();
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