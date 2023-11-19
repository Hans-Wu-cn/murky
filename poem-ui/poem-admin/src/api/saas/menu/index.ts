import { request } from '@/utils/request';

import { PoemSaasMenu } from './types';

const Api = {
  saasMenu: '/poemSaasMenu',
  saasMenuDrop: `/poemSaasMenu/drop`,
  menuList: '/poemSaasMenu/list',
};
/**
 * 获取菜单列表
 * @returns Route
 */
export function getSaasMenuList() {
  return request.get<Array<PoemSaasMenu>>({
    url: Api.menuList,
  });
}

/**
 * 添加菜单
 * @returns Route
 */
export function addMenu(data: PoemSaasMenu) {
  return request.post({
    url: Api.saasMenu,
    data
  });
}
/**
 * 修改菜单
 * @returns Route
 */
export function updateMenu(data: PoemSaasMenu) {
  return request.put({
    url: Api.saasMenu,
    data
  });
}
/**
 * 删除菜单
 * @returns Route
 */
export function delMenu(saasMenuId: string) {
  return request.delete({
    url: `${Api.saasMenu}/${saasMenuId}`,
  });
}
/**
 * 菜单详情
 * @returns Route
 */
export function getMenu(saasMenuId: string) {
  return request.get({
    url: `${Api.saasMenu}/${saasMenuId}`,
  });
}
/**
 * 菜单拖动排序
 * @returns Route
 */
export function dragMenu(data: {
  parentSaasMenuId?: string;
  /*菜单id集合,按顺序排列 */
  saasMenuIds: string[];
}) {
  return request.put({
    url: Api.saasMenuDrop,
    data
  });
}
