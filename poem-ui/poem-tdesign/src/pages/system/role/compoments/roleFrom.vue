<template>
  <div>
    <t-dialog :visible="visible" :close-btn="true" :confirm-btn="getConfirmBtn()" cancel-btn="取消" :on-close="onClose">
      <template #header>{{ title }}</template>
      <template #body>
        <t-form ref="form" :rules="FORM_RULES" :data="formData" :colon="true" @reset="onReset">
          <t-form-item label="角色名" name="roleName">
            <t-input v-model="formData.name" placeholder="请输入角色名"></t-input>
          </t-form-item>
          <t-form-item label="权限码" name="roleCode">
            <t-input v-model="formData.name" placeholder="请输入角色权限码"></t-input>
          </t-form-item>
          <t-form-item label="描述" name="describe">
            <t-input v-model="formData.name" placeholder="请输入描述内容"></t-input>
          </t-form-item>
        </t-form>

      </template>
    </t-dialog>
  </div>
</template>
<script setup lang="tsx">
import { ref } from 'vue'
import { PoemRole } from '@/api/role/types'
const emit = defineEmits(['refresh'])

const props = defineProps({
  title: {
    type: String
  },
})
const visible = ref(false);
const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名' }],
  roleCode: [{ required: true, message: '请输入角色权限码' }],
});
const fromData: PoemRole = ref({

});

/**
 * 显示或隐藏组件
 */
const showDialog = () => {
  visible.value = !visible.value;
}

const getConfirmBtn = (): TdButtonProps => {
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

const onClose = () => {
  visible.value = false;
}
defineExpose({
  showDialog
})



</script>
<style lang="less"></style>