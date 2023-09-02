package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.vo.PoemDeptTreeVO;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 部门service
 * @author hans
 */
public interface IPoemDeptService extends IService<PoemDept> {

    /**
     * 获取部门树
     * @return 部门树视图对象
     */
    List<PoemDeptTreeVO> treeDept();
}
