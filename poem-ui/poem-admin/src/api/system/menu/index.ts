import { request } from '@/utils/request';

import { PoemMenu } from './types';

const Api = {
  menuList: '/poemMenu/list',
  addMenu: '/poemMenu',
};
/**
 * 获取菜单列表
 * @returns Route
 */
export function getMenuList() {
  return request.get<Array<PoemMenu>>({
    url: Api.menuList,
  });
}

/**
 * 添加菜单
 * @returns Route
 */
export function addMenu(data: PoemMenu) {
  return request.post({
    url: Api.addMenu,
    data
  });
}
/**
 * 修改菜单
 * @returns Route
 */
export function updateMenu(data: PoemMenu) {
  return request.put({
    url: Api.addMenu,
    data
  });
}
/**
 * 删除菜单
 * @returns Route
 */
export function delMenu(menuId: string) {
  return request.delete({
    url: `/poemMenu/${menuId}`,
  });
}
/**
 * 菜单详情
 * @returns Route
 */
export function getMenu(menuId: string) {
  return request.get({
    url: `/poemMenu/${menuId}`,
  });
}
/**
 * 菜单拖动排序
 * @returns Route
 */
export function dragMenu(data: {
  parentMenuId?: string;
  /*菜单id集合,按顺序排列 */
  menuIds: string[];
}) {
  return request.put({
    url: `/poemMenu/drop`,
    data
  });
}
