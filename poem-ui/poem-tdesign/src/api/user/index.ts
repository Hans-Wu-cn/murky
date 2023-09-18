import { request } from "@/utils/request";
import { PageResponse } from "../types";
import { PageUser, PoemUser } from "./types";



const Api = {
  role: '/poemUser',
  rolePage: '/poemUser/page',
};

/**
 * 获取用户分页列表
 * @returns Route
 */
export function userPage(params: PageUser) {
  return request.get<PageResponse<Array<PoemUser>>>({
    url: Api.rolePage,
    params
  });
}
/**
 * 添加和编辑用户
 * @returns Route
 */
export function addUser(data: PoemUser) {
  return request.post({
    url: '/poemUser',
    data
  });
}
/**
 * 查询用户信息
 * @returns Route
 */
export function queryUserInfo(userId: string) {
  return request.get({
    url: `/poemUser/${userId}`,
  });
}
/**
 * 删除用户信息
 * @returns Route
 */
export function delUserInfo(userId: string) {
  return request.delete({
    url: `/poemUser/${userId}`,
  });
}
