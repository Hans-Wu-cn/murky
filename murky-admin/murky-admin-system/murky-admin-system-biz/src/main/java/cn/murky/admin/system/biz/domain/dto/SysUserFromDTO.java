package cn.murky.admin.system.biz.domain.dto;

import cn.murky.admin.system.biz.convert.SysUserConvert;
import cn.murky.admin.system.api.enums.Sex;
import cn.murky.admin.system.biz.domain.entity.SysUser;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.Email;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 用户表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户表单类")
public class SysUserFromDTO {
    @ApiModelProperty("用户id")
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank
    private String userName;

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank
    private String account;


    @ApiModelProperty(value = "性别 0:男性 1:女性 2:其他",required = true)
    @NotNull
    private Sex sex;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty(value = "部门id",required = true)
    private Long fkDeptId;

    @ApiModelProperty("角色id")
    private Long fkRoleId;

    public SysUser toEntity(){
        return SysUserConvert.INSTANCES.toEntity(this);
    }

}
