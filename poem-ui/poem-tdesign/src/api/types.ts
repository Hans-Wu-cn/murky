/**
 * @param createTime 创建时间
 * @param updateTime 修改时间
 * @param createUser 创建人
 * @param createTime 修改人
 */
export interface BaseApiType {
  createTime?: Date;
  updateTime?: Date;
  createUser?: number;
  updateUser?: number;
}
