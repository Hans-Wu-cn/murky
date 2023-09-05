import { request } from '@/utils/request';

import { PoemDeptTree, PoemDept } from './types';

const Api = {
  deptList: '/poemDept/list',
  addDept: '/poemDept',
  editDept: '/poemDept',
  removeDept: '/poemDept/',
  dropDept: '/poemDept/drop',
};
/**
 * 获取部门树列表
 * @returns Route
 */
export function getDeptList() {
  return request.get<Array<PoemDeptTree>>({
    url: Api.deptList,
  });
}

/**
 * 新增部门
 * @returns Route
 */
export function addDept(data: PoemDept) {
  return request.post({
    url: Api.addDept,
    data
  });
}

/**
 * 修改部门
 * @returns Route
 */
export function editDept(data: PoemDept) {
  return request.put({
    url: Api.editDept,
    data
  });
}

/**
 * 删除部门
 * @returns Route
 */
export function removeDept(deptId: string) {
  return request.delete({
    url: `${Api.removeDept}/${deptId}`,
  });
}

/**
 * 拖动排序
 * @returns Route
 */
export function dropDept(menuIds: string[]) {
  return request.put({
    url: Api.dropDept,
    data: menuIds
  });
}