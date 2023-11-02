import { PageResponse } from "../types";
import { PagePoemDictData, PagePoemDictType, PoemDictData, PoemDictType } from "./types";
import { request } from '@/utils/request';

const Api = {
  dictType: '/poemDictType',
  dictData: '/poemDictData',
  dictTypePage: '/poemDictType/page',
  dictDataPage: '/poemDictData/page',
  dict: '/poemDictData/dict',
};


/**
 * 获取字典数据
 * @param dictType 字典类型
 * @returns Route
 */
export function dict(params: string) {
  return request.get<Array<PoemDictData>>({
    url: `${Api.dict}/${params}`,
  });
}

/**
 * 获取字典类型分页列表
 * @returns Route
 */
export function dictTypePage(params: PagePoemDictType) {
  return request.get<PageResponse<Array<PoemDictType>>>({
    url: Api.dictTypePage,
    params
  });
}

/**
 * 获取字典数据分页列表
 * @returns Route
 */
export function dictDataPage(params: PagePoemDictData) {
  return request.get<PageResponse<Array<PoemDictData>>>({
    url: Api.dictDataPage,
    params
  });
}

/**
 * 新增字典分类
 * @returns Route
 */
export function addPoemDictType(data: PoemDictType) {
  return request.post({
    url: Api.dictType,
    data
  });
}

/**
 * 新增字典数据
 * @returns Route
 */
export function addPoemDictData(data: PoemDictData) {
  return request.post({
    url: Api.dictData,
    data
  });
}

/**
 * 修改字典分类
 * @returns Route
 */
export function editPoemDictType(data: PoemDictType) {
  return request.put({
    url: Api.dictType,
    data
  });
}

/**
 * 修改字典数据
 * @returns Route
 */
export function editPoemDictData(data: PoemDictData) {
  return request.put({
    url: Api.dictData,
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
 * 字典数据详情
 * @returns Route
 */
export function PoemDictDataInfo(dictCode: string) {
  return request.get({
    url: `${Api.dictData}/${dictCode}`,
  });
}

/**
 * 删除字典分类
 * @returns Route
 */
export function PoemDictTypeRemove(dictTypeId: string) {
  return request.delete({
    url: `${Api.dictType}/${dictTypeId}`,
  });
}

/**
 * 删除字典数据
 * @returns Route
 */
export function PoemDictDataRemove(dictDataId: string) {
  return request.delete({
    url: `${Api.dictData}/${dictDataId}`,
  });
}