<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <div v-else class="menuManage" :bordered="false">
    <!-- 菜单表格组件 -->
    <TenantMenuTable></TenantMenuTable>
  </div>
</template>
<script setup lang="tsx">
import { computed, ref } from 'vue';
import TenantMenuTable from './components/tenantMenuTable.vue';
import { useSettingStore } from '@/store';
import { useRoute } from 'vue-router';
const route = useRoute();
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
</script>

<style scoped lang="less">
.menuManage {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
}
</style>
