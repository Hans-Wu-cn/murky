<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <div class="menuManage" v-else>
    <t-card :bordered="false">
      <div>
        <t-button @click="handleAdd">添加根菜单</t-button>
      </div>
      <!-- 菜单表格组件 -->
      <MenuTable></MenuTable>

      <!-- 弹出框 -->
      <t-dialog v-model:visible="dialogVisible" :header="dialogHeader">
        <menuFrom></menuFrom>
      </t-dialog>
    </t-card>
  </div>
</template>
<script setup lang="tsx">
import { computed, ref } from 'vue';
import menuFrom from './menuFrom.vue';
import MenuTable from './menuTable.vue';
import { useSettingStore } from '@/store';
import { useRoute } from 'vue-router';
import router from '@/router';
import { menuConfig } from './config';
const route = useRoute();
console.log(route)
const settingStore = useSettingStore();
const showBreadcrumbHeight = computed(() => {
  return settingStore.showBreadcrumb ? '46px' : '0px'
})
const dialogHeader = ref('');
const dialogVisible = ref(false);
const handleAdd = () => {
  router.push(menuConfig.menuFromUrl)
};
</script>

<style scoped lang="less">
.menuManage {
  // background: #fff;
  min-height: calc(100% - v-bind(showBreadcrumbHeight));
  display: flex;
  .t-card{
    width: 100%;
  }
}
</style>
