<template>
  <div class="profile-card">
    <t-space>
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


<script lang="ts">
export default {
  name: 'Profile',
};
</script>
<script setup lang="tsx">

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
