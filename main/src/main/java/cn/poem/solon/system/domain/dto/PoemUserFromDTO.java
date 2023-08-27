package cn.poem.solon.system.domain.dto;

import cn.poem.solon.core.validat.Update;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.enums.Sex;
import cn.poem.solon.system.domain.convert.PoemUserConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
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

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(value = "性别 0:男性 1:女性 2:其他",required = true)
    @NotNull
    private Sex sex;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮箱")
    private List<Long> roleIds;

    public PoemUser toEntity(){
        return PoemUserConvert.INSTANCES.toEntity(this);
    }

}
