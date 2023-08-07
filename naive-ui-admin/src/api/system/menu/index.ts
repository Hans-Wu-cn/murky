import { http } from '@/utils/http/axios';
import { PoemMenu,PoemMenuDrop } from './types'
import { Result } from '@/utils/http/axios/types';

/**
 * @description: 根据用户id获取用户菜单
 */
export function adminMenus() {
  return http.request({
    url: '/poemMenu/list',
    method: 'GET',
  });
}

/**
 * 获取tree菜单列表
 * @param params
 */
export function getMenuList() {
  return http.request({
    url: '/poemMenu/list',
    method: 'GET',
  });
}


/**
 * 添加菜单
 * @param params
 */
export function addMenu(poemMenu:PoemMenu) {
  return http.request({
    url: '/poemMenu',
    method: 'POST',
    data:poemMenu,
  });
}

/**
 * 添加菜单
 * @param params
 */
export function editMenu(data:PoemMenu) {
  return http.request({
    url: '/poemMenu',
    method: 'PUT',
    data,
  });
}

/**
 * 
 * @returns 
 */
export function dropMenu(data:PoemMenuDrop) {
  return http.request<Result<boolean>>({
    url: '/poemMenu/drop',
    method: 'PUT',
    data
  });
}

/**
 * 删除菜单
 * @param menuId
 */
export function removeMenu(menuId:string) {
  return http.request({
    url: `/poemMenu/${menuId}`,
    method: 'DELETE',
  });
}

