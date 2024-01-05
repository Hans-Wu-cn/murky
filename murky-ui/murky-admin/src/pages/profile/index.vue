<template>
  <div class="profile-card">
    <t-loading :loading="loading" :text="$t('common.loading')" fullscreen />
    <t-space>
      <t-card :title="$t('profile.userInfo')" :bordered="false" hover-shadow :style="{ width: '400px' }">
        <t-divider></t-divider>
        <div>
          <span class="label">{{ $t('user.label.userName') }}</span>
          <span class="value">{{ userInfo?.userName }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">{{ $t('user.label.email') }}</span>
          <span class="value">{{ userInfo?.email }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">{{ $t('user.label.dept') }}</span>
          <span class="value" v-for="(item, index) in userInfo.deptNameList">{{ index ===
            userInfo.deptNameList.length - 1 ? item : "/" + item }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">{{ $t('user.label.role') }}</span>
          <span class="value" v-for="(item, index) in userInfo.roleNameList">{{ index ==
            userInfo.roleNameList.length - 1 ? item : "/" + item }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">{{ $t('common.attribute.createTime') }}</span>
          <span class="value">{{ userInfo.createTime }}</span>
        </div>
        <t-divider></t-divider>
      </t-card>

      <t-card :title="$t('profile.base')" :bordered="false" hover-shadow :style="{ width: '800px' }">
        <t-tabs :default-value="1">
          <t-tab-panel :value="1" :label="$t('profile.base')">
            <t-form ref="form" style="margin-top: 10px;" :rules="FORM_RULES" :data="formData" :colon="true"
              :on-submit="onSubmit" :on-reset="initUserInfo">
              <t-form-item :label="$t('user.label.userName')" name="userName">
                <t-input v-model="formData.userName" :placeholder="$t('user.label.pl.userName')"></t-input>
              </t-form-item>
              <t-form-item :label="$t('user.label.email')" name="email">
                <t-input v-model="formData.email" :placeholder="$t('user.label.pl.email')"></t-input>
              </t-form-item>
              <t-form-item :label="$t('user.label.sex')" name="sex">
                <t-radio-group v-model="formData.sex">
                  <t-radio v-for="(item, key) in sexDictList" :key="item.dictValue" :value="Number(item.dictValue)">{{
                    $t(item.dictLabel) }}</t-radio>
                </t-radio-group>
              </t-form-item>

              <t-form-item>
                <t-space size="small">
                  <t-button theme="primary" type="submit">{{ $t('common.button.submit') }}</t-button>
                  <t-button theme="default" variant="base" type="reset">{{ $t('common.button.reset1') }}</t-button>
                </t-space>
              </t-form-item>
            </t-form>
          </t-tab-panel>
          <t-tab-panel :value="2" :label="$t('profile.editPassword')">
            <t-form ref="form" style="margin-top: 10px;" :rules="FORM_RULES" :data="passwordFormData" :colon="true"
              :on-submit="onPasswordSubmit" labelWidth="width-content">
              <t-form-item :label="$t('profile.oldPassword')" name="oldPassword">
                <t-input v-model="passwordFormData.oldPassword" type="password"
                  :placeholder="$t('profile.pl.oldPassword')">
                  <template #prefix-icon>
                    <lock-on-icon />
                  </template>
                </t-input>
              </t-form-item>
              <t-form-item :label="$t('profile.newPassword')" name="password">
                <t-input v-model="passwordFormData.password" type="password" :placeholder="$t('profile.pl.newPassword')">
                  <template #prefix-icon>
                    <lock-on-icon />
                  </template>
                </t-input>
              </t-form-item>
              <t-form-item :label="$t('profile.surePassword')" name="surePassword">
                <t-input v-model="passwordFormData.surePassword" type="password"
                  :placeholder="$t('profile.pl.surePassword')">
                  <template #prefix-icon>
                    <lock-on-icon />
                  </template>
                </t-input>
              </t-form-item>


              <t-form-item>
                <t-space size="small">
                  <t-button theme="primary" type="submit">{{ $t('common.button.submit') }}</t-button>
                  <t-button theme="default" variant="base" type="reset">{{ $t('common.button.reset1') }}</t-button>
                </t-space>
              </t-form-item>
            </t-form>
          </t-tab-panel>
        </t-tabs>
      </t-card>
    </t-space>
  </div>
</template>


<script setup lang="tsx">
import { profileInfo, editProfile, editPassword } from '@/api/auth';
import { ProfileInfo, ProfileFrom, EditPasswordFrom } from '@/api/auth/types';
import { DictData } from '@/api/system/dict/types';
import { ResultEnum } from '@/enums/httpEnum';
import i18n from '@/i18n';
import { useDictStore, useUserStore } from '@/store';
import { FormRules, MessagePlugin, SubmitContext } from 'tdesign-vue-next';
import { computed, onMounted, reactive, ref } from 'vue';
const { sexDictHook } = useDictStore()
const FORM_RULES = ref<FormRules>({
  userName: [{ required: true, message: i18n.global.t('user.label.pl.userName') }],
  email: [{ required: true, message: i18n.global.t('user.label.pl.email') }, { email: { ignore_max_length: true }, message: i18n.global.t('user.label.valid.email') }],
  sex: [{ required: true, message: i18n.global.t('user.label.pl.sex') }],

  oldPassword: [{ required: true, message: i18n.global.t('user.label.pl.sex') }],
  password: [{ required: true, message: i18n.global.t('user.label.pl.sex') }],
  surePassword: [{ required: true, message: i18n.global.t('user.label.pl.sex') }],
});
const loading = ref(false);
const userInfo = ref<ProfileInfo>({
  userName: '',
  email: '',
  sex: 2,
  roleNameList: [],
  deptNameList: [],
  createTime: 0,
});
const formData = ref<ProfileFrom>({
  userName: '',
  email: '',
  sex: 2
});

const passwordFormData = ref<EditPasswordFrom>({
  oldPassword: '',
  password: '',
  surePassword: ''
})

const sexDictList = ref<DictData[]>()

const initUserInfo = async () => {
  loading.value = true
  const { code, result } = await profileInfo();
  if (ResultEnum.SUCCESS === code) {
    userInfo.value = result
    formData.value = { ...userInfo.value }
  }
  loading.value = false
}


/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onSubmit = async ({ validateResult }: SubmitContext<ProfileInfo>) => {
  console.log(formData.value)
  if (validateResult === true) {
    loading.value = true
    const res = await editProfile(formData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      await initUserInfo();
    }
    loading.value = false
  }
};

/**
 * 表单提交事件
 * @param param0 表单验证
 */
const onPasswordSubmit = async ({ validateResult }: SubmitContext<ProfileInfo>) => {
  console.log(passwordFormData.value)
  if (validateResult === true) {
    loading.value = true
    const res = await editPassword(passwordFormData.value);
    if (res.code === ResultEnum.SUCCESS) {
      MessagePlugin.success(i18n.global.t('common.message.submitSuccess'));
      await initUserInfo();
    }
    loading.value = false
  }
};

onMounted(async () => {
  await initUserInfo();
  sexDictList.value = await sexDictHook();
  console.log(userInfo.value)
});
</script>

<style lang="less" scoped>
.profile-card {
  :deep(.t-divider) {
    margin: 10px 0;
  }

  .label {
    float: left;
  }

  .value {
    float: right;
  }
}
</style>
