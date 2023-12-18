package cn.poem.solon.admin.system.api.domian;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
    private LocalDateTime createTime;
}
