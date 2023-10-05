<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <t-card v-else class="menuManage" :bordered="false">
    <div>
      <t-button @click="handleAdd" v-auth="['menu']">添加根菜单</t-button>
    </div>
    <!-- 菜单表格组件 -->
    <MenuTable></MenuTable>
  </t-card>
  <t-dialog v-model:visible="menuVisible" :footer="false" width="600px" top="10">
    <menuFrom></menuFrom>
  </t-dialog>
</template>
<script setup lang="tsx">
import { computed, ref } from 'vue';
import MenuTable from './menuTable.vue';
import { useSettingStore } from '@/store';
import { useRoute } from 'vue-router';
import router from '@/router';
import { menuConfig } from './config';
import menuFrom from './menuFrom.vue';
const route = useRoute();
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
const handleAdd = () => {
  menuVisible.value = true
  // router.push(menuConfig.menuFromUrl)
};
// 弹框标识
const menuVisible = ref(false)
</script>

<style scoped lang="less">
.menuManage {
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
}
</style>
