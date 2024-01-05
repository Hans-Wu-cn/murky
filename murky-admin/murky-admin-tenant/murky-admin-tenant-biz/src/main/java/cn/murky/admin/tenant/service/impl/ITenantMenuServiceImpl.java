package cn.murky.admin.tenant.service.impl;


import cn.murky.admin.tenant.domain.convert.TenantMenuConvert;
import cn.murky.admin.tenant.mapper.TenantMenuMapper;
import cn.murky.admin.tenant.domain.dto.TenantMenuDropDTO;
import cn.murky.admin.tenant.domain.entity.TenantMenu;
import cn.murky.admin.tenant.domain.vo.TenantMenuTreeVo;
import cn.murky.admin.tenant.enums.TenantMenuType;
import cn.murky.admin.tenant.service.ITenantMenuService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;

/**
 * TenantMenu商户菜单service
 *
 * @author hans
 */
@Component
public class ITenantMenuServiceImpl extends ServiceImpl<TenantMenuMapper, TenantMenu> implements ITenantMenuService {

    @Inject
    private ITenantMenuService iTenantMenuService;
    /**
     * 获取商户树形菜单
     *
     * @return 商户菜单树视图对象
     */
    @Override
    public List<TenantMenuTreeVo> treeSysMenu(List<TenantMenuType> menuTypes) {
        List<TenantMenu> allTenantMenuList = mapper.selectByMenuType(menuTypes);
        List<TenantMenuTreeVo> tenantMenuTreeVos = TenantMenuConvert.INSTANCES.toEntity(allTenantMenuList);
        List<TenantMenuTreeVo> list = tenantMenuTreeVos.stream().filter(item -> item.getParentTenantMenuId() == 0).toList();
        buildTreeMenu(list, tenantMenuTreeVos);
        return list;
    }

    /**
     * 商户菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param tenantMenuDropDTO 商户菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    @Tran
    @Override
    public Boolean drop(TenantMenuDropDTO tenantMenuDropDTO) {
        List<TenantMenu> tenantMenuList = new ArrayList<>();
        List<Long> menuIds = tenantMenuDropDTO.getSaasMenuIds();
        for (int i = 0; i < menuIds.size(); i++) {
            tenantMenuList.add(new TenantMenu().setTenantMenuId(menuIds.get(i))
                    .setSort(Short.parseShort(String.valueOf(i)))
                    .setParentTenantMenuId(tenantMenuDropDTO.getParentSaasMenuId()));
        }
        return iTenantMenuService.updateBatch(tenantMenuList);
    }

    /**
     * 构建商户菜单树
     *
     * @param parentMenuList 父级菜单
     * @param menuList   菜单资源池
     */
    private void buildTreeMenu(List<TenantMenuTreeVo> parentMenuList, List<TenantMenuTreeVo> menuList) {
        for (TenantMenuTreeVo tenantMenuTreeVo : parentMenuList) {
            List<TenantMenuTreeVo> treeMenu = new ArrayList<>();
            for (TenantMenuTreeVo menu : menuList) {
                if (menu.getParentTenantMenuId().equals(tenantMenuTreeVo.getTenantMenuId())) {
                    treeMenu.add(menu);
                }
            }
            buildTreeMenu(treeMenu, menuList);
            tenantMenuTreeVo.setChildren(treeMenu);
        }
    }
}
