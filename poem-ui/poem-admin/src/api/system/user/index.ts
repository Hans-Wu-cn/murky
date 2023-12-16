import { request } from "@/utils/request";
import { PageResponse } from "@/api/types";
import { PageUser, User, RestPassword } from "./types";



const Api = {
  user: '/user',
  userPage: '/user/page',
  restPassword: '/user/restPassword',
};

/**
 * 获取用户分页列表
 * @returns Route
 */
export function userPage(params: PageUser) {
  return request.get<PageResponse<Array<User>>>({
    url: Api.userPage,
    params
  });
}
/**
 * 新增用户
 * @returns Route
 */
export function addUser(data: User) {
  return request.post({
    url: Api.user,
    data
  });
}
/**
 * 编辑用户
 * @returns Route
 */
export function editUser(data: User) {
  return request.put({
    url: Api.user,
    data
  });
}
/**
 * 查询用户信息
 * @returns Route
 */
export function queryUserInfo(userId: string) {
  return request.get({
    url: `${Api.user}/${userId}`,
  });
}

/**
 * 删除用户信息
 * @returns Route
 */
export function delUserInfo(userId: string) {
  return request.delete({
    url: `${Api.user}/${userId}`,
  });
}


/**
 * 重置密码
 * @returns
 */
export function restPassword(data: RestPassword) {
  return request.put({
    url: Api.restPassword,
    data
  });
}