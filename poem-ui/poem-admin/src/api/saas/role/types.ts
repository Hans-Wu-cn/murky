import { BaseApiType, PageRequest } from '@/api/types';


export interface PageSaasRole extends PageRequest {
  saasRoleName?: string,
  saasRoleCode?: string,
}

/**
 * @param roleId 角色id
 * @param roleName 角色名
 * @param roleCode 角色码
 * @param describe 描述
 */
export interface PoemSaasRole extends BaseApiType {
  saasRoleId?: string,
  saasRoleName: string,
  saasRoleCode: string,
  describe?: string,
  saasMenuIds?: string[],
}