

import { PoemDictTypeRemove, dict } from '@/api/system/dict';
import { PoemDictData } from '@/api/system/dict/types';
import { ResultEnum } from '@/enums/httpEnum';

/**
 * 缓存字典
 */
let dictCache = new Map<string, Array<PoemDictData>>();

export const dictKey = {
  i18n: 'i18n:language',
  i18nTag: 'i18n:tag',
  saasScriptTag: 'saas:script:tag',
};

/**
 * 	商户脚本标签字典数据
 * @returns 
 */
export const saasScriptTagDictHook = async (): Promise<PoemDictData[]> => {
  return await dictFunction(dictKey.saasScriptTag)
}

/**
 * 国际化地区字典数据
 * @returns 
 */
export const i18nDictHook = async (): Promise<PoemDictData[]> => {
  return await dictFunction(dictKey.i18n)
}

/**
 * 国际化标签字典数据
 * @returns 
 */
export const i18nTagDictHook = async (): Promise<PoemDictData[]> => {
  return await dictFunction(dictKey.i18nTag)
}


/**
 * 字典函数通用方法
 * @param key 对应字典的字典key
 * @returns 字典集合
 */
export const dictFunction = async (key: string): Promise<PoemDictData[]> => {
  const cache = dictCache.get(key);
  if (cache) {
    console.debug("cache")
    return cache
  }
  const { code, result, message } = await dict(key);
  if (ResultEnum.SUCCESS === code) {
    dictCache.set(key, result)
    return result
  }
  return []
}

