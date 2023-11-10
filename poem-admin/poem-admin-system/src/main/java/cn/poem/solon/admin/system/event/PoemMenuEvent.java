package cn.poem.solon.admin.system.event;

import cn.poem.solon.admin.security.entity.PoemMenuTree;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.system.service.IPoemMenuService;
import org.noear.dami.solon.annotation.DamiTopic;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * MenuEvent菜单事件监听
 */
@DamiTopic("event.menu")
public class PoemMenuEvent {
    @Inject
    private IPoemMenuService iPoemMenuService;
    public List<PoemMenuTree> treePoemMenu(List<MenuType> menuTypes) {
        return iPoemMenuService.treePoemMenu(menuTypes);
    }
}
