package cn.murky.admin.system.api.domian.bo;

import cn.murky.admin.system.api.enums.Sex;
import cn.murky.common.domain.entity.BaseObj;
import com.mybatisflex.annotation.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysUserBO extends BaseObj {

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
}
