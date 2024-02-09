package cn.murky.common.domain.entity;

import java.time.OffsetDateTime;

public abstract class BaseObj {
    /**
     * 创建时间
     */
    protected OffsetDateTime createTime;

    /**
     * 修改时间
     */
    protected OffsetDateTime updateTime;

    /**
     * 创建人
     */
    protected Long createUser;

    /**
     * 修改人
     */
    protected Long updateUser;
}
