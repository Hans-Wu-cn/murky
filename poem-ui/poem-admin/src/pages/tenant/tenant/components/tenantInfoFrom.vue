<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @submit="onSubmit">
      <t-form-item :label="$t('tenant.label.name')" name="tenantName">
        <t-input v-model="formData.tenantName" :placeholder="$t('tenant.label.pl.name')"
          :disabled="disabledFrom"></t-input>
      </t-form-item>
      <t-form-item :label="$t('permissionGroup.label')" name="groupId">
        <t-select v-model="formData.groupId" :placeholder="$t('permissionGroup.label.from.pl')" :disabled="disabledFrom"
          filterable>
          <t-option v-for="(item, index) in groupSelectOptions" :key="index" :value="item.groupId"
            :label="item.groupName">
            {{ item.groupName }}
          </t-option>
        </t-select>
      </t-form-item>
      <t-form-item :label="$t('tenant.label.expires')" name="expires">
        <t-date-picker v-model="formData.expires" :placeholder="$t('tenant.label.pl.expires')" :disabled="disabledFrom"
          :disable-date="{
            before: new Date(),
          }" enableTimePicker />
      </t-form-item>
      <t-form-item :label="$t('common.attribute.describe')" name="describe">
        <t-textarea v-model="formData.describe" :placeholder="$t('common.attribute.pl.describe')" :disabled="disabledFrom"
          autosize />
      </t-form-item>
      <t-form-item :label="$t('common.attribute.createTime')" name="createTime">
        <t-date-picker v-model="formData.createTime" disabled :disable-date="{
          before: new Date(),
        }" enableTimePicker />
      </t-form-item>
      <t-form-item>
        <t-space size="small">
          <t-button theme="primary" type="button" @click="onEditHandler" :loading="loading">{{
            $t('common.button.edit')
          }}</t-button>
          <t-popconfirm theme="default" :content="$t('common.label.sureSubmit')">
            <t-button v-if="!disabledFrom" theme="primary" type="submit" :loading="loading">{{
              $t('common.button.submit')
            }}</t-button>
          </t-popconfirm>

        </t-space>
      </t-form-item>
    </t-form>
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PermissionGroup } from '@/api/tenant/permissionGroup/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { tenantInfo, editTenant } from '@/api/tenant/tenant';
import { ResultEnum } from '@/enums/httpEnum';
import { Tenant } from '@/api/tenant/tenant/types';
import i18n from '@/i18n';
import { permissionGroupList } from '@/api/tenant/permissionGroup';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  tenantName: [{ required: true, message: i18n.global.t('tenant.label.pl.name'), trigger: 'blur' }],
  groupId: [{ required: true, message: i18n.global.t('permissionGroup.label.from.pl'), trigger: 'blur' }],
  expires: [{ required: true, message: i18n.global.t('tenant.label.pl.expires'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<Tenant>({
  tenantId: '',
  groupId: '',
  tenantName: '',
  expires: '',
  describe: '',
  status: 0,
  groupName: '',
  adminUser: '',
});
const groupSelectOptions = ref<PermissionGroup[]>()
const loading = ref(false);
const tenantFromId = ref('')
const disabledFrom = ref(true)

/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  initFromData(tenantFromId.value)
  loading.value = false
};


/**
 * 初始化表单
 * @param tenantId 租户id
 */
const resetValue = ref({})// 记录重置表单数据

const initFromData = async (tenantId: string) => {
  tenantFromId.value = tenantId;
  const { code, result } = await tenantInfo(tenantId)
  if (ResultEnum.SUCCESS === code) {
    formData.value = result
    resetValue.value = result
  }

}

const onEditHandler = () => {
  if (!disabledFrom.value) {
    onReset()
  }
  disabledFrom.value = !disabledFrom.value

}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PermissionGroup>) => {
  if (validateResult === true) {
    loading.value = true
    console.debug(formData)
    const res = await editTenant(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      emit('submit-hook');
    }
    loading.value = false
  }
};

onMounted(async () => {
  disabledFrom.value = true
  const { code, result } = await permissionGroupList()
  if (ResultEnum.SUCCESS === code) {
    groupSelectOptions.value = result
  }
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