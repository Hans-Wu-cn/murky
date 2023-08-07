package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.dto.PoemMenuDropDTO;
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
     * 菜单排序接口,设置菜单排序并统一设定父级菜单
     * @param poemMenuDropDTO 菜单拖动接口参数实体对象
     * @return 是否修改成功
     */
    Boolean drop(PoemMenuDropDTO poemMenuDropDTO);

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
