package cn.poem.solon.admin.saas.domain.entity;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.domin.BaseEntity;
import cn.poem.solon.admin.saas.enums.ColumnIndex;
import cn.poem.solon.admin.saas.enums.ColumnIndexType;
import cn.poem.solon.admin.saas.enums.ColumnPrimary;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("商户字段脚本实体类")
@Table(value = "poem_saas_script_columns")
public class PoemSaasScriptColumns extends BaseEntity {
    @Id
    @ApiModelProperty("字段Id")
    private Long columnId;

    @ApiModelProperty("表格Id")
    private Long tableId;

    @ApiModelProperty("字段名")
    private String columnName;

    @ApiModelProperty("字段类型")
    private String columnType;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("是否主键(0:非主键   1:主键)")
    private ColumnPrimary isPrimary;

    @ApiModelProperty("0:无索引   1:普通索引   2:唯一索引")
    private ColumnIndexType indexType;

    @ApiModelProperty("默认值")
    private String defaultValue;

    @ApiModelProperty("状态(0:正常  1:停用)")
    private CommonStatus status;
}
