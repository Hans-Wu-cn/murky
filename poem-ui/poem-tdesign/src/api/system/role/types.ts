import { BaseApiType, PageRequest } from '@/api/types';


export interface PageRole extends PageRequest {
  roleName?: string,
  roleCode?: string,
}

/**
 * @param roleId 角色id
 * @param roleName 角色名
 * @param roleCode 角色码
 * @param describe 描述
 */
export interface PoemRole extends BaseApiType {
  roleId?: string,
  roleName: string,
  roleCode: string,
  dataScope: number,
  describe?: string,
  menuIds?: string[],
  deptIds?: string[],
}