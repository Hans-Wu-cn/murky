<template>
  <t-config-provider :global-config="globalConfig">
    <router-view :class="[mode]" />
  </t-config-provider>
</template>

<script setup lang="ts">
import {useTDesignLanguageStore} from '@/store/modules/tDesignI18n'
import { computed} from 'vue';
import { useSettingStore } from '@/store';
const store = useSettingStore();
const tDesighLangStore = useTDesignLanguageStore();

const mode = computed(() => {
  return store.displayMode;
});
const globalConfig = computed(() =>{
  return tDesighLangStore.globalConfig;
})
</script>
<style lang="less" scoped>
#nprogress .bar {
  background: var(--td-brand-color) !important;
}
</style>
