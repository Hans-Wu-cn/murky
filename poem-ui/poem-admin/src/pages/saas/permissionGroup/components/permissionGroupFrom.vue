<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="权限组名" name="saasRoleName">
        <t-input v-model="formData.groupName" placeholder="请输入权限组名"></t-input>
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
          <t-button theme="primary" type="submit" :loading="loading">{{ $t('common.button.submit') }}</t-button>
          <t-button theme="default" variant="base" type="reset" :loading="loading">{{ $t('common.button.reset1')
          }}</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PermissionGroup } from '@/api/saas/permissionGroup/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPermissionGroup, updatePermissionGroup, permissionGroupInfo } from '@/api/saas/permissionGroup';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemSaasMenu } from '@/api/saas/menu/types';
import { getSaasMenuList } from '@/api/saas/menu';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const menuTree = ref<Array<PoemSaasMenu>>();
const menuTreeKeys = { value: 'saasMenuId', label: 'label', children: 'children' }
const FORM_RULES = ref<FormRules>({
  saasRoleName: [{ required: true, message: '请输入权限组名', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PermissionGroup>({
  groupName: '',
  describe: '',
  saasMenuIds: []
});
const permissionGroupFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (permissionGroupFromId.value) {
    initFromData(permissionGroupFromId.value)
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
 * @param groupId 权限组id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (groupId: string) => {
  if (!groupId) {
    formData.value = {
      groupName: '',
      describe: '',
      saasMenuIds: []
    }
    permissionGroupFromId.value = undefined
    return
  }
  permissionGroupFromId.value = groupId;
  const { code, result } = await permissionGroupInfo(groupId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PermissionGroup>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.groupId ? updatePermissionGroup : addPermissionGroup
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
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