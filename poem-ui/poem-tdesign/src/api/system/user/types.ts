import { BaseApiType, PageRequest } from '@/api/types';


export interface PoemUser extends BaseApiType {
  userId?: string,
  userName?: string,
  account?: string,
  password?: string,
  sex?: number,
  email?: string,
  deptId?: string,
  roleIds?: string[]
}

/**
 * @param roleId 所属部门
 * @param userName 用户名称
 */
export interface PageUser extends PageRequest {
  deptId?: string,
  userName?: string,
}

/**
 * @param roleId 所属部门
 * @param userName 用户名称
 */
export interface RestPassword {
  userId: string,
  password: string,
  confirmPassword: string,
}