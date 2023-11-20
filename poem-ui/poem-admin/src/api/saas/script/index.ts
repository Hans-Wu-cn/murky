import { PageResponse } from "@/api/types";
import { PoemSaasScriptTablePage, PoemSaasScriptTable } from "./types";
import { request } from '@/utils/request';

const Api = {
  tableScript: '/poemSaasScriptTable',
  tableScriptPage: '/poemSaasScriptTable/page',
  saasRoleList: '/poemSaasScriptTable/list',
};

/**
 * 获取表格脚本分页信息
 */
export function tablePage(params: PoemSaasScriptTablePage) {
  return request.get<PageResponse<Array<PoemSaasScriptTable>>>({
    url: Api.tableScriptPage,
    params
  });
}

/**
 * 获取表格脚本详情信息
 */
export function tableInfo(tableId: string) {
  return request.get<PoemSaasScriptTable>({
    url: `${Api.tableScript}/${tableId}`,
  });
}

/**
 * 新增表格脚本
 */
export function tableSave(data: PoemSaasScriptTable) {
  return request.post({
    url: Api.tableScript,
    data
  });
}


/**
 * 修改表格脚本
 */
export function tableEdit(data: PoemSaasScriptTable) {
  return request.put({
    url: Api.tableScript,
    data
  });
}

/**
 * 删除表格脚本
 */
export function tableRemove(tableId: string) {
  return request.delete({
    url: `${Api.tableScript}/${tableId}`,
  });
}
