package cn.murky.tenant.system.biz.service;

import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import com.mybatisflex.core.service.IService;

import java.util.List;

public interface ITenantMenuService extends IService<TenantMenu> {
    /**
     * 根据角色id查询权限
     * @param fkRoleId 角色id
     * @param menuTypes 权限类型
     */
    List<TenantMenu> getByFkRoleId(List<MenuType> menuTypes, Long fkRoleId);

    /**
     * 根据权限码查询菜单
     * @param auths 权限码集合
     */
    List<TenantMenu> getListByAuths(List<String> auths);
}
