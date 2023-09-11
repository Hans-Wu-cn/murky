<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <t-card v-else class="menuManage" :bordered="false">
    <div>
      <t-button @click="handleAdd">添加根菜单</t-button>
    </div>
    <!-- 菜单表格组件 -->
    <MenuTable></MenuTable>
  </t-card>
</template>
<script setup lang="tsx">
import { computed, ref } from 'vue';
import MenuTable from './menuTable.vue';
import { useSettingStore } from '@/store';
import { useRoute } from 'vue-router';
import router from '@/router';
import { menuConfig } from './config';
const route = useRoute();
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
const handleAdd = () => {
  router.push(menuConfig.menuFromUrl)
};
</script>

<style scoped lang="less">
.menuManage {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
}
</style>
