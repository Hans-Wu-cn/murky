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
  fkDeptId?: string,
  fkRoleIds?: string[]
}

/**
 * @param deptId 所属部门
 * @param userName 用户名称
 * @param email 邮箱
 * @param sex 性别
 */
export interface PageUser extends PageRequest {
  fkDeptId?: string,
  userName?: string,
  email?: string,
  sex?: string,
}

/**
 * @param id 用户id
 * @param password 新密码
 * @param confirmPassword 确定新密码
 */
export interface RestPassword {
  id: string,
  password: string,
  confirmPassword: string,
}