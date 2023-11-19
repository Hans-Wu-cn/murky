package cn.poem.solon.admin.saas.domain.entity;


import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.domin.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("商户表格脚本实体类")
@Table(value = "poem_saas_script_table")
public class PoemSaasScriptTable extends BaseEntity {

    @Id
    @ApiModelProperty("表格ID")
    private Long tableId;

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("标签")
    private Integer tag;

    @ApiModelProperty("状态(0:正常  1:停用)")
    private CommonStatus status;
}
