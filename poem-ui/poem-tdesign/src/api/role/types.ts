import { BaseApiType, PageRequest } from "../types";


export interface PageRole extends PageRequest{
  roleName?:String,
  roleCode?:String,
}

/**
 * @param roleId 角色id
 * @param roleName 角色名
 * @param roleCode 角色码
 * @param describe 描述
 */
export interface PoemRole extends BaseApiType{
  roleId:String,
  roleName:String,
  roleCode:String,
  describe?:String,
}