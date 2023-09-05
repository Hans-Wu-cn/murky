package cn.poem.solon.system.service;

import cn.poem.solon.system.domain.dto.PoemDeptFromDTO;
import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.vo.PoemDeptTreeVO;
import com.mybatisflex.core.service.IService;

import java.rmi.ServerException;
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

    /**
     * 新增部门
     * @return 新增状态
     */
    Boolean save(PoemDeptFromDTO poemDeptFromDTO);

    /**
     * 修改部门
     * @return 修改状态
     */
    Boolean edit(PoemDeptFromDTO poemDeptFromDTO);

    /**
     * 删除部门
     * @return 删除状态
     */
    Boolean remove(Long deptId);

    /**
     * 拖动排序
     * @return 删除状态
     */
    Boolean drop(List<Long> deptIds);
}
