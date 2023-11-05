import { createI18n } from 'vue-i18n';
import { getLanguage } from '@/api/systemSetting/i18n';
import axios from 'axios';

const i18n = createI18n({
  locale: 'zh-CN', // 默认语言
  fallbackLocale: 'en', // 如果找不到当前语言的翻译，就使用默认语言
  messages: {}, // 空对象，稍后会通过接口获取数据并填充这里
});

// 从后端接口获取语言包数据
getLanguage("admin", "en").then(res => {
  i18n.global.setLocaleMessage("en", res.result);
}).catch(error => {
  console.error('Error fetching locales:', error);
});

// 从后端接口获取语言包数据
getLanguage("admin", "zh-CN").then(res => {
  i18n.global.setLocaleMessage("zh-CN", res.result);
}).catch(error => {
  console.error('Error fetching locales:', error);
});
export default i18n;
