import { PageResponse } from "@/api/types";
import { PagePoemTenant, PoemTenant } from "./types";
import { request } from '@/utils/request';

const Api = {
  poemTenant: '/poemTenant',
  poemTenantPage: '/poemTenant/page',
};


/**
 * 获取权限组分页列表
 * @returns Route
 */
export function poemTenantPage(params: PagePoemTenant) {
  return request.get<PageResponse<Array<PoemTenant>>>({
    url: Api.poemTenantPage,
    params
  });
}

/**
 * 停用租户
 * @returns Route
 */
export function deactivatePoemTenant(tenantId: string) {
  return request.get<PageResponse<Array<PoemTenant>>>({
    url: `${Api.poemTenant}/${tenantId}`,
  });
}