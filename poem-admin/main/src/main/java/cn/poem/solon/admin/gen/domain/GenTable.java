package cn.poem.solon.admin.gen.domain;

import cn.poem.solon.admin.domin.BaseEntity;
import cn.poem.solon.admin.gen.enums.GenType;
import cn.poem.solon.admin.gen.enums.TplCategory;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 业务表 gen_table
 * 
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色实体类")
@Table("gen_table")
public class GenTable extends BaseEntity implements Serializable
{
    @Id
    @ApiModelProperty("table_id")
    private Long tableId;


    @ApiModelProperty("表名")
    private String tableName;


    @ApiModelProperty("表描述")
    private String tableDescribe;


    @ApiModelProperty("实体类名称")
    private String className;


    @ApiModelProperty("关联父表的表名")
    private String subTableName;


    @ApiModelProperty("本表关联父表的外键名")
    private String subTableFkName;


    @ApiModelProperty("使用的模板（0单表操作1树表操作 2主子表操作）")
    private TplCategory tplCategory;


    @ApiModelProperty("生成包名")
    private String packageName;


    @ApiModelProperty("生成模块名")
    private String moduleName;


    @ApiModelProperty("生成业务名")
    private String businessName;


    @ApiModelProperty("生成功能名")
    private String functionName;


    @ApiModelProperty("生成作者")
    private String functionAuthor;


    @ApiModelProperty("生成代码方式（0zip压缩包 1自定义路径）")
    private GenType genType;


    @ApiModelProperty("生成路径（不填默认项目路径）")
    private String genPath;


    @ApiModelProperty("其它生成选项")
    private String options;


    @ApiModelProperty("备注")
    private String remark;

}