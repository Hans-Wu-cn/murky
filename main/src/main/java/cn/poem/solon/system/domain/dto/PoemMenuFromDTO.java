package cn.poem.solon.system.domain.dto;


import cn.poem.core.validat.Update;
import cn.poem.solon.system.domain.convert.PoemMenuConvert;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.noear.snack.ONode;
import org.noear.snack.ONodeData;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;


/**
 * 菜单表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单表单类")
public class PoemMenuFromDTO{
    @ApiModelProperty("菜单id")
    @NotNull(groups = Update.class)
    private Long menuId;

    @ApiModelProperty("菜单标题")
    @NotBlank
    private String label;

    @ApiModelProperty("菜单副标题")
    private String subtitle;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
    private MenuOpenType openType;

    @ApiModelProperty("权限码")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentMenuId;

    @ApiModelProperty("菜单类型 1:侧边菜单 2:按钮")
    @NotNull
    private MenuType type;

    @ApiModelProperty("是否开启缓存 0:关闭  1:开启 ")
    private MenuCacheType isCache;

    @ApiModelProperty("是否显示在菜单  0:显示  1:隐藏")
    private MenuDisplayType isDisplay;

    @ApiModelProperty("是否使用外链  0:否  1:是")
    private MenuOutside isOutside;

    @ApiModelProperty("排序")
    private Short sort;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("路由参数")
    private String query;

    public PoemMenu toEntity(){
        return PoemMenuConvert.INSTANCES.toEntity(this);
    }
}
