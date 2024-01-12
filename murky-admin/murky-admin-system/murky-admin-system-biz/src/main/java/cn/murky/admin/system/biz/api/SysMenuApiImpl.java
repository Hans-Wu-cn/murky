package cn.murky.admin.system.biz.api;

import cn.murky.admin.system.api.SysMenuApi;
import cn.murky.admin.system.api.domian.SysMenuTree;
import cn.murky.admin.system.api.enums.MenuType;
import cn.murky.admin.system.biz.service.ISysMenuService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * MenuApi
 */
@Component
public class SysMenuApiImpl implements SysMenuApi {
    @Inject
    private ISysMenuService iSysMenuService;

    @Override
    public List<SysMenuTree> treeSysMenu(List<MenuType> menuTypes) {
        return iSysMenuService.treeSysMenu(menuTypes);
    }
}
