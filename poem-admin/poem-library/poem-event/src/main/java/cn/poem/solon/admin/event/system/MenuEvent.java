package cn.poem.solon.admin.event.system;

import cn.poem.solon.admin.event.system.enums.MenuType;
import cn.poem.solon.admin.event.system.vo.PoemMenuTreeVO;
import org.noear.dami.solon.annotation.DamiTopic;

import java.util.List;

/**
 * 菜单事件
 */
@DamiTopic("event.menu")
public interface MenuEvent {

    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    List<PoemMenuTreeVO> treePoemMenu(List<MenuType> menuTypes);
}
