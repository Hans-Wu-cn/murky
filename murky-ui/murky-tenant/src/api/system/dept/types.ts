

/**
 * 菜单类
 * @param id 部门id
 * @param deptName 部门名称
 * @param parentId 上级部门
 * @param ancestors 祖级部门
 * @param sort 排序
 * @param children 子部门
 */
export interface DeptTree {
  id?: string;
  deptName?: string;
  parentId?: string;
  ancestors?: string;
  sort?: number;
  children?: Array<DeptTree>;
}

export interface Dept {
  id?: string;
  deptName?: string;
  parentId?: string;
  sort?: number;
}