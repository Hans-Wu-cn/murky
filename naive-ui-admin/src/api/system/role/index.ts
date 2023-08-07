import { http } from '@/utils/http/axios';
import { PoemRoleFrom,PoemRole } from './types';
import { Result } from '@/utils/http/axios/types';
import { PageRequest } from '@/api/types';

/**
 * @description: 角色列表分页查询
 */
export function getRolePage(params:PageRequest) {
  return http.request({
    url: '/poemRole/page',
    method: 'GET',
    params,
  });
}

/**
 * @description: 角色列表查询
 */
export async function getRoleList():Promise<Result<PoemRole[]>> {
  return await http.request<Result<Array<PoemRole>>>({
    url: '/poemRole/list',
    method: 'GET',
  });
}

/**
 * @description: 获取角色详情
 */
export function getRoleInfo(roleId:number) {
  return http.request<Result<PoemRoleFrom>>({
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

