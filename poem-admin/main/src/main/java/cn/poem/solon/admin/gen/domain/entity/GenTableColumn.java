package cn.poem.solon.admin.gen.domain.entity;


import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.domin.BaseEntity;
import cn.poem.solon.admin.gen.enums.HtmlType;
import cn.poem.solon.admin.gen.enums.QueryType;
import cn.poem.solon.admin.gen.enums.YesOrNo;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

/**
 * 代码生成业务字段表 gen_table_column
 * 
 * @author ruoyi
 */
@Data
@Accessors(chain = true)
@ApiModel("角色实体类")
@Table("gen_table_column")
public class GenTableColumn extends BaseEntity
{
    @Id
    @NotBlank(groups = Update.class)
    @ApiModelProperty("主键")
    private Long columnId;


    @ApiModelProperty("归属表编号")
    private Long tableId;


    @ApiModelProperty("列名称")
    private String columnName;


    @ApiModelProperty("列描述")
    private String columnComment;


    @ApiModelProperty("列类型")
    private String columnType;


    @ApiModelProperty("java类型")
    private String javaType;

    @NotBlank
    @ApiModelProperty(value = "java字段名",required = true)
    private String javaField;


    @ApiModelProperty("是否主键（0:否 1是）")
    private YesOrNo pk;


    @ApiModelProperty("是否自增（0:否 1:是）")
    private YesOrNo increment;


    @ApiModelProperty("是否必填（0:否 1:是）")
    private YesOrNo required;


    @ApiModelProperty("是否为插入字段（0:否 1:是）")
    private YesOrNo insert;


    @ApiModelProperty("是否编辑字段（0:否 1:是）")
    private YesOrNo edit;


    @ApiModelProperty("是否编辑字段（0:否  1:是）")
    private YesOrNo list;


    @ApiModelProperty("是否查询字段（0:否 1是）")
    private YesOrNo query;


    @ApiModelProperty("查询方式（0:等于、1:不等于、2:大于、3:小于、4:范围）")
    private QueryType queryType;


    @ApiModelProperty("显示类型（0:文本框、1:文本域、2:下拉框、3:复选框、4:单选框、5:日期控件）")
    private HtmlType htmlType;


    @ApiModelProperty("字典类型")
    private String dictType;


    @ApiModelProperty("排序")
    private Integer sort;

    public Boolean isPk(){
        return YesOrNo.YES==pk;
    }


}