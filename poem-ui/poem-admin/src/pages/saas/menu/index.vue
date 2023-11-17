<template>
  <RouterView v-if="route.meta.hidden"></RouterView>
  <div v-else class="menuManage" :bordered="false">
    <!-- 菜单表格组件 -->
    <SaasMenuTable>
      <t-button @click="handleAdd" v-auth="['saasMenu:add']">{{ $t('menu.button.add') }}</t-button>
    </SaasMenuTable>
  </div>
  <t-dialog v-model:visible="menuVisible" :footer="false" width="600px" top="10">
    <SaasMenuFrom></SaasMenuFrom>
  </t-dialog>
</template>
<script setup lang="tsx">
import { computed, ref } from 'vue';
import SaasMenuTable from './components/saasMenuTable.vue';
import { useSettingStore } from '@/store';
import { useRoute } from 'vue-router';
import SaasMenuFrom from './components/saasMenuFrom.vue';
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
