package cn.murky.admin.system.biz.domain.dto;

import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("系统参数实体类")
public class SystemParameterDTO {
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty("参数key")
    @NotBlank
    private String key;

    @ApiModelProperty("参数值")
    @NotBlank
    private String value;

    @ApiModelProperty("描述")
    private String describe;
}
