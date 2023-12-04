import { defineStore } from 'pinia';
import { ResultEnum } from '@/enums/httpEnum';
import i18n from '@/i18n'
import { getLanguage } from '@/api/systemSetting/i18n';
import { MessagePlugin } from 'tdesign-vue-next';
import { useUserStore } from '@/store/modules/user'
import { useTDesignLanguageStore } from '@/store/modules/tDesignI18n';
import { request } from '@/utils/request';
import { PoemDictData } from '@/api/system/dict/types';
import { dict } from '@/api/system/dict';

export const dictKey = {
  i18n: 'i18n:language',
  i18nTag: 'i18n:tag',
  saasScriptTag: 'saas:script:tag',
};

export const useDictStore = defineStore('dict', {
  state: () => ({
    dictCache: new Map<string, Array<PoemDictData>>()
  }),
  getters: {
    getDictCache: (state) => {
      return state.dictCache;
    },
  },
  actions: {
    /**
     * 国际化标签字典数据
     * @returns 
     */
    async saasScriptTagDictHook(): Promise<PoemDictData[]> {
      return await this.dictFunction(dictKey.saasScriptTag)
    },

    /**
     * 国际化标签字典数据
     * @returns 
     */
    async i18nDictHook(): Promise<PoemDictData[]> {
      return await this.dictFunction(dictKey.i18n)
    },

    /**
     * 国际化标签字典数据
     * @returns 
     */
    async i18nTagDictHook(): Promise<PoemDictData[]> {
      return await this.dictFunction(dictKey.i18nTag)
    },

    /**
     * 字典函数通用方法
     * @param key 对应字典的字典key
     * @returns 字典集合
     */
    async dictFunction(key: string): Promise<PoemDictData[]> {
      const cache = this.dictCache.get(key);
      if (cache) {
        console.debug("cache")
        return cache
      }
      const { code, result, message } = await dict(key);
      if (ResultEnum.SUCCESS === code) {
        this.dictCache.set(key, result)
        return result
      }
      return []
    },
  }
});
