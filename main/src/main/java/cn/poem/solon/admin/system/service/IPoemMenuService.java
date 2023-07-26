package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
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
     * @return
     */
    List<PoemMenuTreeVO> treePoemMenu();


//    @Override
    boolean removeById(Serializable id);
}
