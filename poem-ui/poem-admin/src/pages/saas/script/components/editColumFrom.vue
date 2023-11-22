<template>
  <div>
    <t-tabs :default-value="1">
      <t-tab-panel :value="1" :label="$t('script.table.attribute.ddl')">
        <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
          @submit="onSubmit">

          <t-form-item>
            <t-space size="small">
              <t-button theme="primary" type="submit" :loading="loading">{{ $t('common.button.submit') }}</t-button>
              <t-button theme="default" variant="base" type="reset" :loading="loading">{{ $t('common.button.reset1')
              }}</t-button>
            </t-space>
          </t-form-item>
        </t-form>
      </t-tab-panel>
      <t-tab-panel :value="2" :label="$t('script.table.attribute.data')">
        <p style="margin: 20px">选项卡2内容区</p>
      </t-tab-panel>
    </t-tabs>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PoemSaasScriptTable } from '@/api/saas/script/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { tableInfo, tableSave, tableEdit } from '@/api/saas/script';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemSaasMenu } from '@/api/saas/menu/types';
import { saasScriptTagDictHook } from '@/hooks/dict';
import { PoemDictData } from '@/api/system/dict/types';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  tableName: [{ required: true, message: i18n.global.t('script.table.attribute.pl.tableName'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemSaasScriptTable>({
  tableName: '',
  describe: '',
  tag: 0,
  status: 0
});

const scriptTag = ref<PoemDictData[]>()

const tableFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (tableFromId.value) {
    initFromData(tableFromId.value)
  }
  loading.value = false

};

/**
 * 初始化表单
 * @param saasRoleId 角色id
 */
const resetValue = ref({})// 记录重置表单数据
const initFromData = async (tableId: string) => {
  if (!tableId) {
    formData.value = {
      tableName: '',
      describe: '',
      tag: 0,
      status: 0
    }
    tableFromId.value = undefined
    return
  }
  tableFromId.value = tableId;
  const { code, result } = await tableInfo(tableId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PoemSaasScriptTable>) => {
  if (validateResult === true) {
    loading.value = true
    const api = formData.value.tableId ? tableEdit : tableSave
    const res = await api(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      emit('submit-hook');
    }
    loading.value = false
  }
};

defineExpose({
  initFromData
})

onMounted(async () => {
  scriptTag.value = await saasScriptTagDictHook()
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