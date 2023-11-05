import { createI18n } from 'vue-i18n';
import { getLanguage } from '@/api/systemSetting/i18n';
import { i18nDictHook } from './hooks/dict';
import { ResultEnum } from './enums/httpEnum';
import { useUserStore } from '@/store';

const i18n = createI18n({
  locale: 'zh-CN', // 默认语言
  fallbackLocale: 'en', // 如果找不到当前语言的翻译，就使用默认语言
  messages: {}, // 空对象，稍后会通过接口获取数据并填充这里
});

/**
 * 初始化语言包
 */
export const initLanguage = async () => {
  const i18ns = await i18nDictHook()
  // 加载默认语言
  const { code, result } = await getLanguage("admin", i18ns[0].dictValue);
  if (ResultEnum.SUCCESS === code) {
    i18n.global.setLocaleMessage(i18ns[0].dictValue, result);
  }
  const { userInfo } = useUserStore();
  // 如果用户有设置自己的语言则优先使用,否则使用浏览器语言
  if (!userInfo.language) {
    // 获取浏览器语言
    const lang = navigator.language
    const { code, result } = await getLanguage("admin", lang);
    if (ResultEnum.SUCCESS === code) {
      i18n.global.setLocaleMessage(lang, result);
    }
  } else {
    // 获取用户语言
    const lang = userInfo.language
    const { code, result } = await getLanguage("admin", lang);
    if (ResultEnum.SUCCESS === code) {
      i18n.global.setLocaleMessage(lang, result);
    }
  }
}
export default i18n;
