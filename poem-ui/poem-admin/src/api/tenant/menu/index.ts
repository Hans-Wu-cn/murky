import { request } from '@/utils/request';

import { PoemTenantMenu } from './types';

const Api = {
  tenantMenu: '/poemTenantMenu',
  tenantMenuDrop: `/poemTenantMenu/drop`,
  menuList: '/poemTenantMenu/list',
};
/**
 * 获取菜单列表
 * @returns Route
 */
export function getTenantMenuList() {
  return request.get<Array<PoemTenantMenu>>({
    url: Api.menuList,
  });
}

/**
 * 添加菜单
 * @returns Route
 */
export function addMenu(data: PoemTenantMenu) {
  return request.post({
    url: Api.tenantMenu,
    data
  });
}
/**
 * 修改菜单
 * @returns Route
 */
export function updateMenu(data: PoemTenantMenu) {
  return request.put({
    url: Api.tenantMenu,
    data
  });
}
/**
 * 删除菜单
 * @returns Route
 */
export function delMenu(tenantMenuId: string) {
  return request.delete({
    url: `${Api.tenantMenu}/${tenantMenuId}`,
  });
}
/**
 * 菜单详情
 * @returns Route
 */
export function getMenu(tenantMenuId: string) {
  return request.get({
    url: `${Api.tenantMenu}/${tenantMenuId}`,
  });
}
/**
 * 菜单拖动排序
 * @returns Route
 */
export function dragMenu(data: {
  parentTenantMenuId?: string;
  /*菜单id集合,按顺序排列 */
  tenantMenuIds: string[];
}) {
  return request.put({
    url: Api.tenantMenuDrop,
    data
  });
}
