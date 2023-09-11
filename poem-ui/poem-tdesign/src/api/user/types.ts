import { BaseApiType, PageRequest } from "../types";


export interface PoemUser extends BaseApiType {
  userId?: string,
  userName?: string,
  account?: string,
  password?: string,
  sex?: number,
  email?: string,
  deptId?: string,

}

/**
 * @param roleId 所属部门
 * @param userName 用户名称
 */
export interface PageUser extends PageRequest {
  deptId?: string,
  userName?: string,
}