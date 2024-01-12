<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item :label="$t('dept.label.name')" name="deptName">
        <t-input v-model="formData.deptName" :placeholder="$t('dept.label.pl.name')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('common.attribute.sort')" name="sort">
        <t-input-number :placeholder="$t('common.attribute.pl.sort')" v-model="formData.sort" />
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
import { ref } from 'vue'
import { DeptTree, Dept } from '@/api/system/dept/types'
import { FormRules, MessagePlugin, SubmitContext, } from 'tdesign-vue-next';
import { addDept, deptInfo, editDept } from '@/api/system/dept';
import { ResultEnum } from '@/enums/httpEnum';
import i18n from '@/i18n';
const emit = defineEmits(['submit-hook'])

const FORM_RULES = ref<FormRules>({
  deptName: [{ required: true, message: i18n.global.t('dept.label.name'), trigger: 'blur' }],
  sort: [{ required: true, message: i18n.global.t('common.attribute.pl.sort'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<DeptTree>({
  id: '',
  deptName: '',
  parentId: '0',
  sort: 0
});

const deptFormId = ref('');
const parentFromDept = ref('');

/**
 * 重置表单
 */
const onReset = () => {
  if (deptFormId) {
    formData.value = historyFormValue.value
  }
};

/**
 * 初始化表单
 * @param roleId 角色id
 */
const historyFormValue = ref({})
const initFromData = async (id: string, parentId: string) => {
  if (!id) {
    formData.value = {
      id: '',
      deptName: '',
      parentId: parentId,
      sort: 0
    }
    parentFromDept.value = parentId
    deptFormId.value = undefined
    return
  }
  deptFormId.value = id;
  const { code, result } = await deptInfo(id)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    historyFormValue.value = JSON.parse(JSON.stringify(result))
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const loading = ref(false)
const onSubmit = async ({ validateResult }: SubmitContext<Dept>) => {
  if (validateResult) {
    loading.value = true
    const api = formData.value.id ? editDept : addDept
    const res = await api(formData.value);
    loading.value = false
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
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