import { BaseApiType, PageRequest } from '@/api/types';

/**
 * @param i18nTag 国际化标签
 * @param i18nKey 国际化编码
 */
export interface I18nPageParams extends PageRequest {
  i18nTag: string,
  i18nKey?: string,
}


/**
 * @param i18nTag 国际化标签
 * @param i18nKey 国际化编码
 */
export interface I18nParam {
  i18nTag: string,
  i18nKey: string,
}

/**
 * @param i18nTag 国际化标签
 * @param i18nKey 国际化编码
 * @param i18nInputs 国际化值集合[{i18n:en,i18nValue:submit}]
 */
export interface I18nData {
  id?: string
  i18nTag: string,
  i18nKey: string,
  i18nInputs: Array<I18nInputs>,
}

/**
 * @param i18n 国际化对应语言
 * @param i18nValue 国际化语言对应得值
 */
export interface I18nInputs {
  language: string,
  i18nValue: string,
}
