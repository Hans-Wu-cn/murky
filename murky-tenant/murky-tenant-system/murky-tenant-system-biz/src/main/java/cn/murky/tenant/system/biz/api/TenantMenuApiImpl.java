package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantMenuApi;
import cn.murky.tenant.system.api.domain.bo.TenantMenuBO;
import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.system.biz.convert.TenantMenuConvert;
import cn.murky.tenant.system.biz.domian.entity.Tenant;
import cn.murky.tenant.system.biz.domian.entity.TenantGroupMenu;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import cn.murky.tenant.system.biz.mapper.TenantGroupMenuMapper;
import cn.murky.tenant.system.biz.service.ITenantMenuService;
import cn.murky.tenant.system.biz.service.ITenantService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Component
public class TenantMenuApiImpl implements TenantMenuApi {
    @Inject
    private ITenantMenuService iTenantMenuService;
    @Inject
    private ITenantService iTenantService;
    @Inject
    private TenantGroupMenuMapper tenantGroupMenuMapper;

    /**
     * 根据租户id查询权限
     *
     * @param fkTenantId 租户id
     */
    @Override
    public List<TenantMenuBO> getByFkTenantId(Long fkTenantId) {
        Tenant tenant = iTenantService.getById(fkTenantId);
        List<Long> tenantMenuIds = tenantGroupMenuMapper.selectListByFkGroupId(tenant.getFkGroupId())
                .stream().map(TenantGroupMenu::getFkMenuId).toList();
        List<TenantMenu> tenantMenus = iTenantMenuService.listByIds(tenantMenuIds);
        return TenantMenuConvert.INSTANCES.toBOs(tenantMenus);
    }

    /**
     * 根据角色id查询权限
     *
     * @param fkRoleId 角色id
     */
    @Override
    public List<TenantMenuBO> getByFkRoleId(List<MenuType> menuTypes, Long fkRoleId){
        List<TenantMenu> tenantMenus = iTenantMenuService.getByFkRoleId(menuTypes, fkRoleId);
        return TenantMenuConvert.INSTANCES.toBOs(tenantMenus);
    }

    @Override
    public List<TenantMenuTreeVO> getByAuth(List<String> auths) {
        List<TenantMenu> tenantMenus = iTenantMenuService.getListByAuths(auths);
        return TenantMenuConvert.INSTANCES.toTreeVOs(tenantMenus);
    }
}
