package cn.poem.mybatisflex.extension;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共base实体
 * 方便字段填充
 * @author hans
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 修改时间
     */
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long createUser;

    /**
     * 修改人
     */
    protected Long updateUser;
}

