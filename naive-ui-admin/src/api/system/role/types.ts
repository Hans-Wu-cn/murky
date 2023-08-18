import { BaseApiType } from '@/api/types';

/**
 * 角色表单类
 */
export type PoemRoleFrom = {
  roleId?: string   //角色id,修改时必填项
  roleName: string  //角色名
  roleCode: string  //角色码
  describe: string  //描述
  menuIds: Array<string> //菜单id集合
}

/**
 * 角色列表类
 */
export interface PoemRole extends BaseApiType{
  roleId?: string   //角色id,修改时必填项
  roleName: string  //角色名
  roleCode: string  //角色码
  describe: string  //描述
}