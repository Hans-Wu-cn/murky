import { PageResponse } from "@/api/types";
import { PageSaasRole, PoemSaasRole } from "./types";
import { request } from '@/utils/request';

const Api = {
  saasRole: '/poemSaasRole',
  saasRolePage: '/poemSaasRole/page',
  saasRoleList: '/poemSaasRole/list',
};

/**
 * 获取角色分页列表
 * @returns Route
 */
export function saasRolePage(params: PageSaasRole) {
  return request.get<PageResponse<Array<PoemSaasRole>>>({
    url: Api.saasRolePage,
    params
  });
}
/**
 * 获取角色列表
 * @returns Route
 */
export function roleList(params?: PageSaasRole) {
  return request.get<PoemSaasRole[]>({
    url: Api.saasRoleList,
    params
  });
}

/**
 * 获取角色详情信息
 * @returns Route
 */
export function saasRoleInfo(roleId: string) {
  return request.get<PoemSaasRole>({
    url: `${Api.saasRole}/${roleId}`,
  });
}

/**
 * 修改
 * @returns Route
 */
export function updatePoemSaasRole(data: PoemSaasRole) {
  return request.put({
    url: Api.saasRole,
    data
  });
}
/**
 * 新增
 * @returns Route
 */
export function addPoemSaasRole(data: PoemSaasRole) {
  return request.post({
    url: Api.saasRole,
    data
  });
}
/**
 * 删除
 * @returns Route
 */
export function delPoemSaasRole(saasRoleId: string) {
  return request.delete({
    url: `/${Api.saasRole}/${saasRoleId}`,
  });
}