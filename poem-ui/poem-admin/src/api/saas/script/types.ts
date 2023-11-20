import { BaseApiType, PageRequest } from '@/api/types';


/**
 * @param tableName 表名
 * @param tag 标签
 * @param status 状态(0:正常  1:停用)
 */
export interface PoemSaasScriptTablePage extends PageRequest {
  tableName?: string,
  tag?: string,
  status?: number,
}

/**
 * @param tableId 表格脚本id
 * @param tableName 表名
 * @param describe 描述
 * @param tag 标签
 * @param status 状态(0:正常  1:停用)
 */
export interface PoemSaasScriptTable extends BaseApiType {
  tableId?: string,
  tableName: string,
  describe: string,
  tag?: number,
  status?: number
}