package cn.murky.admin.system.biz.domain.dto;


import cn.murky.common.enums.DataScope;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel
public class SysPostFromDTO {
    @ApiModelProperty("岗位编码")
    @NotBlank(groups = Update.class)
    private String id;

    @ApiModelProperty("岗位名称")
    @NotBlank
    private String postName;

    @ApiModelProperty("岗位权限")
    @NotBlank
    private DataScope dataScope;
}
