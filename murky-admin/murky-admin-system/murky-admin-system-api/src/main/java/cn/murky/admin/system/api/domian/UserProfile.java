package cn.murky.admin.system.api.domian;

import cn.murky.admin.system.api.enums.Sex;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 用户文档对象
 */
@Data
@Accessors(chain = true)
public class UserProfile {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Sex sex;

    /**
     * 所属角色
     */
    private List<String> roleNameList;

    /**
     * 所属部门
     */
    private List<String> deptNameList;

    /**
     * 创建日期
     */
    private OffsetDateTime createTime;
}
