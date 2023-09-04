package cn.poem.solon.system.mapper;

import cn.poem.solon.core.utils.CollectionUtils;
import cn.poem.solon.system.domain.entity.table.PoemRoleMenuTableDef;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.entity.table.PoemMenuTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

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
                                        ).when(CollectionUtils.isNotEmpty(roleids))
                        )
                        .orderBy(POEM_MENU.SORT.asc(), POEM_MENU.LABEL.asc())
        );
    }
}
