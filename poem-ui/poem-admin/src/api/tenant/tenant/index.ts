import { PageResponse } from "@/api/types";
import { PagePoemTenant, PoemTenantFrom, PoemTenant } from "./types";
import { request } from '@/utils/request';

const Api = {
  poemTenant: '/poemTenant',
  poemTenantPage: '/poemTenant/page',
};


/**
 * 获取租户分页列表
 * @returns Route
 */
export function poemTenantPage(params: PagePoemTenant) {
  return request.get<PageResponse<Array<PoemTenant>>>({
    url: Api.poemTenantPage,
    params
  });
}

/**
 * 获取租户详情
 * @returns Route
 */
export function poemTenantInfo(tenantId: string) {
  return request.get<PoemTenant>({
    url: `${Api.poemTenant}/${tenantId}`,
  });
}

/**
 * 添加租户
 * @returns Route
 */
export function addPoemTenant(data: PoemTenantFrom) {
  return request.post({
    url: Api.poemTenant,
    data
  });
}

/**
 * 修改租户
 * @returns Route
 */
export function editPoemTenant(data: PoemTenant) {
  return request.put({
    url: Api.poemTenant,
    data
  });
}

/**
 * 停用租户
 * @returns Route
 */
export function deactivatePoemTenant(tenantId: string) {
  return request.post({
    url: `${Api.poemTenant}/${tenantId}`,
  });
}