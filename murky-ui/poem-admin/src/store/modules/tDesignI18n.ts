import { defineStore } from 'pinia';
import zhConfig from 'tdesign-vue-next/es/locale/zh_CN';

import {getTdesignLanguage} from '@/utils/lang'

export const useTDesignLanguageStore = defineStore('tDesignLanguage', {
    state: () => ({
      globalConfig: zhConfig,
    }),
    actions: {
      setLanguage(language: string) {
        this.globalConfig = getTdesignLanguage(language)
      },
    },
  });