import { BaseApiType, PageRequest } from "../types";


export interface PagePoemDictType extends PageRequest {
  dictName: string,
  dictType: string,
  status: number,
}

/**
 * @param dictTypeId 字典类型id
 * @param dictName 字典名称
 * @param dictType 字典类型
 * @param status 字典状态
 * @param remark 备注/描述
 */
export interface PoemDictType extends BaseApiType {
  dictTypeId?: string,
  dictName: string,
  dictType: string,
  status: number,
  remark?: string,
}