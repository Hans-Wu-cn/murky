import { BaseApiType, PageRequest } from '@/api/types';


/**
 * @param tenantName 租户名称
 */
export interface PagePoemTenant extends PageRequest {
  tenantName?: string,
}

/**
 * @param tenantId 租户id
 * @param tenantName 租户名称
 * @param groupName 权限组名称
 * @param adminUser 租户管理员
 * @param expires 到期时间
 * @param describe 描述
 * @param status 状态
 * @param createTime 创建时间
 */
export interface PoemTenant extends PageRequest {
  tenantId?: string,
  groupName: string,
  tenantName: string,
  adminUser: string,
  expires: string,
  describe?: string,
  status: number,
  createTime?: string,
}