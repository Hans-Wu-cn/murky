<template>
  <div>
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
  </div>
</template>
<script setup lang="tsx">
import { onMounted, ref } from 'vue'
import { PermissionGroup } from '@/api/tenant/permissionGroup/types'
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { addPoemTenant } from '@/api/tenant/tenant';
import { ResultEnum } from '@/enums/httpEnum';
import { PoemTenantFrom } from '@/api/tenant/tenant/types';
import i18n from '@/i18n';

const emit = defineEmits(['submit-hook'])
const FORM_RULES = ref<FormRules>({
  groupName: [{ required: true, message: '请输入权限组名', trigger: 'blur' }],
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
const tenantFromId = ref('');
const loading = ref(false);
/**
 * 重置表单
 */
const onReset = () => {
  loading.value = true
  if (tenantFromId.value) {
    initFromData()
  }
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
    const res = await addPoemTenant(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      emit('submit-hook');
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