package cn.murky.admin.system.biz.domain.entity;

import cn.murky.common.domain.entity.BaseEntity;
import cn.murky.admin.system.api.enums.Sex;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@Table("sys_user")
public class SysUser extends BaseEntity<SysUser> {

    /**
     * 用户id
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 0:男性 1:女性 2:其他
     */
    private Sex sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门id
     */
    private Long fkDeptId;

    /**
     * 语言
     */
    private String language;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 角色Id
     */
    private Long fkRoleId;

}
