import { BaseApiType, PageRequest } from '@/api/types';

/**
 * @param roleName 角色名
 * @param roleCode 角色码
 */
export interface PageRole extends PageRequest {
  roleName?: string,
  roleCode?: string,
}

/**
 * @param id 角色id
 * @param roleName 角色名
 * @param roleCode 角色码
 * @param describe 描述
 * @param menuIds 菜单Id
 * @param deptIds 部门Id
 */
export interface Role extends BaseApiType {
  id?: string,
  roleName: string,
  roleCode: string,
  dataScope: number,
  describe?: string,
  fkMenuIds?: string[],
  fkDeptIds?: string[],
}