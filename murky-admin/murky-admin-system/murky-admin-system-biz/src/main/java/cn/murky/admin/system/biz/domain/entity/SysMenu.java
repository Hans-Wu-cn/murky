package cn.murky.admin.system.biz.domain.entity;

import cn.murky.admin.system.api.enums.*;
import cn.murky.common.domain.entity.BaseEntity;
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
@Table(value = "sys_menu")
public class SysMenu extends BaseEntity<SysMenu> implements Serializable {
    @Id
    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("菜单标题")
    private String label;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("打开方式 1:当前窗口  2:新窗口")
    private MenuOpenType openType;

    @ApiModelProperty("权限字符")
    private String auth;

    @ApiModelProperty("上级菜单id")
    private Long parentId;

    @ApiModelProperty("菜单类型 1:侧边菜单 2:按钮")
    private MenuType type;

    @ApiModelProperty("是否开启缓存 0:关闭  1:开启")
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

}
