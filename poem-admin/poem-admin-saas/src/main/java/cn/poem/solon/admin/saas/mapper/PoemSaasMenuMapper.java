package cn.poem.solon.admin.saas.mapper;


import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasMenuTableDef;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.Utils;

import java.util.Collection;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.exists;

/**
 * 商户菜单Mapper
 *
 * @Author hans
 */
public interface PoemSaasMenuMapper extends BaseMapper<PoemSaasMenu> {
    PoemSaasMenuTableDef POEM_SAAS_MENU=PoemSaasMenuTableDef.POEM_SAAS_MENU;

    /**
     * 根据menuTypes查询
     * @param menuTypes 菜单类型
     * @return PoemSaasMenu
     */
    default List<PoemSaasMenu> selectByMenuType(List<MenuType> menuTypes) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select().from(POEM_SAAS_MENU)
                .where(POEM_SAAS_MENU.TYPE.in(menuTypes))
                .orderBy(POEM_SAAS_MENU.SORT.asc(), POEM_SAAS_MENU.LABEL.asc());

        return this.selectListByQuery(queryWrapper);
    }
}
