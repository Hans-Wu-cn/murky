package cn.poem.solon.admin.gen.domain.dto;

import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.enums.GenType;
import cn.poem.solon.admin.gen.enums.TplCategory;
import com.mybatisflex.annotation.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@ApiModel
@Data
public class GenTableDTO implements Serializable {
    @Id
    @NotBlank(groups = Update.class)
    private Long tableId;

    @NotBlank
    @ApiModelProperty(value = "表名称",required = true)
    private String tableName;

    @NotBlank
    @ApiModelProperty(value = "表描述",required = true)
    private String tableComment;

    @ApiModelProperty("关联父表的表名")
    private String subTableName;

    @ApiModelProperty("本表关联父表的外键名")
    private String subTableFkName;

    @NotBlank
    @ApiModelProperty(value = "实体类名称(首字母大写)",required = true)
    private String className;

    @ApiModelProperty("用的模板（crud单表操作 tree树表操作 sub主子表操作）")
    private TplCategory tplCategory;

    @NotBlank
    @ApiModelProperty(value = "生成包路径",required = true)
    private String packageName;

    @NotBlank
    @ApiModelProperty(value = "生成模块名",required = true)
    private String moduleName;

    @NotBlank
    @ApiModelProperty(value = "生成业务名",required = true)
    private String businessName;

    @NotBlank
    @ApiModelProperty(value = "生成功能名",required = true)
    private String functionName;

    @NotBlank
    @ApiModelProperty(value = "生成作者",required = true)
    private String functionAuthor;

    @ApiModelProperty("生成代码方式（0zip压缩包 1自定义路径）")
    private GenType genType;

    @ApiModelProperty("生成路径（不填默认项目路径）")
    private String genPath;

    @ApiModelProperty("主键信息")
    private GenTableColumn pkColumn;

    @ApiModelProperty("子表信息")
    private GenTable subTable;

    @Validated
    @ApiModelProperty(value = "表列信息",required = true)
    private List<GenTableColumn> columns;

    @ApiModelProperty("其它生成选项")
    private String options;

    @ApiModelProperty("树编码字段")
    private String treeCode;

    @ApiModelProperty("树父编码字段")
    private String treeParentCode;

    @ApiModelProperty("树名称字段")
    private String treeName;

    @ApiModelProperty("上级菜单ID字段")
    private String parentMenuId;

    @ApiModelProperty("上级菜单名称字段")
    private String parentMenuName;

    @ApiModelProperty("备注")
    private String remark;
}
