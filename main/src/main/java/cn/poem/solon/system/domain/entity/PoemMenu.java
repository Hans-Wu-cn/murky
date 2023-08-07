package cn.poem.solon.system.domain.entity;

import cn.poem.mybatisflex.extension.BaseEntity;
import cn.poem.solon.system.enums.MenuCacheType;
import cn.poem.solon.system.enums.MenuDisplayType;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.enums.MenuOpenType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单实体类")
@Table("poem_menu")
public class PoemMenu extends BaseEntity implements Serializable {
    @Id
    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单标题")
    private String label;

    @ApiModelProperty("菜单副标题")
    private String subtitle;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
    private MenuOpenType openType;

    @ApiModelProperty("权限字符")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentMenuId;

    @ApiModelProperty("菜单类型 1:侧边菜单 2:按钮")
    private MenuType type;

    @ApiModelProperty("是否开启缓存 0:关闭  1:开启")
    private MenuCacheType isCache;

    @ApiModelProperty("是否显示在菜单  0:显示  1:隐藏")
    private MenuDisplayType isDisplay;

    @ApiModelProperty("排序")
    private Short sort;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;
}
