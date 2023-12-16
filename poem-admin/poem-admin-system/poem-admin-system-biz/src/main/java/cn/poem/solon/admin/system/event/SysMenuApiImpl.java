package cn.poem.solon.admin.system.event;

import cn.poem.solon.admin.system.api.SysMenuApi;
import cn.poem.solon.admin.system.api.domian.SysMenuTree;
import cn.poem.solon.admin.system.api.enums.MenuType;
import cn.poem.solon.admin.system.service.ISysMenuService;
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
    public List<SysMenuTree> treePoemMenu(List<MenuType> menuTypes) {
        return iSysMenuService.treeSysMenu(menuTypes);
    }
}
