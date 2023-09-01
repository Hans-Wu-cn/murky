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

/**
 * @param pageNumber 页数
 * @param pageSize 每页数据量
 * @param totalPage 总页数
 * @param records 数据
 */
export interface PageRequest {
  pageNumber: number;
  pageSize: number;
}

/**
 * @param pageNumber 页数
 * @param pageSize 每页数据量
 * @param totalPage 总页数
 * @param records 数据
 */
export interface PageResponse<T> {
  pageNumber: number;
  pageSize: number;
  records:T;
}