import type { Route } from '@/api/auth/types';
import type { UserInfo } from '@/types/interface';
import { request } from '@/utils/request';

const Api = {
  menuList: '/auth/menu',
  login: '/auth/login',
  userInfo: '/auth/info',
};

/**
 * 登录
 * @returns
 */
export function login(data: Record<string, unknown>) {
  return request.post({
    url: Api.login,
    data,
  });
}

/**
 * 获取用户详情信息
 * @returns
 */
export function getUserInfo() {
  return request.get<UserInfo>({
    url: Api.userInfo,
  });
}

/**
 * 获取用户菜单
 * @returns Route
 */
export function getMenuList() {
  return request.get<Array<Route>>({
    url: Api.menuList,
  });
}
