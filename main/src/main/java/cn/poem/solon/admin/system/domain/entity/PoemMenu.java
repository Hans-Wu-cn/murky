package cn.poem.solon.admin.system.domain.entity;

import cn.poem.mybatisplus.extension.BaseEntity;
import cn.poem.solon.admin.system.enums.MenuType;
import cn.poem.solon.admin.system.enums.OpenType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("菜单实体对象")
public class PoemMenu extends BaseEntity implements Serializable {
    @TableId
    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单标题")
    private String label;

    @ApiModelProperty("菜单副标题")
    private String subtitle;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
    private OpenType openType;

    @ApiModelProperty("权限字符")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentMenuId;

    @ApiModelProperty("菜单类型 1:侧边菜单 2:按钮")
    private MenuType type;

    @ApiModelProperty("排序")
    private Short order;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;
}
