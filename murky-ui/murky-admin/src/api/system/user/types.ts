import { BaseApiType, PageRequest } from '@/api/types';

/**
 * @param id 所属部门
 * @param userName 用户名称
 * @param account 账号
 * @param password 密码
 * @param sex 性别
 * @param email 邮箱
 * @param deptId 部门id
 * @param roleIds 角色id
 */
export interface User extends BaseApiType {
  id?: string,
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
  email?: string,
  sex?: string,
}

/**
 * @param roleId 所属部门
 * @param userName 用户名称
 */
export interface RestPassword {
  id: string,
  password: string,
  confirmPassword: string,
}