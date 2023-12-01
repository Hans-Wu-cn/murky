<template>
  <div>
    <t-form ref="form" colon reset-type="initial" :rules="FORM_RULES" :data="formData" @reset="onReset"
      @submit="onSubmit">
      <t-form-item :label="$t('tenant.label.name')" name="tenantName">
        <t-input v-model="formData.tenantName" :placeholder="$t('tenant.label.pl.name')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('permissionGroup.label')" name="groupId">
        <t-select v-model="formData.groupId" :placeholder="$t('permissionGroup.label.from.pl')" filterable>
          <t-option v-for="(item, index) in groupSelectOptions" :key="index" :value="item.groupId"
            :label="item.groupName">
            {{ item.groupName }}
          </t-option>
        </t-select>
      </t-form-item>
      <t-form-item :label="$t('tenant.label.account')" name="account">
        <t-input v-model="formData.account" :placeholder="$t('tenant.label.pl.account')"></t-input>
      </t-form-item>
      <t-form-item :label="$t('tenant.label.password')" name="password" type="password">
        <t-input v-model="formData.password" :placeholder="$t('tenant.label.pl.password')" type="password">
          <template #prefix-icon>
            <lock-on-icon />
          </template>
        </t-input>
      </t-form-item>
      <t-form-item :label="$t('tenant.label.confirmPassword')" name="confirmPassword" type="password">
        <t-input v-model="formData.confirmPassword" :placeholder="$t('tenant.label.pl.confirmPassword')" type="password">
          <template #prefix-icon>
            <lock-on-icon />
          </template></t-input>
      </t-form-item>
      <t-form-item :label="$t('tenant.label.expires')" name="expires">
        <t-date-picker v-model="formData.expires" :placeholder="$t('tenant.label.pl.expires')" :disable-date="{
          before: new Date(),
        }" enableTimePicker />
      </t-form-item>
      <t-form-item :label="$t('common.attribute.describe')" name="describe">
        <t-textarea v-model="formData.describe" :placeholder="$t('common.attribute.pl.describe')" autosize />
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
import { onMounted, ref } from 'vue'
import { PermissionGroup } from '@/api/tenant/permissionGroup/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { LockOnIcon } from 'tdesign-icons-vue-next';
import { addPoemTenant } from '@/api/tenant/tenant';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemTenantFrom } from '@/api/tenant/tenant/types';
import i18n from '@/i18n';
import { permissionGroupList } from '@/api/tenant/permissionGroup';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  tenantName: [{ required: true, message: i18n.global.t('tenant.label.pl.name'), trigger: 'blur' }],
  groupId: [{ required: true, message: i18n.global.t('permissionGroup.label.from.pl'), trigger: 'blur' }],
  account: [{ required: true, message: i18n.global.t('tenant.label.pl.account'), trigger: 'blur' }],
  password: [{ required: true, message: i18n.global.t('tenant.label.pl.password'), trigger: 'blur' }],
  confirmPassword: [{ required: true, message: i18n.global.t('tenant.label.pl.confirmPassword'), trigger: 'blur' }, {
    validator: (val) => new Promise((resolve) => {
      const timer = setTimeout(() => {
        resolve(formData.value.password === val);
        clearTimeout(timer);
      });
    }), message: i18n.global.t('tenant.label.re.confirmPassword')
    , trigger: 'change'
  }],
  expires: [{ required: true, message: i18n.global.t('tenant.label.pl.expires'), trigger: 'blur' }],
})
// 表单对象
const formData = ref<PoemTenantFrom>({
  groupId: '',
  tenantName: '',
  account: '',
  password: '',
  confirmPassword: '',
  expires: '',
  describe: '',
  status: 0,
});
const groupSelectOptions = ref<PermissionGroup[]>()
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  initFromData()
  loading.value = false

};

/**
 * 初始化表单
 * @param tenantId 租户id
 */
const initFromData = async () => {
  formData.value = {
    groupId: '',
    tenantName: '',
    account: '',
    password: '',
    confirmPassword: '',
    expires: '',
    describe: '',
    status: 0,
  }
}

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<PermissionGroup>) => {
  if (validateResult === true) {
    loading.value = true
    console.debug(formData)
    const res = await addPoemTenant(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      emit('submit-hook');
    }
    loading.value = false
  }
};

onMounted(async () => {
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