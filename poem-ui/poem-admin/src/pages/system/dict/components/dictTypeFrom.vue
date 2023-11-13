<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item :label="$t('dict.label.dictName')" name="dictName">
        <t-input v-model="formData.dictName" :placeholder="$t('dict.label.pl.dictName')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('dict.label.dictType')" name="dictType">
        <t-input v-model="formData.dictType" :placeholder="$t('dict.label.pl.dictType')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('common.label.status')" name="status">
        <t-radio-group v-model="formData.status">
          <t-radio :value="0">{{ $t('common.label.status.0') }}</t-radio>
          <t-radio :value="1">{{ $t('common.label.status.1') }}</t-radio>
        </t-radio-group>
      </t-form-item>
      <t-form-item :label="$t('common.attribute.describe')" name="remark">
        <t-textarea v-model="formData.remark" :placeholder="$t('common.attribute.pl.describe')"></t-textarea>
      </t-form-item>
      <t-form-item>
        <t-space size="small">
          <t-button theme="primary" type="submit" :loading="loading">{{ $t('common.button.submit') }}</t-button>
          <t-button theme="default" variant="base" type="reset" :loading="loading">{{
            $t('common.button.reset1') }}</t-button>
        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PoemDictType } from '@/api/system/dict/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { addPoemDictType, editPoemDictType, PoemDictTypeInfo } from '@/api/system/dict';
import { ResultEnum } from '@/enums/httpEnum';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  dictName: [{ required: true, message: i18n.global.t('dict.label.pl.dictName'), trigger: 'blur' }],
  dictType: [{ required: true, message: i18n.global.t('dict.label.pl.dictType'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemDictType>({
  dictTypeId: '',
  dictName: '',
  dictType: '',
  status: 0,
});
// 记录重置表单数据
const resetValue = ref({})
const dictTypeFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (dictTypeFromId.value) {
    initFromData(dictTypeFromId.value)
  }
  loading.value = false

};

/**
 * 初始化表单
 * @param dictTypeId 字典类型Id
 */
const initFromData = async (dictTypeId: string) => {
  console.log(dictTypeId)
  if (!dictTypeId) {
    formData.value = {
      dictTypeId: '',
      dictName: '',
      dictType: '',
      status: 0,
      remark: ''
    }
    dictTypeFromId.value = undefined
    return
  }
  dictTypeFromId.value = dictTypeId;
  const { code, result } = await PoemDictTypeInfo(dictTypeId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemDictType>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.dictTypeId ? editPoemDictType : addPoemDictType
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