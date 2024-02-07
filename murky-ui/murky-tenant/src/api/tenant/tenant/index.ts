import { PageResponse } from "@/api/types";
import { PageTenant, TenantFrom, Tenant } from "./types";
import { request } from '@/utils/request';

const Api = {
  tenant: '/tenant',
  tenantPage: '/tenant/page',
};


/**
 * 获取租户分页列表
 * @returns Route
 */
export function tenantPage(params: PageTenant) {
  return request.get<PageResponse<Array<Tenant>>>({
    url: Api.tenantPage,
    params
  });
}

/**
 * 获取租户详情
 * @returns Route
 */
export function tenantInfo(id: string) {
  return request.get<Tenant>({
    url: `${Api.tenant}/${id}`,
  });
}

/**
 * 添加租户
 * @returns Route
 */
export function addTenant(data: TenantFrom) {
  return request.post({
    url: Api.tenant,
    data
  });
}

/**
 * 修改租户
 * @returns Route
 */
export function editTenant(data: Tenant) {
  return request.put({
    url: Api.tenant,
    data
  });
}

/**
 * 停用租户
 * @returns Route
 */
export function deactivateTenant(tenantId: string) {
  return request.post({
    url: `${Api.tenant}/${tenantId}`,
  });
}