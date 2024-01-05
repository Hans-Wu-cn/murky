package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.dto.SysDeptFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysDeptTreeVO;
import cn.murky.admin.system.biz.domain.entity.SysDept;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 部门service
 * @author hans
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 获取部门树
     * @return 部门树视图对象
     */
    List<SysDeptTreeVO> treeDept();

    /**
     * 新增部门
     * @return 新增状态
     */
    Boolean save(SysDeptFromDTO sysDeptFromDTO);

    /**
     * 修改部门
     * @return 修改状态
     */
    Boolean edit(SysDeptFromDTO sysDeptFromDTO);

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
