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
export function addMenu() {
  return request.post({
    url: Api.addMenu,
  });
}
