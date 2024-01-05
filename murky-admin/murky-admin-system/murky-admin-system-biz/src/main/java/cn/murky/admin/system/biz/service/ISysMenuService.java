package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.dto.SysMenuDropDTO;
import cn.murky.admin.system.api.domian.SysMenuTree;
import cn.murky.admin.system.api.enums.MenuType;
import cn.murky.admin.system.biz.domain.entity.SysMenu;
import com.mybatisflex.core.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单service
 *
 * @author hans
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param sysMenuDropDTO 菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    Boolean drop(SysMenuDropDTO sysMenuDropDTO);

    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    List<SysMenuTree> treeSysMenu(List<MenuType> menuTypes);

    /**
     * 删除菜单业务
     * @param id 数据主键
     * @return 删除成功状态
     */
    boolean removeById(Serializable id);


}
