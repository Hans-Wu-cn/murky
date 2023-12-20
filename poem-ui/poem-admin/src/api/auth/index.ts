import type { UserInfo } from '@/types/interface';
import { request } from '@/utils/request';
import { ProfileFrom, ProfileInfo, Route } from './types';

const Api = {
  menuList: '/auth/menu',
  login: '/auth/login',
  logout: '/auth/logout',
  userInfo: '/auth/info',
  languagePreference: '/profile/language',
  profileInfo: '/profile',
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
 * 登出
 * @returns
 */
export function logout() {
  return request.post({
    url: Api.logout
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
 * 获取用户详情信息
 * @returns
 */
export function profileInfo() {
  return request.get<ProfileInfo>({
    url: Api.profileInfo,
  });
}

/**
 * 获取用户详情信息
 * @returns
 */
export function editProfile(data: ProfileFrom) {
  return request.put({
    url: Api.profileInfo,
    data
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

/**
 * 获取用户菜单
 * @returns Route
 */
export function setLanguagePreference(lang: string) {
  return request.post<Array<Route>>({
    url: `${Api.languagePreference}/${lang}`,
  });
}
