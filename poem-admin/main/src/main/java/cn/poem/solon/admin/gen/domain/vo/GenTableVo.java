package cn.poem.solon.admin.gen.domain.vo;

import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.enums.GenType;
import cn.poem.solon.admin.gen.enums.TplCategory;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.RelationOneToMany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@ApiModel
@Data
public class GenTableVo implements Serializable {
    @Id
    private Long tableId;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("表描述")
    private String tableComment;

    @ApiModelProperty("关联父表的表名")
    private String subTableName;

    @ApiModelProperty("本表关联父表的外键名")
    private String subTableFkName;

    @ApiModelProperty("实体类名称(首字母大写)")
    private String className;

    @ApiModelProperty("用的模板（crud单表操作 tree树表操作 sub主子表操作）")
    private TplCategory tplCategory;

    @ApiModelProperty("生成包路径")
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

    @ApiModelProperty("主键信息")
    private GenTableColumn pkColumn;

    @ApiModelProperty("子表信息")
    private GenTable subTable;

    @ApiModelProperty("表列信息")
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
