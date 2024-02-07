import { request } from '@/utils/request';

import { DeptTree, Dept } from './types';

const Api = {
  deptList: '/dept/list',
  dropDept: '/dept/drop',
  dept: '/dept'
};
/**
 * 获取部门树列表
 * @returns Route
 */
export function getDeptList() {
  return request.get<Array<DeptTree>>({
    url: Api.deptList,
  });
}

/**
 * 获取部门详情
 * @returns Route
 */
export function deptInfo(deptId: string) {
  return request.get<DeptTree>({
    url: `${Api.dept}/${deptId}`,
  });
}

/**
 * 新增部门
 * @returns Route
 */
export function addDept(data: Dept) {
  return request.post({
    url: Api.dept,
    data
  });
}

/**
 * 修改部门
 * @returns Route
 */
export function editDept(data: Dept) {
  return request.put({
    url: Api.dept,
    data
  });
}

/**
 * 删除部门
 * @returns Route
 */
export function removeDept(id: string) {
  return request.delete({
    url: `${Api.dept}/${id}`,
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