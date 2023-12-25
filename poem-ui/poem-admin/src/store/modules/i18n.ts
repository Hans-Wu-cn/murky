import { defineStore } from 'pinia';
import { ResultEnum } from '@/enums/httpEnum';
import i18n from '@/i18n'
import { getLanguage } from '@/api/system/i18n';
import { MessagePlugin } from 'tdesign-vue-next';
import { useUserStore } from '@/store/modules/user'
import { useTDesignLanguageStore } from '@/store/modules/tDesignI18n';
import { request } from '@/utils/request';

const I18N_TAG = import.meta.env.VITE_I18N_TAG
const langed: Object = {
  en: Object,
  zh_CN: Object,
};
export const useI18nStore = defineStore('i18n', {
  state: () => ({
    languages: {},
  }),
  getters: {
    getLanguages: (state) => {
      return state.languages;
    },
  },
  actions: {
    /**
     * 切换当前语言
     * @param lang 语言编码
     */
    async changeLanguage(lang: string) {
      const language = await this.getI18nLanguage(lang);
      i18n.global.locale = lang
      i18n.global.setLocaleMessage(lang, language)
      useUserStore().setLanguage(lang)
      useTDesignLanguageStore().setLanguage(lang)
      request.setHeader({ "Accept-Language": lang })

    },
    /**
     * 设置并缓存语言包
     * @param lang 语言编码
     */
    async setI18nLanguage(lang: string, language: any,) {
      Reflect.set(this.getLanguages, lang, language)
      i18n.global.setLocaleMessage(lang, language);
    },
    /**
     * 获取语言包
     * @param lang 语言编码
     * @returns 语言包内容
     */
    async getI18nLanguage(lang: string): Promise<any> {
      const languages = this.getLanguages;
      useTDesignLanguageStore().setLanguage(lang)
      if (!languages) {
        const { code, result } = await getLanguage(I18N_TAG, lang);
        if (ResultEnum.SUCCESS === code) {
          await this.setI18nLanguage(lang, result)
          return result
        }
      }
      const language = languages[lang];
      if (!language) {
        const { code, result } = await getLanguage(I18N_TAG, lang);
        if (ResultEnum.SUCCESS === code) {
          await this.setI18nLanguage(lang, result)
          return result;
        }
        throw new Error("系统没有内置该语言")
      }
      return language;
    },
  }
});
