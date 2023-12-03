<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item :label="$t('	role.label.name')" name="roleName">
        <t-input v-model="formData.roleName" :placeholder="$t('role.label.pl.name')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('role.label.code')" name="roleCode">
        <t-input v-model="formData.roleCode" :placeholder="$t('role.label.pl.code')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('common.attribute.describe')" name="describe">
        <t-textarea v-model="formData.describe" :placeholder="$t('common.attribute.pl.describe')"></t-textarea>
      </t-form-item>
      <t-form-item :label="$t('menu.label.from.auth')" name="menuIds">
        <div class="treeBox">
          <t-tree ref="menuTreeRef" hover expand-all v-model="formData.menuIds" :data="menuTree" :keys="menuTreeKeys"
            checkable value-mode="all" @change="treeOnChange" />
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
import { PoemRole } from '@/api/system/role/types'
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole, roleInfo } from '@/api/system/role';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemMenu } from '@/api/system/menu/types';
import { getMenuList } from '@/api/system/menu';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const menuTree = ref<Array<PoemMenu>>();
const menuTreeKeys = { value: 'menuId', label: 'label', children: 'children' }
const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: i18n.global.t('role.label.pl.name'), trigger: 'blur' }],
  roleCode: [{ required: true, message: i18n.global.t('role.label.pl.code'), trigger: 'blur' }],
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
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
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