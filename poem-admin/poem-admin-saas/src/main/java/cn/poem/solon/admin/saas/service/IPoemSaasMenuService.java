package cn.poem.solon.admin.saas.service;

import cn.poem.solon.admin.saas.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasMenuTreeVo;
import cn.poem.solon.admin.security.entity.PoemMenuTree;
import cn.poem.solon.admin.security.enums.MenuType;
import com.mybatisflex.core.service.IService;

import java.util.List;


/**
 * 商户菜单Service
 *
 * @Author hans
 */
public interface IPoemSaasMenuService extends IService<PoemSaasMenu> {

    /**
     * 获取商户树形菜单
     *
     * @return 商户菜单树视图对象
     */
    List<PoemSaasMenuTreeVo> treePoemMenu(List<MenuType> menuTypes);

    /**
     * 商户菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param poemMenuDropDTO 菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    Boolean drop(PoemMenuDropDTO poemMenuDropDTO);
}
