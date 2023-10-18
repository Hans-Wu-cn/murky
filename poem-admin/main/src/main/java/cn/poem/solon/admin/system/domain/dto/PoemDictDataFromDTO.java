package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.enums.DictStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

/**
 * 字典数据表单DTO实体
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("字典数据表单DTO实体")
public class PoemDictDataFromDTO {

    @ApiModelProperty("字典编码")
    @NotNull(groups = Update.class)
    private Long dictCode;

    @ApiModelProperty("字典排序")
    private Short dictSort;

    @ApiModelProperty("字典类型")
    @NotBlank
    private String dictType;

    @ApiModelProperty("字典标签")
    @NotBlank
    private String dictLabel;

    @ApiModelProperty("字典值")
    @NotBlank
    private String dictValue;

    @ApiModelProperty("状态")
    private DictStatus status;

    @ApiModelProperty("备注")
    private String remark;
}
