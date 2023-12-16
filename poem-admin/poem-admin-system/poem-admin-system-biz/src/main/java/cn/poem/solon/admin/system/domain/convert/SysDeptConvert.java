package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.dto.SysDeptFromDTO;
import cn.poem.solon.admin.system.domain.entity.SysDept;
import cn.poem.solon.admin.system.domain.vo.SysDeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemDept实体转化接口
 * @author hans
 */
@Mapper
public interface SysDeptConvert {
    SysDeptConvert INSTANCES = Mappers.getMapper(SysDeptConvert.class);

    /**
     * 将PoemDept转为PoemDeptTreeVO
     * @param poemDept 部门实体对象
     * @return 部门树视图对象
     */
    @Mapping(target = "children", ignore = true)
    SysDeptTreeVO toEntity(SysDept poemDept);


    /**
     * 部门实体列表转为部门树实体列表
     * @param poemDepts 部门实体列表对象
     * @return 部门树实体列表
     */
    List<SysDeptTreeVO> toEntity(List<SysDept> poemDepts);

    /**
     * 部门实体列表转为部门树实体列表
     * @param poemDeptFromDTO 部门表单实体列表对象
     * @return 部门实体
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDept toEntity(SysDeptFromDTO poemDeptFromDTO);

}
