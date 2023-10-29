import { request } from '@/utils/request';
import { PageResponse } from '../types';
import { I18nPageParams, I18nParam, I18nData } from './types';

const Api = {
  i18n: '/poemI18n',
  i18nPage: '/poemI18n/page',
  i18nInfo: '/poemI18n/info',
};

/**
 * 获取国际化分页列表
 * @returns Route
 */
export function i18nPage(params: I18nPageParams) {
  return request.get<PageResponse<Array<any>>>({
    url: Api.i18nPage,
    params
  });
}


/**
 * i18n详情
 * @returns Route
 */
export function i18nInfo(params: I18nParam) {
  return request.get<I18nData>({
    url: Api.i18nInfo,
    params
  });
}

/**
 * 新增国际化数据
 * @returns Route
 */
export function saveI18n(data: I18nData) {
  return request.post({
    url: Api.i18n,
    data
  });
}

/**
 * 修改国际化数据
 * @returns Route
 */
export function updateI18n(data: I18nData) {
  return request.put({
    url: Api.i18n,
    data
  });
}

/**
 * 删除国际化数据
 * @returns Route
 */
export function removeI18n(i18nKey: string) {
  return request.delete({
    url: `${Api.i18n}/${i18nKey}`,
  });
}