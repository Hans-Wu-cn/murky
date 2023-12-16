import { BaseApiType, PageRequest } from '@/api/types';


/**
 * @param tenantName 租户名称
 */
export interface PageTenant extends PageRequest {
  tenantName?: string,
}

/**
 * @param tenantId 租户id
 * @param tenantName 租户名称
 * @param groupId 权限组id
 * @param groupName 权限组名称
 * @param adminUser 租户管理员
 * @param expires 到期时间
 * @param describe 描述
 * @param status 状态
 * @param createTime 创建时间
 */
export interface Tenant extends BaseApiType {
  tenantId: string,
  groupId: string,
  groupName: string,
  tenantName: string,
  adminUser?: string,
  expires: string,
  describe?: string,
  status?: number,
}

/**
 * @param tenantId 租户id
 * @param groupId 权限组id
 * @param tenantName 租户名称
 * @param account 租户管理员账号
 * @param password 租户管理员密码
 * @param confirmPassword 租户管理员确定密码
 * @param expires 到期时间
 * @param describe 描述
 * @param status 状态
 */
export interface TenantFrom extends BaseApiType {
  tenantId?: string,
  groupId: string,
  tenantName: string,
  account: string,
  password: string,
  confirmPassword: string,
  expires: string,
  describe?: string,
  status: number,
}