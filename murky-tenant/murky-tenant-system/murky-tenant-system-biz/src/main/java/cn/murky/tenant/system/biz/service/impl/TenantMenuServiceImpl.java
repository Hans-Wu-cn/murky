package cn.murky.tenant.system.biz.service.impl;

import cn.murky.tenant.core.MurkyServiceImpl;
import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import cn.murky.tenant.system.biz.mapper.TenantMenuMapper;
import cn.murky.tenant.system.biz.service.ITenantMenuService;
import org.noear.solon.annotation.Component;

import java.util.List;

@Component
public class TenantMenuServiceImpl extends MurkyServiceImpl<TenantMenuMapper, TenantMenu> implements ITenantMenuService {
    @Override
    public List<TenantMenu> getByFkRoleId(List<MenuType> menuTypes, Long fkRoleId) {
        return mapper.selectByMenuTypeAndfkRoleid(menuTypes,fkRoleId);
    }

    @Override
    public List<TenantMenu> getListByAuths(List<String> auths) {
        return mapper.selectListByAuths(auths);
    }
}
