package cn.poem.solon.admin.saas.service.impl;


import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasMenuConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasMenuTreeVo;
import cn.poem.solon.admin.saas.mapper.PoemSaasMenuMapper;
import cn.poem.solon.admin.saas.service.IPoemSaasMenuService;
import cn.poem.solon.admin.security.entity.PoemMenuTree;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;

/**
 * PoemSaasMenu商户菜单service
 *
 * @author hans
 */
@Component
public class IPoemSaasMenuServiceImpl extends PoemServiceImpl<PoemSaasMenuMapper, PoemSaasMenu> implements IPoemSaasMenuService {

    @Inject
    private IPoemSaasMenuService iPoemSaasMenuService;
    /**
     * 获取商户树形菜单
     *
     * @return 商户菜单树视图对象
     */
    @Override
    public List<PoemSaasMenuTreeVo> treePoemMenu(List<MenuType> menuTypes) {
        List<PoemSaasMenu> allPoemMenuList = mapper.selectByMenuType(menuTypes);
        List<PoemSaasMenuTreeVo> poemSaasMenuTreeVos = PoemSaasMenuConvert.INSTANCES.toEntity(allPoemMenuList);
        List<PoemSaasMenuTreeVo> list = poemSaasMenuTreeVos.stream().filter(item -> item.getParentSaasMenuId() == 0).toList();
        buildTreePoemMenu(list, poemSaasMenuTreeVos);
        return list;
    }

    /**
     * 商户菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param poemMenuDropDTO 商户菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    @Tran
    @Override
    public Boolean drop(PoemMenuDropDTO poemMenuDropDTO) {
        List<PoemSaasMenu> poemMenuList = new ArrayList<>();
        List<Long> menuIds = poemMenuDropDTO.getSaasMenuIds();
        for (int i = 0; i < menuIds.size(); i++) {
            poemMenuList.add(new PoemSaasMenu().setSaasMenuId(menuIds.get(i))
                    .setSort(Short.parseShort(String.valueOf(i)))
                    .setParentSaasMenuId(poemMenuDropDTO.getParentSaasMenuId()));
        }
        return iPoemSaasMenuService.updateBatch(poemMenuList);
    }

    /**
     * 构建商户菜单树
     *
     * @param parentMenuList 父级菜单
     * @param poemMenuList   菜单资源池
     */
    private void buildTreePoemMenu(List<PoemSaasMenuTreeVo> parentMenuList, List<PoemSaasMenuTreeVo> poemMenuList) {
        for (PoemSaasMenuTreeVo poemMenuTreeVO : parentMenuList) {
            List<PoemSaasMenuTreeVo> treePoemMenu = new ArrayList<>();
            for (PoemSaasMenuTreeVo poemMenu : poemMenuList) {
                if (poemMenu.getParentSaasMenuId().equals(poemMenuTreeVO.getSaasMenuId())) {
                    treePoemMenu.add(poemMenu);
                }
            }
            buildTreePoemMenu(treePoemMenu, poemMenuList);
            poemMenuTreeVO.setChildren(treePoemMenu);
        }
    }
}
