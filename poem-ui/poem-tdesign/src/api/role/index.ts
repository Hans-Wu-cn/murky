import { PageResponse } from "../types";
import { PageRole, PoemRole } from "./types";
import { request } from '@/utils/request';

const Api = {
  rolePage: '/poemRole/page',
};

/**
 * 获取角色分页列表
 * @returns Route
 */
export function rolePage(params:PageRole) {
  return request.get<PageResponse<Array<PoemRole>>>({
    url: Api.rolePage,
    params
  });
}
/**
 * 修改
 * @returns Route
 */
export function updatePoemRole(data:PoemRole) {
  return request.put({
    url: '/poemRole',
    data
  });
}
/**
 * 新增
 * @returns Route
 */
export function addPoemRole(data:PoemRole) {
  return request.post({
    url: '/poemRole',
    data
  });
}
/**
 * 删除
 * @returns Route
 */
export function delPoemRole(data:PoemRole) {
  return request.delete({
    url: `/poemRole/${data.roleId}`,
    data
  });
}