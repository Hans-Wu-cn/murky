import { http } from '@/utils/http/axios';

/**
 * @description: 角色列表
 */
export function getRoleList() {
  return http.request({
    url: '/poemRole/list',
    method: 'GET',
  });
}

/**
 * @description: 添加角色
 */
export function addRole(poemRole:PoemRole) {
  return http.request({
    url: '/poemRole',
    method: 'Post',
    data:poemRole
  });
}

/**
 * @description: 修改角色
 */
export function editRole(poemRole:PoemRole) {
  return http.request({
    url: '/poemRole',
    method: 'PUT',
    data:poemRole
  });
}


export type PoemRole = {
  roleId?: number
  roleName: string
  roleCode: string
  describe: string
  createTime?: Date
  updateTime?: Date
  createUser?: number
  updateUser?: number
}