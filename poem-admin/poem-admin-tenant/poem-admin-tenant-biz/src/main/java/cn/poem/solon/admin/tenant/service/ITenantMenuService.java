package cn.poem.solon.admin.tenant.service;

import cn.poem.solon.admin.tenant.domain.dto.TenantMenuDropDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantMenu;
import cn.poem.solon.admin.tenant.domain.vo.TenantMenuTreeVo;
import cn.poem.solon.admin.tenant.enums.TenantMenuType;
import com.mybatisflex.core.service.IService;

import java.util.List;


/**
 * 商户菜单Service
 *
 * @Author hans
 */
public interface ITenantMenuService extends IService<TenantMenu> {

    /**
     * 获取商户树形菜单
     *
     * @return 商户菜单树视图对象
     */
    List<TenantMenuTreeVo> treePoemMenu(List<TenantMenuType> menuTypes);

    /**
     * 商户菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param tenantMenuDropDTO 菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    Boolean drop(TenantMenuDropDTO tenantMenuDropDTO);
}
