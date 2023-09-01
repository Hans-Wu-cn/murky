import { PageResponse } from "../types";
import { PageRole, PoemRole } from "./types";
import { request } from '@/utils/request';

const Api = {
  rolePage: '/poemRole/page',
};

/**
 * 获取角色分页列表
 * @returns Route
 */
export function rolePage(params:PageRole) {
  return request.get<PageResponse<Array<PoemRole>>>({
    url: Api.rolePage,
    params
  });
}