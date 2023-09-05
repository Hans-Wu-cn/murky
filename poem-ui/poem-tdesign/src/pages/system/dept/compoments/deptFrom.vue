<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="角色名" name="deptName">
        <t-input v-model="formData.deptName" placeholder="请输入部门名称"></t-input>
      </t-form-item>
      <t-form-item label="排序" name="sort">
        <t-input-number placeholder="请输入内容" v-model="formData.sort" />
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
import { PoemDeptTree, PoemDept } from '@/api/dept/types'
import { FormRules, MessagePlugin, SelectValue, SelectValueChangeTrigger, SubmitContext, } from 'tdesign-vue-next';
import { addDept, editDept } from '@/api/dept';
import { ResultEnum } from '@/enums/httpEnum';
const emit = defineEmits(['submit-hook'])

const FORM_RULES = ref<FormRules>({
  deptName: [{ required: true, message: '请输入部门名称', trigger: 'blur' }],
  sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
})
// 表单对象
const formData = reactive<PoemDeptTree>({
  deptId: '',
  deptName: '',
  parentDept: '0',
  sort: 0
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
const onSubmit = async ({ validateResult }: SubmitContext<PoemDept>) => {
  if (validateResult) {
    const api = formData.deptId ? editDept : addDept
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