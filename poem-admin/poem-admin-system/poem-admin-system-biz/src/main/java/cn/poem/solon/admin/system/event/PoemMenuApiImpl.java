package cn.poem.solon.admin.system.event;

import cn.poem.solon.admin.system.api.PoemMenuApi;
import cn.poem.solon.admin.system.api.domian.PoemMenuTree;
import cn.poem.solon.admin.system.api.enums.MenuType;
import cn.poem.solon.admin.system.service.IPoemMenuService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * MenuApi
 */
@Component
public class PoemMenuApiImpl implements PoemMenuApi {
    @Inject
    private IPoemMenuService iPoemMenuService;

    @Override
    public List<PoemMenuTree> treePoemMenu(List<MenuType> menuTypes) {
        return iPoemMenuService.treePoemMenu(menuTypes);
    }
}
