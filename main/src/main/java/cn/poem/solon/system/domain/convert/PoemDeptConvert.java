package cn.poem.solon.system.domain.convert;

import cn.poem.solon.system.domain.dto.PoemDeptFromDTO;
import cn.poem.solon.system.domain.dto.PoemMenuFromDTO;
import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.vo.PoemDeptTreeVO;
import cn.poem.solon.system.domain.vo.PoemMenuTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemDept实体转化接口
 * @author hans
 */
@Mapper
public interface PoemDeptConvert {
    PoemDeptConvert INSTANCES = Mappers.getMapper(PoemDeptConvert.class);

    /**
     * 将PoemDept转为PoemDeptTreeVO
     * @param poemDept 部门实体对象
     * @return 部门树视图对象
     */
    PoemDeptTreeVO toEntity(PoemDept poemDept);


    /**
     * 部门实体列表转为部门树实体列表
     * @param poemDepts 部门实体列表对象
     * @return 部门树实体列表
     */
    List<PoemDeptTreeVO> toEntity(List<PoemDept> poemDepts);

    /**
     * 部门实体列表转为部门树实体列表
     * @param poemDeptFromDTO 部门表单实体列表对象
     * @return 部门实体
     */
    PoemDept toEntity(PoemDeptFromDTO poemDeptFromDTO);

}
