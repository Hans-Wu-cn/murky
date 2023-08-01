package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.system.domain.entity.PoemMenu;
import com.mybatisflex.core.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单service
 *
 * @author hans
 */
public interface IPoemMenuService extends IService<PoemMenu> {

    /**
     * 获取树形菜单
     *
     * @return 菜单树视图对象
     */
    List<PoemMenuTreeVO> treePoemMenu();


    /**
     * 删除菜单业务
     * @param id 数据主键
     * @return 删除成功状态
     */
    boolean removeById(Serializable id);
}
