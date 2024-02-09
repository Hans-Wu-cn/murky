package cn.murky.admin.system.biz.domain.dto;

import cn.murky.common.enums.CommonStatus;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 字典类型表单DTO实体
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("字典类型表单DTO实体")
public class SysDictTypeFromDTO {

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "字典名称",required = true)
    @NotBlank
    private String dictName;

    @ApiModelProperty(value = "字典类型",required = true)
    @NotBlank
    private String dictType;

    @ApiModelProperty("字典状态")
    private CommonStatus status;

    @ApiModelProperty("备注")
    private String remark;
}
