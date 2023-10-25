import { BaseApiType, PageRequest } from '../types';

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
export interface I18nParams {
  i18nTag: string,
  i18nKey: string,
}

/**
 * @param i18nTag 国际化标签
 * @param i18nKey 国际化编码
 * @param i18nInputs 国际化值集合[{i18n:en,i18nValue:submit}]
 */
export interface I18nResult {
  i18nTag: string,
  i18nKey: string,
  i18nInputs: any,
}
