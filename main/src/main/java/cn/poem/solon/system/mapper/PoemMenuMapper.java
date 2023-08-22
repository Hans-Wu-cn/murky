package cn.poem.solon.system.mapper;

import cn.poem.solon.core.utils.CollectionUtils;
import cn.poem.solon.system.contant.AdminContant;
import cn.poem.solon.system.domain.entity.table.PoemRoleMenuTableDef;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.entity.table.PoemMenuTableDef;
import cn.poem.solon.utils.SecurityUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.Collection;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.exists;
import static com.mybatisflex.core.query.QueryMethods.noCondition;

public interface PoemMenuMapper extends BaseMapper<PoemMenu> {


    default List<PoemMenu> selectByMenuType(List<MenuType> menuTypes, Collection<Long> roleids) {
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(PoemMenuTableDef.POEM_MENU)
                        .where(PoemMenuTableDef.POEM_MENU.TYPE.in(menuTypes))
                        //超级管理员查询全部数据
                        .and(
                                exists
                                        (
                                                QueryWrapper.create().select(PoemRoleMenuTableDef.POEM_ROLE_MENU.MENU_ID)
                                                        .from(PoemRoleMenuTableDef.POEM_ROLE_MENU)
                                                        .where(PoemRoleMenuTableDef.POEM_ROLE_MENU.ROLE_ID.in(roleids))
                                                        .and(PoemMenuTableDef.POEM_MENU.MENU_ID.eq(PoemRoleMenuTableDef.POEM_ROLE_MENU.MENU_ID))
                                        ).when(CollectionUtils.isNotEmpty(roleids))
                        )
                        .orderBy(PoemMenuTableDef.POEM_MENU.SORT.asc(), PoemMenuTableDef.POEM_MENU.LABEL.asc())
        );
    }
}
