package cn.poem.solon.admin.system.mapper;


import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.domain.entity.table.PoemMenuTableDef;
import cn.poem.solon.admin.system.domain.entity.table.PoemRoleMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.Utils;

import java.util.Collection;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.exists;

public interface PoemMenuMapper extends BaseMapper<PoemMenu> {
    PoemMenuTableDef POEM_MENU = PoemMenuTableDef.POEM_MENU;
    PoemRoleMenuTableDef POEM_ROLE_MENU = PoemRoleMenuTableDef.POEM_ROLE_MENU;

    default List<PoemMenu> selectByMenuType(List<MenuType> menuTypes, Collection<Long> roleids) {

        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(POEM_MENU)
                        .where(POEM_MENU.TYPE.in(menuTypes))
                        //超级管理员查询全部数据
                        .and(
                                exists
                                        (
                                                QueryWrapper.create().select(POEM_ROLE_MENU.MENU_ID)
                                                        .from(POEM_ROLE_MENU)
                                                        .where(POEM_ROLE_MENU.ROLE_ID.in(roleids))
                                                        .and(POEM_MENU.MENU_ID.eq(POEM_ROLE_MENU.MENU_ID))
                                        ).when(Utils.isNotEmpty(roleids))
                        )
                        .orderBy(POEM_MENU.SORT.asc(), POEM_MENU.LABEL.asc())
        );
    }

    default List<PoemMenu> selectByListByIds(List<Long> menuIds) {

        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(POEM_MENU)
                        .where(POEM_MENU.MENU_ID.in(menuIds))
                        .orderBy(POEM_MENU.SORT.asc(), POEM_MENU.LABEL.asc())
        );
    }

}
