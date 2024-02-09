package cn.murky.common.domain.entity;

import com.mybatisflex.core.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * 公共base实体
 * 方便字段填充
 * @author hans
 */
@Getter
@Setter
public class BaseEntity<T extends Model<T>> extends Model<T>implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

