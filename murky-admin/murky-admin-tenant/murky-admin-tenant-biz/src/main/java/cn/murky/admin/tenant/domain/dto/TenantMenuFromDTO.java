package cn.murky.admin.tenant.domain.dto;

import cn.murky.admin.tenant.domain.convert.TenantMenuConvert;
import cn.murky.admin.tenant.domain.entity.TenantMenu;
import cn.murky.admin.tenant.enums.*;
import cn.murky.core.validat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;
/**
 * 租户菜单表单类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("租户菜单表单类")
public class TenantMenuFromDTO {
    @ApiModelProperty("租户菜单id")
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "菜单标题", required = true)
    @NotBlank
    private String label;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty(value = "打开方式 1:当前窗口  2:新窗口")
    private MenuOpenType openType;

    @ApiModelProperty("权限码")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentId;

    @ApiModelProperty(value = "菜单类型 0:目录 1:侧边菜单 2:按钮", required = true)
    @NotNull
    private TenantMenuType type;

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

    public TenantMenu toEntity() {
        return TenantMenuConvert.INSTANCES.toEntity(this);
    }
}
