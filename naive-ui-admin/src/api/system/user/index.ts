import { http } from '@/utils/http/axios';
import { PoemUserFrom,PoemUserResponse} from './types';
import { Result } from '@/utils/http/axios/types';
import { PageRequest } from '@/api/types';


/**
 * @description: 查询用户列表
 */
export function pageUser(params:PageRequest) {
  return http.request({
    url: '/poemUser/page',
    method: 'GET',
    params,
  });
}

/**
 * @description: 查询用户详情
 */
export function userInfo(userId:number) {
  return http.request<Result<PoemUserResponse>>({
    url: `/poemUser/${userId}`,
    method: 'GET',
  });
}

/**
 * @description: 添加用户
 */
export function addUser(poemUserFrom:PoemUserFrom) {
  return http.request({
    url: '/poemUser',
    method: 'POST',
    data:poemUserFrom
  });
}

/**
 * @description: 修改用户
 */
export function editUser(poemUserFrom:PoemUserFrom) {
  return http.request({
    url: '/poemUser',
    method: 'PUT',
    data:poemUserFrom
  });
}

/**
 * @description: 删除用户
 */
export function removeUser(userId:number) {
  return http.request({
    url: `/poemUser/${userId}`,
    method: 'DELETE',
  });
}





/**
 * @description: 用户修改密码
 */
export function changePassword(params, uid) {
  return http.request(
    {
      url: `/user/u${uid}/changepw`,
      method: 'POST',
      params,
    },
    {
      isTransformResponse: false,
    }
  );
}

/**
 * @description: 用户登出
 */
export function logout(params) {
  return http.request({
    url: '/login/logout',
    method: 'POST',
    params,
  });
}
