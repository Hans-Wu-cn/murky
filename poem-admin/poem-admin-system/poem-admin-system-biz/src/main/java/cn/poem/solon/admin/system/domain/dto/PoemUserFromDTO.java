package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.validat.Update;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.enums.Sex;
import cn.poem.solon.admin.system.domain.convert.PoemUserConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.Email;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * 用户表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("用户表单类")
public class PoemUserFromDTO {
    @ApiModelProperty("用户id")
    @NotNull(groups = Update.class)
    private Long userId;

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
    private Long deptId;

    @ApiModelProperty("角色id")
    private List<Long> roleIds;

    public PoemUser toEntity(){
        return PoemUserConvert.INSTANCES.toEntity(this);
    }

}
