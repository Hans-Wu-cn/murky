package cn.murky.tenant.auth.service.impl;

import cn.murky.tenant.core.utils.SecurityUtils;
import cn.murky.tenant.system.api.TenantMenuApi;
import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.auth.service.ITenantMenuService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.ArrayList;
import java.util.List;

@Component
public class TenantMenuServiceImpl implements ITenantMenuService {
    @Inject
    private TenantMenuApi tenantMenuApi;
    @Override
    public List<TenantMenuTreeVO> treeSysMenu(List<MenuType> menuTypes) {
        List<String> permissions = SecurityUtils.getUserInfo().getPermissions();
        List<TenantMenuTreeVO> allSysMenuList = tenantMenuApi.getByAuth(permissions);
        List<TenantMenuTreeVO> list = allSysMenuList.stream().filter(item -> item.getParentId() == 0).toList();
        buildTreeSysMenu(list, allSysMenuList);
        return list;
    }

    /**
     * 构建菜单树
     *
     * @param parentMenuList 父级菜单
     * @param SysMenuList   菜单资源池
     */
    private void buildTreeSysMenu(List<TenantMenuTreeVO> parentMenuList, List<TenantMenuTreeVO> SysMenuList) {
        for (TenantMenuTreeVO SysMenuTreeVO : parentMenuList) {
            List<TenantMenuTreeVO> treeSysMenu = new ArrayList<>();
            for (TenantMenuTreeVO SysMenu : SysMenuList) {
                if (SysMenu.getParentId().equals(SysMenuTreeVO.getId())) {
                    treeSysMenu.add(SysMenu);
                }
            }
            buildTreeSysMenu(treeSysMenu, SysMenuList);
            SysMenuTreeVO.setChildren(treeSysMenu);
        }
    }
}
