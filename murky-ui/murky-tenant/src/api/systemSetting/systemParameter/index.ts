import { request } from '@/utils/request';
import { PageResponse } from '@/api/types';
import { SystemParameterPageParams, SystemParameter } from './types';

const Api = {
  systemParameter: '/systemParameter',
  systemParameterPage: '/systemParameter/page',
  refresh: '/systemParameter/refresh',
};

/**
 * 获取系统参数配置分页列表
 * @returns Route
 */
export function systemParameterPage(params: SystemParameterPageParams) {
  return request.get<PageResponse<Array<SystemParameter>>>({
    url: Api.systemParameterPage,
    params
  });
}


/**
 * 系统参数配置详情
 * @returns Route
 */
export function systemParameterInfo(id: string) {
  return request.get<SystemParameter>({
    url: `${Api.systemParameter}/${id}`,
  });
}

/**
 * 刷新缓存
 * @returns Route
 */
export function refreshCache() {
  return request.post({
    url: Api.refresh,
  });
}

/**
 * 新增系统参数配置
 * @returns Route
 */
export function saveSystemParameter(data: SystemParameter) {
  return request.post({
    url: Api.systemParameter,
    data
  });
}

/**
 * 修改系统参数配置
 * @returns Route
 */
export function updateSystemParameter(data: SystemParameter) {
  return request.put({
    url: Api.systemParameter,
    data
  });
}

/**
 * 删除系统参数配置
 * @returns Route
 */
export function removeSystemParameter(id: string) {
  return request.delete({
    url: `${Api.systemParameter}/${id}`,
  });
}
