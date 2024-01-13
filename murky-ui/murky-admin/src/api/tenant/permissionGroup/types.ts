import { BaseApiType, PageRequest } from '@/api/types';

/**
 * @param groupName 权限组名
 */
export interface PagePermissionGroup extends PageRequest {
  groupName?: string,
}

/**
 * @param groupId 权限组id
 * @param groupName 权限组名
 * @param describe 描述
 * @param tenantMenuIds 菜单列表
 */
export interface PermissionGroup extends BaseApiType {
  id?: string,
  groupName: string,
  describe?: string,
  tenantMenuIds?: string[],
}