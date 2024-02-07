<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item :label="$t('dictData.attribute.dictLabel')" name="dictLabel">
        <t-input-adornment :prepend="$t(formData.dictLabel)">
          <t-input v-model="formData.dictLabel" :placeholder="$t('dictData.label.pl.dictLabel')"></t-input>
        </t-input-adornment>
      </t-form-item>
      <t-form-item :label="$t('dictData.attribute.dictValue')" name="dictValue">
        <t-input v-model="formData.dictValue" :placeholder="$t('dictData.label.pl.dictValue')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('common.label.status')" name="status">
        <t-radio-group v-model="formData.status">
          <t-radio :value="0">{{ $t('common.label.status.0') }}</t-radio>
          <t-radio :value="1">{{ $t('common.label.status.1') }}</t-radio>
        </t-radio-group>
      </t-form-item>
      <t-form-item :label="$t('dictData.label.sort')" name="dictSort">
        <t-input-number v-model="formData.dictSort" autoWidth />
      </t-form-item>
      <t-form-item :label="$t('common.attribute.describe')" name="remark">
        <t-textarea v-model="formData.remark" :placeholder="$t('common.attribute.pl.describe')"></t-textarea>
      </t-form-item>
      <t-form-item>
        <t-space size=" small">
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
import { DictData } from '@/api/system/dict/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { addDictData, editDictData, dictDataInfo } from '@/api/system/dict';
import { ResultEnum } from '@/enums/httpEnum';
import { fromPairs } from 'lodash';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
// const props = defineProps<{
//   dictType: string
// }>()

const FORM_RULES = ref<FormRules>({
  dictLabel: [{ required: true, message: i18n.global.t('dictData.label.pl.dictLabel'), trigger: 'blur' }],
  dictValue: [{ required: true, message: i18n.global.t('dictData.label.pl.dictValue'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<DictData>({
  dictSort: 0,
  dictLabel: '',
  dictValue: '',
  dictType: '',
  status: 0
});
// 记录重置表单数据
const resetValue = ref({})
const dictDataFromId = ref('');
const dictDataFromType = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (dictDataFromId.value) {
    initFromData(dictDataFromId.value, dictDataFromType.value)
  }
  loading.value = false

};

/**
 * 初始化表单
 * @param dictType 字典类型
 */
const initFromData = async (dictCode: string | undefined, dictType: string) => {
  console.log(dictCode, dictType)
  console.log(!dictCode, dictType)
  if (!dictCode) {
    formData.value = {
      dictSort: 0,
      dictLabel: '',
      dictValue: '',
      dictType: dictType,
      status: 0
    }
    dictDataFromType.value = dictType
    dictDataFromId.value = undefined;
    return
  }
  dictDataFromType.value = dictType;
  dictDataFromId.value = dictCode;
  formData.value.dictType = dictType;
  formData.value.dictCode = dictCode;
  const { code, result } = await dictDataInfo(dictCode);
  console.log(code, result)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<DictData>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.dictCode ? editDictData : addDictData
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