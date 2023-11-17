import { PageResponse } from "@/api/types";
import { PageRole, PoemRole } from "./types";
import { request } from '@/utils/request';

const Api = {
  role: '/poemRole',
  rolePage: '/poemRole/page',
};

/**
 * 获取角色分页列表
 * @returns Route
 */
export function rolePage(params: PageRole) {
  return request.get<PageResponse<Array<PoemRole>>>({
    url: Api.rolePage,
    params
  });
}
/**
 * 获取角色列表
 * @returns Route
 */
export function roleList(params?: PageRole) {
  return request.get<PoemRole[]>({
    url: '/poemRole/list',
    params
  });
}

/**
 * 获取角色详情信息
 * @returns Route
 */
export function roleInfo(roleId: string) {
  return request.get<PoemRole>({
    url: `${Api.role}/${roleId}`,
  });
}

/**
 * 修改
 * @returns Route
 */
export function updatePoemRole(data: PoemRole) {
  return request.put({
    url: Api.role,
    data
  });
}
/**
 * 新增
 * @returns Route
 */
export function addPoemRole(data: PoemRole) {
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