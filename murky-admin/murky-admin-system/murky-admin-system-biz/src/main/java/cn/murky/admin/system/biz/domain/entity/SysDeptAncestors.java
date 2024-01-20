package cn.murky.admin.system.biz.domain.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门祖级关系
 * @author hans
 */
@Data
@Accessors(chain = true)
@Table("sys_dept_ancestors")
public class SysDeptAncestors {
    /**
     * 部门id
     */
    private Long fkDeptId;

    /**
     * 祖级部门Id
     */
    private Long ancestors;
}
