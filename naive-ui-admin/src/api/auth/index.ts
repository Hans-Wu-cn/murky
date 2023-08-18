import { http } from '@/utils/http/axios';
import { Result } from '@/utils/http/axios/types';

/**
 * @description: 用户登录
 */
export function login(params) {
  return http.request<Result>(
    {
      url: '/auth/login',
      method: 'POST',
      params,
    },
    {
      isTransformResponse: false,
    }
  );
}

/**
 * @description: 获取用户信息
 */
export function getUserInfo() {
  return http.request({
    url: '/auth/info',
    method: 'get',
  });
}

/**
 * @description: 用户登出
 */
export function logout() {
  return http.request({
    url: '/auth/logout',
    method: 'POST',
  });
}