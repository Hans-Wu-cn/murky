import { http } from '@/utils/http/axios';

/**
 * @description: 角色列表
 */
export function getRoleList() {
  return http.request({
    url: '/poemRole/page',
    method: 'GET',
  });
}

/**
 * @description: 获取角色详情
 */
export function getRoleInfo(roleId:number) {
  return http.request({
    url: `/poemRole/${roleId}`,
    method: 'GET',
  });
}

/**
 * @description: 添加角色
 */
export function addRole(poemRoleFrom:PoemRoleFrom) {
  return http.request({
    url: '/poemRole',
    method: 'POST',
    data:poemRoleFrom
  });
}

/**
 * @description: 修改角色
 */
export function editRole(poemRole:PoemRoleFrom) {
  return http.request({
    url: '/poemRole',
    method: 'PUT',
    data:poemRole
  });
}

/**
 * @description: 删除角色
 */
export function removeRole(roleId:number) {
  return http.request({
    url: `/poemRole/${roleId}`,
    method: 'DELETE',
    
  });
}

/**
 * 角色表单类
 */
export type PoemRoleFrom = {
  roleId?: number   //角色id,修改时必填项
  roleName: string  //角色名
  roleCode: string  //角色码
  describe: string  //描述
  menuIds: number[] //菜单id集合
}