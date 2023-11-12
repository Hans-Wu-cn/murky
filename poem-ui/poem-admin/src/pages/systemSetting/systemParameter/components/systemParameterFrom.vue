<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item label="配置key" name="key">
        <t-input v-model="formData.key" placeholder="请输入配置key"></t-input>
      </t-form-item>
      <t-form-item label="配置值" name="value">
        <t-input v-model="formData.value" placeholder="请输入配置值"></t-input>
      </t-form-item>
      <t-form-item label="描述" name="describe">
        <t-textarea v-model="formData.describe" placeholder="请输入描述内容"></t-textarea>
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
import { FormRules, MessagePlugin, SubmitContext, TreeNodeModel, TreeNodeValue, } from 'tdesign-vue-next';
import { saveSystemParameter, updateSystemParameter, systemParameterInfo } from '@/api/systemSetting/systemParameter';
import { SystemParameter } from '@/api/systemSetting/systemParameter/types';
import { ResultEnum } from '@/enums/httpEnum';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  key: [{ required: true, message: '请输入配置key', trigger: 'blur' }],
  value: [{ required: true, message: '请输入配置值', trigger: 'blur' }],
})
// 表单对象
const formData = ref<SystemParameter>({
  id: '',
  key: '',
  value: '',
  describe: '',
});

const fromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (fromId.value) {
    initFromData(fromId.value)
  }
  loading.value = false

};



/**
 * 初始化表单
 */
const resetValue = ref({})// 记录重置表单数据

const initFromData = async (id: string) => {
  if (!id) {
    formData.value = {
      id: '',
      key: '',
      value: '',
      describe: '',
    }
    fromId.value = ''
    return
  }
  fromId.value = id;
  const { code, result } = await systemParameterInfo(id)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<SystemParameter>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.id ? updateSystemParameter : saveSystemParameter
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