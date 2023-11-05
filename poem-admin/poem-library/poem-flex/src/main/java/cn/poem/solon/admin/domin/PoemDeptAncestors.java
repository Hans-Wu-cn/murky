package cn.poem.solon.admin.domin;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门祖级关系
 * @author hans
 */
@Data
@Accessors(chain = true)
@Table("poem_dept_ancestors")
public class PoemDeptAncestors {
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 祖级部门Id
     */
    private Long ancestors;
}
