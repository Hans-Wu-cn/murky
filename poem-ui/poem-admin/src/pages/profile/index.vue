<template>
  <div class="profile-card">
    <t-loading :loading="loading" :text="$t('common.loading')" fullscreen />
    <t-space>
      <t-card title="个人信息" :bordered="false" hover-shadow :style="{ width: '400px' }">
        <t-divider></t-divider>
        <div>
          <span class="label">用户名</span>
          <span class="value">{{ userInfo?.userName }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">邮箱</span>
          <span class="value">{{ userInfo?.email }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">所属部门</span>
          <span class="value" v-for="(item, index) in userInfo.deptNameList">{{ index ===
            userInfo.deptNameList.length - 1 ? item : "/" + item }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">所属角色</span>
          <span class="value" v-for="(item, index) in userInfo.roleNameList">{{ index ==
            userInfo.roleNameList.length - 1 ? item : "/" + item }}</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">创建日期</span>
          <span class="value">{{ userInfo.createTime }}</span>
        </div>
        <t-divider></t-divider>
      </t-card>

      <t-card title="基本资料" :bordered="false" hover-shadow :style="{ width: '400px' }">
        <t-tabs :default-value="1">
          <t-tab-panel :value="1" label="基本资料">
            <t-form ref="form" style="margin-top: 10px;" :rules="FORM_RULES" :data="formData" :colon="true"
              :on-submit="onSubmit" :on-reset="initUserInfo">
              <t-form-item label="用户名称" name="userName">
                <t-input v-model="formData.userName" :placeholder="$t('user.label.pl.userName')"></t-input>
              </t-form-item>
              <t-form-item label="用户邮箱" name="email">
                <t-input v-model="formData.email" :placeholder="$t('user.label.pl.email')"></t-input>
              </t-form-item>
              <t-form-item label="性别" name="sex">
                <t-radio-group v-model="formData.sex">
                  <t-radio v-for="(item, key) in sexDictList" :key="item.dictValue" :value="Number(item.dictValue)">{{
                    $t(item.dictLabel) }}</t-radio>
                </t-radio-group>
              </t-form-item>

              <t-form-item>
                <t-space size="small">
                  <t-button theme="primary" type="submit">提交</t-button>
                  <t-button theme="default" variant="base" type="reset">重置</t-button>
                </t-space>
              </t-form-item>
            </t-form>
          </t-tab-panel>
          <t-tab-panel :value="2" label="修改密码">
            <t-form ref="form" style="margin-top: 10px;" :rules="FORM_RULES" :data="passwordFormData" :colon="true"
              :on-submit="onSubmit" :on-reset="initUserInfo">
              <t-form-item label="旧密码" name="oldPassword">
                <t-input v-model="passwordFormData.oldPassword" :placeholder="$t('user.label.pl.userName')"></t-input>
              </t-form-item>
              <t-form-item label="新密码" name="password">
                <t-input v-model="passwordFormData.password" :placeholder="$t('user.label.pl.email')"></t-input>
              </t-form-item>
              <t-form-item label="确定新密码" name="surePassword">
                <t-input v-model="passwordFormData.surePassword" :placeholder="$t('user.label.pl.email')"></t-input>
              </t-form-item>


              <t-form-item>
                <t-space size="small">
                  <t-button theme="primary" type="submit">提交</t-button>
                  <t-button theme="default" variant="base" type="reset">重置</t-button>
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
import { profileInfo, editProfile } from '@/api/auth';
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
