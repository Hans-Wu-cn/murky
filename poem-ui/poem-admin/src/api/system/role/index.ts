import { PageResponse } from "@/api/types";
import { PageRole, Role } from "./types";
import { request } from '@/utils/request';

const Api = {
  role: '/role',
  rolePage: '/role/page',
  roleList: '/role/list',
};

/**
 * 获取角色分页列表
 * @returns Route
 */
export function rolePage(params: PageRole) {
  return request.get<PageResponse<Array<Role>>>({
    url: Api.rolePage,
    params
  });
}
/**
 * 获取角色列表
 * @returns Route
 */
export function roleList(params?: PageRole) {
  return request.get<Role[]>({
    url: Api.roleList,
    params
  });
}

/**
 * 获取角色详情信息
 * @returns Route
 */
export function roleInfo(roleId: string) {
  return request.get<Role>({
    url: `${Api.role}/${roleId}`,
  });
}

/**
 * 修改
 * @returns Route
 */
export function updatePoemRole(data: Role) {
  return request.put({
    url: Api.role,
    data
  });
}
/**
 * 新增
 * @returns Route
 */
export function addPoemRole(data: Role) {
  return request.post({
    url: Api.role,
    data
  });
}
/**
 * 删除
 * @returns Route
 */
export function delPoemRole(roleId: string) {
  return request.delete({
    url: `/${Api.role}/${roleId}`,
  });
}