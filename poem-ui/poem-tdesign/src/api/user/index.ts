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