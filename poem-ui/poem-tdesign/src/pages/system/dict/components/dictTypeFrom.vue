<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="字典名称" name="dictName">
        <t-input v-model="formData.dictName" placeholder="请输入字典名称"></t-input>
      </t-form-item>
      <t-form-item label="字典类型" name="dictType">
        <t-input v-model="formData.dictType" placeholder="请输入字典类型"></t-input>
      </t-form-item>
      <t-form-item label="字典状态" name="status">
        <t-radio-group v-model="formData.status">
          <t-radio :value="0">正常</t-radio>
          <t-radio :value="1">停用</t-radio>
        </t-radio-group>
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
import { PoemDictType } from '@/api/dict/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { addPoemDictType, editPoemDictType, PoemDictTypeInfo } from '@/api/dict';
import { ResultEnum } from '@/enums/httpEnum';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
  dictType: [{ required: true, message: '请输入字典类型', trigger: 'blur' }],
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