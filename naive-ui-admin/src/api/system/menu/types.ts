import { BaseApiType } from "@/api/types"

/**
 * 菜单类
 * @param menuId 菜单id
 * @param label 菜单标题
 * @param subtitle 副标题
 * @param path 路径
 * @param openType 打开方式
 * @param auth 权限码
 * @param parentMenuId 父菜单id
 * @param type 菜单类型
 * @param sort 排序
 * @param component 组件
 * @param icon icon
 */
export interface PoemMenu extends BaseApiType {
  menuId?: string
  label: string
  subtitle: string
  path: string
  openType: number
  auth: string
  parentMenuId?: string
  type: number
  sort: number
  component: string
  icon: string
  isCache: number
  isDisplay: number
}

export interface PoemMenuDrop {
  menuIds: Array<string>
  parentMenuId?: string
}
  