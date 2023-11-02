<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_I18N" :data="formData" @reset="onReset" @submit="onSubmit">
      <t-form-item label="i18n编码" name="i18nKey">
        <t-input v-model="formData.i18nKey" placeholder="请输入i18n编码"></t-input>
      </t-form-item>
      <t-form-item label="i18n标签" name="college">
        <t-select v-model="formData.i18nTag">
          <t-option v-for="(item, index) in i18nTagSelectOption" :key="index" :value="item.dictValue"
            :label="item.dictLabel">
            {{ item.dictLabel }}
          </t-option>
        </t-select>
      </t-form-item>
      <t-form-item v-for="(item, index) in formData.i18nInputs" :label="item.language" :name="item.language">
        <t-input v-model="item.i18nValue" :placeholder='`请输入${item.language}对应值`'></t-input>
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
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { I18nData, I18nInputs } from '@/api/system/i18n/types';
import { saveI18n, i18nInfo, updateI18n } from '@/api/system/i18n';
import { ResultEnum } from '@/enums/httpEnum';
import { i18nDictHook, i18nTagDictHook } from '@/hooks/dict';
import { PoemDictData } from '@/api/system/dict/types';

const emit = defineEmits(['submit-hook'])


const FORM_I18N = ref<FormRules>({
  dictLabel: [{ required: true, message: '请输入字典标签', trigger: 'blur' }],
  dictValue: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
})


// 记录重置表单数据
const resetValue = ref({})
const i18nFromKey = ref('');
const i18nFromTag = ref('');
const loading = ref(false);
let api = saveI18n
const i18nTagSelectOption = ref<PoemDictData[]>()

// 表单对象
const formData = ref<I18nData>({
  i18nKey: '',
  i18nTag: '',
  i18nInputs: []
});


/**
 * 初始化表单
 * @param dictType 字典类型
 */
const initFromData = async (i18nKey: string, i18nTag: string) => {
  if (!i18nKey) {
    // 防止值多次改变导致表单闪烁
    formData.value = {
      i18nKey: '',
      i18nTag: formData.value.i18nTag = i18nTagSelectOption.value[0].dictValue,
      i18nInputs: formData.value.i18nInputs
    }
    i18nFromKey.value = i18nKey
    i18nFromTag.value = i18nTag
    await loadI18n()
    api = saveI18n;
    return
  }
  i18nFromKey.value = i18nKey
  i18nFromTag.value = i18nTag
  formData.value.i18nKey = i18nKey;
  formData.value.i18nTag = i18nTag;
  const { code, result } = await i18nInfo({ i18nKey, i18nTag });
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
    api = updateI18n;
  }
}

/**
 * 重置表单
 */
const onReset = async () => {
  loading.value = true
  if (i18nFromKey.value) {
    initFromData(i18nFromKey.value, i18nFromTag.value)
  } else {
    await loadI18n()
  }
  loading.value = false
};

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemDictData>) => {
  if (validateResult === true) {
    loading.value = true
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

const loadI18n = async () => {
  const i18ns = await i18nDictHook();
  const i18nInputs: Array<I18nInputs> = [];
  i18ns.forEach(item => {
    i18nInputs.push({
      language: item.dictValue,
      i18nValue: undefined
    })
  })
  formData.value.i18nInputs = i18nInputs
}

const loadI18nTag = async () => {
  const i18nTags = await i18nTagDictHook();
  i18nTagSelectOption.value = i18nTags
  formData.value.i18nTag = i18nTags[0].dictValue
}

onMounted(async () => {
  await loadI18nTag()
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