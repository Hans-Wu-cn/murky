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
      <t-form-item label="数据范围">
        <t-select v-model="formData.dataScope">
          <t-option v-for="item in dataScopeDict" :key="item.value" :label="item.label" :value="item.value" />
        </t-select>
      </t-form-item>
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
      </t-form-item>
      <t-form-item>
        <t-space size="small">
          <t-button theme="primary" type="submit">提交</t-button>
          <t-button theme="default" variant="base" type="reset">重置</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { reactive, ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { FormRules, MessagePlugin, SelectValue, SelectValueChangeTrigger, SubmitContext, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole } from '@/api/role';
import { ResultEnum } from '@/enums/httpEnum';
import { dataScopeDict } from '../constants';
const emit = defineEmits(['submit-hook'])

const FORM_RULES = ref<FormRules>({
  roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色权限码', trigger: 'blur' }],
})
// 表单对象
const formData = reactive<PoemRole>({
  roleCode: '',
  roleName: '',
  describe: '',
  dataScope: 0
});

/**
 * 重置表单
 */
const onReset = () => {
  MessagePlugin.success('重置成功');
};

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemRole>) => {
  if (validateResult === true) {
    const api = formData.roleId ? updatePoemRole : addPoemRole
    const res = await api(formData);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success('提交成功');
      emit('submit-hook');
    } else {
      MessagePlugin.error(res.message);
    }
  }
};

defineExpose({
  formData
})



</script>
<style lang="less"></style>