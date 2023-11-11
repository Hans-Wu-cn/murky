package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.security.entity.PoemMenuTree;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.system.domain.convert.PoemMenuConvert;
import cn.poem.solon.admin.system.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.domain.entity.table.PoemMenuTableDef;
import cn.poem.solon.admin.system.domain.entity.table.PoemRoleMenuTableDef;
import cn.poem.solon.admin.system.mapper.PoemMenuMapper;
import cn.poem.solon.admin.system.mapper.PoemRoleMenuMapper;
import cn.poem.solon.admin.system.service.IPoemMenuService;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 菜单service实现
 *
 * @author hans
 */
@Component
public class IPoemMenuServiceImpl extends ServiceImpl<PoemMenuMapper, PoemMenu> implements IPoemMenuService {

    @Inject
    private PoemRoleMenuMapper poemRoleMenuMapper;

    /**
     * 菜单排序接口,设置菜单排序并统一设定父级菜单
     *
     * @param poemMenuDropDTO 菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    @Tran
    @Override
    public Boolean drop(PoemMenuDropDTO poemMenuDropDTO) {
        IPoemMenuServiceImpl bean = Solon.context().getBean(this.getClass());
        List<PoemMenu> poemMenuList = new ArrayList<>();
        List<Long> menuIds = poemMenuDropDTO.getMenuIds();
        for (int i = 0; i < menuIds.size(); i++) {
            poemMenuList.add(new PoemMenu().setMenuId(menuIds.get(i))
                    .setSort(Short.parseShort(String.valueOf(i)))
                    .setParentMenuId(poemMenuDropDTO.getParentMenuId()));
        }
        return bean.updateBatch(poemMenuList);
    }

    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    @Override
    public List<PoemMenuTree> treePoemMenu(List<MenuType> menuTypes) {
        List<PoemMenu> allPoemMenuList = mapper.selectByMenuType(menuTypes, SecurityUtils.isAdmin() ? null : SecurityUtils.getUserInfo().getRoleIds());
        List<PoemMenuTree> poemMenuTreeVOS = PoemMenuConvert.INSTANCES.toEntity(allPoemMenuList);
        List<PoemMenuTree> list = poemMenuTreeVOS.stream().filter(item -> item.getParentMenuId() == 0).toList();
        buildTreePoemMenu(list, poemMenuTreeVOS);
        return list;
    }

    /**
     * 删除菜单业务
     *
     * @param id 数据主键
     * @return 删除成功状态
     */
    @Override
    @Tran
    public boolean removeById(Serializable id) {
        long count = mapper.selectCountByQuery(
                QueryWrapper.create().from(PoemMenuTableDef.POEM_MENU).where(PoemMenuTableDef.POEM_MENU.PARENT_MENU_ID.eq(id))
        );
        if (count > 0) {
            throw new ServiceException("删除失败,请保证该菜单没有子级菜单");
        }
        long l = poemRoleMenuMapper.selectCountByQuery(QueryWrapper.create().where(
                PoemRoleMenuTableDef.POEM_ROLE_MENU.MENU_ID.eq(id)
        ));
        if (l > 0) {
            throw new ServiceException("删除失败,请保证该菜单没有被角色引用");
        }
        return super.removeById(id);
    }

    /**
     * 构建菜单树
     *
     * @param parentMenuList 父级菜单
     * @param poemMenuList   菜单资源池
     */
    private void buildTreePoemMenu(List<PoemMenuTree> parentMenuList, List<PoemMenuTree> poemMenuList) {
        for (PoemMenuTree poemMenuTreeVO : parentMenuList) {
            List<PoemMenuTree> treePoemMenu = new ArrayList<>();
            for (PoemMenuTree poemMenu : poemMenuList) {
                if (poemMenu.getParentMenuId().equals(poemMenuTreeVO.getMenuId())) {
                    treePoemMenu.add(poemMenu);
                }
            }
            buildTreePoemMenu(treePoemMenu, poemMenuList);
            poemMenuTreeVO.setChildren(treePoemMenu);
        }
    }
}
