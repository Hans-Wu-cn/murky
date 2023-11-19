package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("商户表格脚本表单类")
public class PoemSaasScriptTableFromDTO {
    @ApiModelProperty("表格ID")
    @NotNull(groups = Update.class)
    private Long tableId;

    @ApiModelProperty("表名")
    @NotNull
    private String tableName;

    @ApiModelProperty("描述")
    private String describe;


    @ApiModelProperty("标签")
    @NotNull
    private Integer tag;

    @ApiModelProperty("状态(0:正常  1:停用)")
    @NotNull
    private CommonStatus status;

}
