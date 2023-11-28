import { BaseApiType } from '@/api/types';

/**
 * 菜单类
 * @param tenantMenuId 菜单id
 * @param label 菜单标题
 * @param name 菜单名称
 * @param path 路径
 * @param openType 打开方式 1:当前窗口  2:新窗口
 * @param auth 权限码
 * @param parentTenantMenuId 父菜单id
 * @param type 菜单类型 0:目录 1:侧边菜单 2:按钮
 * @param isDisplay 是否显示在菜单  0:显示  1:隐藏
 * @param sort 排序
 * @param component 组件
 * @param icon icon
 * @param children 子菜单
 */
export interface PoemTenantMenu {
  tenantMenuId?: string;
  label?: string;
  name?: string;
  path?: string;
  openType?: number;
  auth?: string;
  parentTenantMenuId?: string;
  type?: number;
  sort?: number;
  component?: string;
  icon?: string;
  isCache?: number;
  isDisplay?: number;
  isOutside?: number;
  query?: string;
  children?: Array<PoemTenantMenu>;
}
