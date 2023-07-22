package cn.poem.mybatisplus.extension;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Date updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Long updateUser;
}

