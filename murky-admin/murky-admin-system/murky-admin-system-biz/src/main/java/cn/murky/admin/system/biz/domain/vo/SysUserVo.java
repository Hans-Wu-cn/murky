package cn.murky.admin.system.biz.domain.vo;

import cn.murky.admin.flex.enums.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户视图类，包含角色信息
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户视图类")
public class SysUserVo {
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别 0:男性 1:女性 2:其他")
    private Sex sex;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门id")
    private String deptId;

    @ApiModelProperty("角色id")
    private List<Long> roleIds;

}
