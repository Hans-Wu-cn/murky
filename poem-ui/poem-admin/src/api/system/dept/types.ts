

/**
 * 菜单类
 * @param deptId 部门id
 * @param deptName 部门名称
 * @param parentDept 上级部门
 * @param ancestors 祖级部门
 * @param sort 排序
 * @param children 子部门
 */
export interface DeptTree {
  deptId?: string;
  deptName?: string;
  parentDept?: string;
  ancestors?: string;
  sort?: number;
  children?: Array<DeptTree>;
}

export interface Dept {
  deptId?: string;
  deptName?: string;
  parentDept?: string;
  sort?: number;
}