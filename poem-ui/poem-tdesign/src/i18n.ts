import { createI18n } from 'vue-i18n';
import { getLanguage } from '@/api/systemSetting/i18n';
import { i18nDictHook } from './hooks/dict';
import { ResultEnum } from './enums/httpEnum';
import { useUserStore, useI18nStore } from '@/store';



const i18n = createI18n({
  locale: 'en', // 默认语言
  fallbackLocale: 'en', // 如果找不到当前语言的翻译，就使用默认语言
  messages: {}, // 空对象，稍后会通过接口获取数据并填充这里
});

/**
 * 初始化语言包
 */
export const initLanguage = async () => {
  const { setI18nLanguage } = useI18nStore()
  const i18ns = await i18nDictHook()
  const defaultLanguage = i18ns[0].dictValue;
  // 加载默认语言
  const { code, result } = await getLanguage("admin", defaultLanguage);
  if (ResultEnum.SUCCESS === code) {
    i18n.global.setLocaleMessage(defaultLanguage, result);
    i18n.global.fallbackLocale = defaultLanguage
    await setI18nLanguage(defaultLanguage, result);
  }
  const userStore = useUserStore();
  // 如果用户有设置自己的语言则优先使用,否则使用浏览器语言
  if (!userStore.userInfo.language) {
    // 获取浏览器语言
    const lang = navigator.language
    const { code, result } = await getLanguage("admin", lang);
    if (ResultEnum.SUCCESS === code) {
      i18n.global.setLocaleMessage(lang, result);
      // await setI18nLanguage(lang, result);
      i18n.global.locale = lang
    }
  } else {
    // 获取用户语言
    const lang = userStore.userInfo.language
    const { code, result } = await getLanguage("admin", lang);
    if (ResultEnum.SUCCESS === code) {
      i18n.global.setLocaleMessage(lang, result);
      i18n.global.locale = lang
      await setI18nLanguage(lang, result);
    }
  }
}
export default i18n;
