package cn.poem.solon.admin.domin;

import cn.poem.solon.admin.common.entity.BaseEntity;
import cn.poem.solon.admin.enums.Sex;
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
@Table("poem_user")
public class PoemUser extends BaseEntity {

    /**
     * 用户id
     */
    @Id
    private Long userId;

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
     * 邮箱
     */
    private Long deptId;

    /**
     * 语言
     */
    private String language;

    /**
     * 密码加密盐值
     */
    private String salt;

}
