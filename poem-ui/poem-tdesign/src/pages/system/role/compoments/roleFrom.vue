<template>
  <div>
      <t-form ref="form" :rules="FORM_RULES" :data="formData" :colon="true" @reset="onReset" @submit="onSubmit">
        <t-form-item label="角色名" name="roleName">
          <t-input v-model="formData.roleName" placeholder="请输入角色名"></t-input>
        </t-form-item>
        <t-form-item label="权限码" name="roleCode">
          <t-input v-model="formData.roleCode" placeholder="请输入角色权限码"></t-input>
        </t-form-item>
        <t-form-item label="数据范围" name="roleCode">
          <t-radio-group v-model="formData.dataScope">
            <t-radio v-for="(item,i) in DATASCOPE" :key="i" :value="i" allow-uncheck>{{ item }}</t-radio>
          </t-radio-group>
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
import { ref } from 'vue'
import { PoemRole } from '@/api/role/types'
import { FormRules, MessagePlugin, SubmitContext, } from 'tdesign-vue-next';
import { addPoemRole, updatePoemRole } from '@/api/role';
import { ResultEnum } from '@/enums/httpEnum';
import { DATASCOPE } from '../constants';
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
const formData = ref<PoemRole>({
  roleCode:'',
  roleName:'',
  dataScope:0
});

/**
 * 重置表单
 */
const onReset = () => {
  MessagePlugin.success('重置成功');
};
const onSubmit = async ({ validateResult, firstError }: SubmitContext<PoemRole>) => {
  if (validateResult === true) {
    const api = formData.value.roleId ? updatePoemRole : addPoemRole
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      visible.value = true
      MessagePlugin.success('提交成功');
      emit('refresh');
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