package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import com.baomidou.mybatisplus.solon.service.IService;

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
}
