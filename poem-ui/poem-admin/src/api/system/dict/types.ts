import { BaseApiType, PageRequest } from '@/api/types';
import { ComputedRef } from 'vue';

/**
 * 字典分类查询类
 * @param dictName 字典名称
 * @param dictType 字典类型
 * @param status 字典状态 0:正常 1:停用
 */

export interface PagePoemDictType extends PageRequest {
  dictName: string,
  dictType: string,
  status: number,
}

/**
 * 字典数据查询类
 * @param dictLabel 字典标签
 * @param dictType 字典类型
 * @param status 字典状态 0:正常 1:停用
 */
export interface PagePoemDictData extends PageRequest {
  dictLabel?: string,
  dictType: string,
  status?: number,
}

/**
 * @param dictTypeId 字典类型id
 * @param dictName 字典名称
 * @param dictType 字典类型
 * @param status 字典状态 0:正常 1:停用
 * @param remark 备注/描述
 */
export interface PoemDictType extends BaseApiType {
  dictTypeId?: string,
  dictName: string,
  dictType: string,
  status: number,
  remark?: string,
}

/**
 * @param dictCode 字典编码
 * @param dictSort 字典排序
 * @param dictLabel 字典标签
 * @param dictValue 字典值
 * @param dictType 状态
 * @param remark 备注
 * @param status 字典状态 0:正常 1:停用
 */
export interface PoemDictData extends BaseApiType {
  dictCode?: string,
  dictSort: number,
  dictLabel: string,
  dictValue: string,
  dictType: string,
  remark?: string,
  status: number,
}