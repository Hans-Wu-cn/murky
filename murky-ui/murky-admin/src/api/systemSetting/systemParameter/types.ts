import { BaseApiType, PageRequest } from '@/api/types';

/**
 * @param key 配置key
 */
export interface SystemParameterPageParams extends PageRequest {
  value: any;
  key?: string,
}

/**
 * @param id 配置id
 * @param key 配置key
 * @param value 配置值
 * @param describe 描述
 */
export interface SystemParameter extends BaseApiType {
  id?: string,
  key: string,
  value: string,
  describe?: string,
}