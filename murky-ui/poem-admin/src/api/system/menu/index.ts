import { request } from '@/utils/request';

import { Menu } from './types';

const Api = {
  menu: '/menu',
  menuList: '/menu/list',
  drop: '/menu/drop',
};
/**
 * 获取菜单列表
 * @returns Route
 */
export function getMenuList() {
  return request.get<Array<Menu>>({
    url: Api.menuList,
  });
}

/**
 * 添加菜单
 * @returns Route
 */
export function addMenu(data: Menu) {
  return request.post({
    url: Api.menu,
    data
  });
}
/**
 * 修改菜单
 * @returns Route
 */
export function updateMenu(data: Menu) {
  return request.put({
    url: Api.menu,
    data
  });
}
/**
 * 删除菜单
 * @returns Route
 */
export function delMenu(menuId: string) {
  return request.delete({
    url: `${Api.menu}/${menuId}`,
  });
}
/**
 * 菜单详情
 * @returns Route
 */
export function getMenu(menuId: string) {
  return request.get({
    url: `${Api.menu}/${menuId}`,
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
    url: Api.drop,
    data
  });
}
