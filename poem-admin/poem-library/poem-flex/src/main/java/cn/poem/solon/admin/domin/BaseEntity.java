package cn.poem.solon.admin.domin;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共base实体
 * 方便字段填充
 * @author hans
 */
@Getter
@Setter
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    protected LocalDateTime createTime;

    /**
     * 修改时间
     */
    protected LocalDateTime updateTime;

    /**
     * 创建人
     */
    protected Long createUser;

    /**
     * 修改人
     */
    protected Long updateUser;

}
