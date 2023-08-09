package cn.poem.solon.system.mapper;

import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.entity.table.PoemMenuTableDef;
import cn.poem.solon.system.enums.MenuType;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface PoemMenuMapper extends BaseMapper<PoemMenu> {

    default List<PoemMenu> selectByMenuType(List<MenuType> menuTypes){
        return this.selectListByQuery(
                QueryWrapper.create()
                        .select().from(PoemMenuTableDef.POEM_MENU)
                        .where(PoemMenuTableDef.POEM_MENU.TYPE.in(menuTypes))
                        .orderBy(PoemMenuTableDef.POEM_MENU.SORT.asc(),PoemMenuTableDef.POEM_MENU.LABEL.asc())
        );
    }
}
