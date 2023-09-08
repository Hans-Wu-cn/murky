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
          <t-button theme="primary" type="submit" :loading="loading">提交</t-button>
          <t-button theme="default" variant="base" type="reset" :loading="loading">重置</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { reactive, ref } from 'vue'
import { PoemDeptTree, PoemDept } from '@/api/dept/types'
import { FormRules, MessagePlugin, SelectValue, SelectValueChangeTrigger, SubmitContext, } from 'tdesign-vue-next';
import { addDept, deptInfo, editDept } from '@/api/dept';
import { ResultEnum } from '@/enums/httpEnum';
const emit = defineEmits(['submit-hook'])

const FORM_RULES = ref<FormRules>({
  deptName: [{ required: true, message: '请输入部门名称', trigger: 'blur' }],
  sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemDeptTree>({
  deptId: '',
  deptName: '',
  parentDept: '0',
  sort: 0
});

const deptFormId = ref('');
const parentFromDept = ref('');

/**
 * 重置表单
 */
const onReset = () => {
  if (deptFormId) {
    loading.value = true;
    initFromData(deptFormId.value, parentFromDept.value)
    loading.value = false;

  }
};

/**
 * 初始化表单
 * @param roleId 角色id
 */
const initFromData = async (deptId: string, parentDept: string) => {
  console.log(111111111111, formData, parentDept, parentFromDept);
  if (!deptId) {
    formData.value = {
      deptId: '',
      deptName: '',
      parentDept: parentDept,
      sort: 0
    }
    parentFromDept.value = parentDept
    deptFormId.value = undefined
    return
  }
  deptFormId.value = deptId;
  const { code, result } = await deptInfo(deptId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const loading = ref(false)
const onSubmit = async ({ validateResult }: SubmitContext<PoemDept>) => {
  if (validateResult) {
    loading.value = true
    const api = formData.value.deptId ? editDept : addDept
    const res = await api(formData.value);
    loading.value = false
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success('提交成功');
      emit('submit-hook');
    } else {
      MessagePlugin.error(res.message);
    }
  }
};

defineExpose({
  formData,
  initFromData
})



</script>
<style lang="less"></style>