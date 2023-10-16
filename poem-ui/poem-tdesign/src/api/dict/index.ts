import { PageResponse } from "../types";
import { PagePoemDictType, PoemDictType } from "./types";
import { request } from '@/utils/request';

const Api = {
  dictType: '/poemDictType',
  dictTypePage: '/poemDictType/page',
};

/**
 * 获取数据字典分页列表
 * @returns Route
 */
export function dictTypePage(params: PagePoemDictType) {
  return request.get<PageResponse<Array<PoemDictType>>>({
    url: Api.dictTypePage,
    params
  });
}

/**
 * 新增
 * @returns Route
 */
export function addPoemDictType(data: PoemDictType) {
  return request.post({
    url: Api.dictType,
    data
  });
}

/**
 * 修改
 * @returns Route
 */
export function editPoemDictType(data: PoemDictType) {
  return request.put({
    url: Api.dictType,
    data
  });
}

/**
 * 字典类型详情
 * @returns Route
 */
export function PoemDictTypeInfo(dictTypeId: string) {
  return request.get({
    url: `${Api.dictType}/${dictTypeId}`,
  });
}

/**
 * 字典类型详情
 * @returns Route
 */
export function PoemDictTypeRemove(dictTypeId: string) {
  return request.delete({
    url: `${Api.dictType}/${dictTypeId}`,
  });
}