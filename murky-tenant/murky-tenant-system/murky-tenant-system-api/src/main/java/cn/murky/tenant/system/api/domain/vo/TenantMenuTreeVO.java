package cn.murky.tenant.system.api.domain.vo;

import cn.murky.tenant.system.api.enums.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TenantMenuTreeVO implements Serializable {
    /**
     * 菜单id
     */
    private Long id;

    /**
     * 菜单标题
     */
    private String label;

    /**
     * 菜单类型 0:目录 1:侧边菜单 2:按钮
     */
    private MenuType type;

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
     * 上级菜单id
     */
    private Long parentId;

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
    private List<TenantMenuTreeVO> children;
}
