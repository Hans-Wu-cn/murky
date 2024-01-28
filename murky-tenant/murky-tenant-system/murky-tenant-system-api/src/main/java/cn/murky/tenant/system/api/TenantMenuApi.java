package cn.murky.tenant.system.api;

import cn.murky.tenant.system.api.domain.bo.TenantMenuBO;
import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.api.enums.MenuType;

import java.util.List;

public interface TenantMenuApi {

    /**
     * 根据租户id查询权限
     * @param fkTenantId 租户id
     */
    List<TenantMenuBO> getByFkTenantId(Long fkTenantId);

    /**
     * 根据角色id查询权限
     * @param fkRoleId 角色id
     * @param menuTypes 权限类型
     */
    List<TenantMenuBO> getByFkRoleId(List<MenuType> menuTypes,Long fkRoleId);

    /**
     * 根据权限码查询菜单
     *
     * @param auths 权限码集合
     */
    List<TenantMenuTreeVO> getByAuth(List<String> auths);
}
