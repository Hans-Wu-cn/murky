import zhCN from 'tdesign-vue-next/es/locale/zh_CN';
import enUS from 'tdesign-vue-next/es/locale/en_US';
export const TDEGISN_LANGUAE_MAP = new Map<string, {}>(Object.entries({
  'en': enUS,
  'zh-CN': zhCN,
  'cn':  zhCN,
  'en-us': enUS,
  'en-ca': enUS,
  'en-uk': enUS,
  'en-au': enUS,
  'en-sg': enUS,
}));
export const getTdesignLanguage = (lang: string) : {} =>{
    return TDEGISN_LANGUAE_MAP.get(lang) || zhCN;
}