<template>
  <div class="profile-card">
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

      <t-card title="个人信息" :bordered="false" hover-shadow :style="{ width: '400px' }">
        <t-divider></t-divider>
        <div>
          <span class="label">用户名</span>
          <span class="value">用户名</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">昵称</span>
          <span class="value">昵称</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">邮箱</span>
          <span class="value">邮箱</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">所属部门</span>
          <span class="value">所属部门</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">所属角色</span>
          <span class="value">所属角色</span>
        </div>
        <t-divider></t-divider>
        <div>
          <span class="label">创建日期</span>
          <span class="value">创建日期</span>
        </div>
        <t-divider></t-divider>
      </t-card>
    </t-space>
  </div>
</template>


<script setup lang="tsx">
import { profileInfo } from '@/api/auth';
import { ProfileInfo } from '@/api/auth/types';
import { ResultEnum } from '@/enums/httpEnum';
import { useUserStore } from '@/store';
import { computed, onMounted, reactive, ref } from 'vue';

const userInfo = ref<ProfileInfo>({
  userName: '',
  email: '',
  roleNameList: [],
  deptNameList: [],
  createTime: 0,
});
const initUserInfo = async () => {
  const { code, result } = await profileInfo();
  if (ResultEnum.SUCCESS === code) {
    userInfo.value = result
  }
}
onMounted(async () => {
  await initUserInfo();
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
