package cn.poem.solon.admin.saas.service.impl;


import cn.poem.solon.admin.saas.domain.convert.PoemTenantMenuConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemTenantMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemTenantMenuTreeVo;
import cn.poem.solon.admin.saas.mapper.PoemTenantMenuMapper;
import cn.poem.solon.admin.saas.service.IPoemTenantMenuService;
import cn.poem.solon.admin.security.enums.MenuType;
import com.mybatisflex.solon.service.impl.ServiceImpl;
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
public class IPoemTenantMenuServiceImpl extends ServiceImpl<PoemTenantMenuMapper, PoemTenantMenu> implements IPoemTenantMenuService {

    @Inject
    private IPoemTenantMenuService iPoemSaasMenuService;
    /**
     * 获取商户树形菜单
     *
     * @return 商户菜单树视图对象
     */
    @Override
    public List<PoemTenantMenuTreeVo> treePoemMenu(List<MenuType> menuTypes) {
        List<PoemTenantMenu> allPoemMenuList = mapper.selectByMenuType(menuTypes);
        List<PoemTenantMenuTreeVo> poemSaasMenuTreeVos = PoemTenantMenuConvert.INSTANCES.toEntity(allPoemMenuList);
        List<PoemTenantMenuTreeVo> list = poemSaasMenuTreeVos.stream().filter(item -> item.getParentTenantMenuId() == 0).toList();
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
        List<PoemTenantMenu> poemMenuList = new ArrayList<>();
        List<Long> menuIds = poemMenuDropDTO.getSaasMenuIds();
        for (int i = 0; i < menuIds.size(); i++) {
            poemMenuList.add(new PoemTenantMenu().setTenantMenuId(menuIds.get(i))
                    .setSort(Short.parseShort(String.valueOf(i)))
                    .setParentTenantMenuId(poemMenuDropDTO.getParentSaasMenuId()));
        }
        return iPoemSaasMenuService.updateBatch(poemMenuList);
    }

    /**
     * 构建商户菜单树
     *
     * @param parentMenuList 父级菜单
     * @param poemMenuList   菜单资源池
     */
    private void buildTreePoemMenu(List<PoemTenantMenuTreeVo> parentMenuList, List<PoemTenantMenuTreeVo> poemMenuList) {
        for (PoemTenantMenuTreeVo poemMenuTreeVO : parentMenuList) {
            List<PoemTenantMenuTreeVo> treePoemMenu = new ArrayList<>();
            for (PoemTenantMenuTreeVo poemMenu : poemMenuList) {
                if (poemMenu.getParentTenantMenuId().equals(poemMenuTreeVO.getTenantMenuId())) {
                    treePoemMenu.add(poemMenu);
                }
            }
            buildTreePoemMenu(treePoemMenu, poemMenuList);
            poemMenuTreeVO.setChildren(treePoemMenu);
        }
    }
}
