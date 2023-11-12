<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="字典标签" name="dictLabel">
        <t-input v-model="formData.dictLabel" placeholder="请输入字典标签"></t-input>
      </t-form-item>
      <t-form-item label="字典值" name="dictValue">
        <t-input v-model="formData.dictValue" placeholder="请输入字典值"></t-input>
      </t-form-item>
      <t-form-item label="字典状态" name="status">
        <t-radio-group v-model="formData.status">
          <t-radio :value="0">正常</t-radio>
          <t-radio :value="1">停用</t-radio>
        </t-radio-group>
      </t-form-item>
      <t-form-item label="字典排序" name="dictSort">
        <t-input-number v-model="formData.dictSort" autoWidth />
      </t-form-item>
      <t-form-item label="备注" name="remark">
        <t-textarea v-model="formData.remark" placeholder="请输入备注"></t-textarea>
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
import { onMounted, ref } from 'vue'
import { PoemDictData } from '@/api/system/dict/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { addPoemDictData, editPoemDictData, PoemDictDataInfo } from '@/api/system/dict';
import { ResultEnum } from '@/enums/httpEnum';
import { fromPairs } from 'lodash';

const emit = defineEmits(['submit-hook'])
// const props = defineProps<{
//   dictType: string
// }>()

const FORM_RULES = ref<FormRules>({
  dictLabel: [{ required: true, message: '请输入字典标签', trigger: 'blur' }],
  dictValue: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemDictData>({
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
  const { code, result } = await PoemDictDataInfo(dictCode);
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
const onSubmit = async ({ validateResult }: SubmitContext<PoemDictData>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.dictCode ? editPoemDictData : addPoemDictData
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success('提交成功');
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