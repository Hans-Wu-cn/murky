package cn.poem.solon.admin.saas.domain.dto;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("商户表格脚本分页DTO实体类")
public class PoemSaasScriptTablePageDTO extends Page<PoemSaasScriptTable> {

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("标签")
    private Integer tag;

    @ApiModelProperty("状态(0:正常  1:停用)")
    private CommonStatus status;
}
