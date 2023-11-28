import { PageResponse } from "@/api/types";
import { PagePermissionGroup, PermissionGroup } from "./types";
import { request } from '@/utils/request';

const Api = {
  permissionGroup: '/permissionGroup',
  permissionGroupPage: '/permissionGroup/page',
  permissionGroupList: '/permissionGroup/list',
};

/**
 * 获取权限组分页列表
 * @returns Route
 */
export function permissionGroupPage(params: PagePermissionGroup) {
  return request.get<PageResponse<Array<PermissionGroup>>>({
    url: Api.permissionGroupPage,
    params
  });
}
/**
 * 获取权限组列表
 * @returns Route
 */
export function permissionGroupList(params?: PagePermissionGroup) {
  return request.get<PermissionGroup[]>({
    url: Api.permissionGroupList,
    params
  });
}

/**
 * 获取权限组详情信息
 * @returns Route
 */
export function permissionGroupInfo(groupId: string) {
  return request.get<PermissionGroup>({
    url: `${Api.permissionGroup}/${groupId}`,
  });
}

/**
 * 修改
 * @returns Route
 */
export function updatePermissionGroup(data: PermissionGroup) {
  return request.put({
    url: Api.permissionGroup,
    data
  });
}
/**
 * 新增
 * @returns Route
 */
export function addPermissionGroup(data: PermissionGroup) {
  return request.post({
    url: Api.permissionGroup,
    data
  });
}
/**
 * 删除
 * @returns Route
 */
export function delPermissionGroup(groupId: string) {
  return request.delete({
    url: `/${Api.permissionGroup}/${groupId}`,
  });
}