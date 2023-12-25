import { createI18n } from 'vue-i18n';
import { getLanguage } from '@/api/system/i18n';
import { ResultEnum } from './enums/httpEnum';
import { useUserStore, useI18nStore, useDictStore } from '@/store';
import { request } from './utils/request';
const I18N_TAG = import.meta.env.VITE_I18N_TAG

const i18n = createI18n({
  locale: 'en', // 默认语言
  fallbackLocale: 'en', // 如果找不到当前语言的翻译，就使用默认语言
  messages: {}, // 空对象，稍后会通过接口获取数据并填充这里
  silentFallbackWarn: true,
  silentTranslationWarn: true
});

/**
 * 初始化语言包
 */
export const initLanguage = async () => {
  const { setI18nLanguage, changeLanguage } = useI18nStore()
  const { i18nDictHook } = useDictStore()
  const i18ns = await i18nDictHook()
  const defaultLanguage = i18ns[0].dictValue;
  let localeLang = 'en';
  // 加载默认语言
  const { code, result } = await getLanguage(I18N_TAG, defaultLanguage);
  if (ResultEnum.SUCCESS === code) {
    i18n.global.fallbackLocale = defaultLanguage
    await setI18nLanguage(defaultLanguage, result);
  }
  const userStore = await useUserStore();
  // 如果用户有设置自己的语言则优先使用,否则使用浏览器语言
  if (!userStore.userInfo.language) {
    // 获取浏览器语言
    const lang = navigator.language
    const { code, result } = await getLanguage(I18N_TAG, lang);
    if (ResultEnum.SUCCESS === code) {
      await setI18nLanguage(lang, result);
      localeLang = lang;
    }
  } else {
    // 获取用户语言
    const lang = userStore.userInfo.language
    const { code, result } = await getLanguage(I18N_TAG, lang);
    if (ResultEnum.SUCCESS === code) {
      await setI18nLanguage(lang, result);
      localeLang = lang;
    }
  }
  await changeLanguage(localeLang)
  request.setHeader({ "Accept-Language": localeLang })
}



export default i18n;
