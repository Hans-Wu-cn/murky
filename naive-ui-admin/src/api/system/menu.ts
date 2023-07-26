import { http } from '@/utils/http/axios';

/**
 * @description: 根据用户id获取用户菜单
 */
export function adminMenus() {
  return http.request({
    url: '/menus',
    method: 'GET',
  });
}

/**
 * 获取tree菜单列表
 * @param params
 */
export function getMenuList(params?) {
  return http.request({
    url: '/poemMenu/list',
    method: 'GET',
    params,
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
    params:poemMenu,
  });
}

/**
 * 删除菜单
 * @param menuId
 */
export function removeMenu(menuId:any) {
  return http.request({
    url: `/poemMenu/${menuId}`,
    method: 'DELETE',
  });
}

export type PoemMenu = {
  menuId?: number
  label: string
  subtitle: string
  path: string
  openType: number
  auth: string
  createTime?: Date
  updateTime?: Date
  createUser?: number
  updateUser?: number
  parentMenuId?: number
  type: number
  sort: number
  component: string
  icon: string
}