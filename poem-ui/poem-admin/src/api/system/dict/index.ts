import { PageResponse } from "@/api/types";
import { PageDictData, PageDictType, DictData, DictType } from "./types";
import { request } from '@/utils/request';

const Api = {
  dictType: '/dictType',
  dictData: '/dictData',
  dictTypePage: '/dictType/page',
  dictDataPage: '/dictData/page',
  dict: '/dictData/dict',
  refresh: '/dictType/refresh',
};


/**
 * 获取字典数据
 * @param dictType 字典类型
 * @returns Route
 */
export function dict(params: string) {
  return request.get<Array<DictData>>({
    url: `${Api.dict}/${params}`,
  });
}

/**
 * 获取字典类型分页列表
 * @returns Route
 */
export function dictTypePage(params: PageDictType) {
  return request.get<PageResponse<Array<DictType>>>({
    url: Api.dictTypePage,
    params
  });
}

/**
 * 获取字典数据分页列表
 * @returns Route
 */
export function dictDataPage(params: PageDictData) {
  return request.get<PageResponse<Array<DictData>>>({
    url: Api.dictDataPage,
    params
  });
}

/**
 * 新增字典分类
 * @returns Route
 */
export function addDictType(data: DictType) {
  return request.post({
    url: Api.dictType,
    data
  });
}

/**
 * 新增字典数据
 * @returns Route
 */
export function addDictData(data: DictData) {
  return request.post({
    url: Api.dictData,
    data
  });
}

/**
 * 修改字典分类
 * @returns Route
 */
export function editDictType(data: DictType) {
  return request.put({
    url: Api.dictType,
    data
  });
}

/**
 * 修改字典数据
 * @returns Route
 */
export function editDictData(data: DictData) {
  return request.put({
    url: Api.dictData,
    data
  });
}

/**
 * 字典类型详情
 * @returns Route
 */
export function dictTypeInfo(dictTypeId: string) {
  return request.get({
    url: `${Api.dictType}/${dictTypeId}`,
  });
}

/**
 * 字典数据详情
 * @returns Route
 */
export function dictDataInfo(dictCode: string) {
  return request.get({
    url: `${Api.dictData}/${dictCode}`,
  });
}

/**
 * 删除字典分类
 * @returns Route
 */
export function dictTypeRemove(dictTypeId: string) {
  return request.delete({
    url: `${Api.dictType}/${dictTypeId}`,
  });
}

/**
 * 删除字典数据
 * @returns Route
 */
export function dictDataRemove(dictDataId: string) {
  return request.delete({
    url: `${Api.dictData}/${dictDataId}`,
  });
}

/**
 * 刷新字典
 * @returns Route
 */
export function refershDict() {
  return request.post({
    url: Api.refresh,
  });
}