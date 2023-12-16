package cn.poem.solon.admin.tenant.domain.vo;

import cn.poem.solon.admin.tenant.enums.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
/**
 * 租户菜单树视图类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class TenantMenuTreeVo implements Serializable {

    /**
     * 租户菜单id
     */
    private Long tenantMenuId;

    /**
     * 菜单标题
     */
    private String label;

    /**
     * 菜单类型 0:目录 1:侧边菜单 2:按钮
     */
    private TenantMenuType type;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 打开方式 1:当前窗口  2:新窗口
     */
    private MenuOpenType openType;

    /**
     * 是否开启缓存 0:关闭  1:开启
     */
    private MenuCacheType isCache;

    /**
     * 是否显示在菜单  0:显示  1:隐藏
     */
    private MenuDisplayType isDisplay;

    /**
     * 是否使用外链  0:否  1:是
     */
    private MenuOutside isOutside;

    /**
     * 权限字符
     */
    private String auth;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 上级租户菜单id
     */
    private Long parentTenantMenuId;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 下级菜单
     */
    private List<TenantMenuTreeVo> children;
}
