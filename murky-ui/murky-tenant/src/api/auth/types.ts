import { defineComponent } from 'vue';


/**
 * 
 * @param userName 用户名称
 * @param email 邮箱
 * @param sex 性别
 * @param roleName 角色名称
 * @param deptNameList 部门名称
 * @param createTime 创建日期
 */
export interface ProfileInfo {
  userName: string;
  email: string;
  sex: number;
  roleName: string;
  deptNameList: string[];
  createTime: number;
}

/**
 * 
 * @param userName 用户名称
 * @param email 邮箱
 * @param sex 性别
 */
export interface ProfileFrom {
  userName: string;
  email: string;
  sex: number;
}

/**
 * 
 * @param oldPassword 旧密码
 * @param password 新密码
 * @param surePassword 确定新密码
 */
export interface EditPasswordFrom {
  oldPassword: string;
  password: string;
  surePassword: string;
}

export interface UserInfo {
  userId: string;
  userName: string;
  token: string;
  roleIds: string[];
  roleCodes: string[];
  permissions: string[];
}

export type Component<T = any> =
  | ReturnType<typeof defineComponent>
  | (() => Promise<typeof import('*.vue')>)
  | (() => Promise<T>);

export interface RouteItem {
  path: string;
  name: string;
  component?: Component | string;
  components?: Component;
  redirect?: string;
  meta: RouteMeta;
  children?: Array<RouteItem>;
}
export interface RouteMeta {
  title: string;
  icon?: string;
  expanded?: boolean;
  orderNo?: number;
  hidden?: boolean;
  hiddenBreadcrumb?: boolean;
  single?: boolean;
  keepAlive?: boolean;
  frameSrc?: string;
  frameBlank?: boolean;
}

export interface Route {
  menuId: string;
  path: string;
  name: string;
  component?: Component | string;
  children?: Array<Route>;
  label: string;
  icon?: string;
  isDisplay: number;
  single?: boolean;
  isCache: number;
  isOutside: number;
  frameBlank?: boolean;
}
