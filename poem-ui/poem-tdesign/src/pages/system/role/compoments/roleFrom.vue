<template>
  <div>
    <t-dialog :visible="visible" :confirm-btn="getConfirmBtn()" cancel-btn="取消" :on-close="onClose">
      <template #header>{{ title }}</template>
      <t-form ref="form" :rules="FORM_RULES" :data="formData" :colon="true" @reset="onReset">
        <t-form-item label="角色名" name="roleName">
          <t-input v-model="formData.name" placeholder="请输入角色名"></t-input>
        </t-form-item>
        <t-form-item label="权限码" name="roleCode">
          <t-input v-model="formData.name" placeholder="请输入角色权限码"></t-input>
        </t-form-item>
        <t-form-item label="描述" name="describe">
          <t-textarea v-model="formData.name" placeholder="请输入描述内容"></t-textarea>
        </t-form-item>
      </t-form>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { ButtonProps, FormRules, MessagePlugin, TNode, TdButtonProps } from 'tdesign-vue-next';
const emit = defineEmits(['refresh'])

const props = defineProps({
  title: {
    type: String
  },
})
//弹框显示
const visible = ref(false);

const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名' }],
  roleCode: [{ required: true, message: '请输入角色权限码' }],
})
const formData = ref({
  name: ''
});

/**
 * 显示或隐藏组件
 */
const showDialog = () => {
  visible.value = !visible.value;
}

const getConfirmBtn = () => {
  return (
    <t-button theme="primary" onClick={() => submit}>
      提交
    </t-button>
  );
};

/**
 * 提交表单
 */
const submit = () => {

}

/**
 * 重置表单
 */
const onReset = () => {
  MessagePlugin.success('重置成功');
};

const onClose = () => {
  visible.value = false;
}

defineExpose({
  showDialog
})



</script>
<style lang="less"></style>