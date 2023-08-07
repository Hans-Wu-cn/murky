package cn.poem.solon.system.domain.vo;

import cn.poem.solon.system.enums.MenuCacheType;
import cn.poem.solon.system.enums.MenuDisplayType;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.enums.MenuOpenType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树视图类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单树视图类")
public class PoemMenuTreeVO implements Serializable {
    @ApiModelProperty("菜单标题")
    private String label;

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单类型 0:目录 1:侧边菜单 2:按钮")
    private MenuType type;

    @ApiModelProperty("副标题")
    private String subtitle;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
    private MenuOpenType openType;

    @ApiModelProperty("是否开启缓存 0:关闭  1:开启 ")
    private MenuCacheType isCache;

    @ApiModelProperty("是否显示在菜单  0:显示  1:隐藏")
    private MenuDisplayType isDisplay;

    @ApiModelProperty("权限字符")
    private String auth;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("排序")
    private Short sort;

    @ApiModelProperty("上级菜单id")
    private Long parentMenuId;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("下级菜单")
    private List<PoemMenuTreeVO> children;
}
