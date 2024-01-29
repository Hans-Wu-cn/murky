package cn.murky.admin.system.biz.convert;

import cn.murky.admin.system.biz.domain.dto.SysDeptFromDTO;
import cn.murky.admin.system.biz.domain.entity.SysDept;
import cn.murky.admin.system.biz.domain.vo.SysDeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysDept实体转化接口
 * @author hans
 */
@Mapper
public interface SysDeptConvert {
    SysDeptConvert INSTANCES = Mappers.getMapper(SysDeptConvert.class);

    /**
     * 将SysDept转为SysDeptTreeVO
     * @param sysDept 部门实体对象
     * @return 部门树视图对象
     */
    @Mapping(target = "children", ignore = true)
    SysDeptTreeVO toVO(SysDept sysDept);


    /**
     * 部门实体列表转为部门树实体列表
     * @param sysDepts 部门实体列表对象
     * @return 部门树实体列表
     */
    List<SysDeptTreeVO> toVOs(List<SysDept> sysDepts);

    /**
     * 部门实体列表转为部门树实体列表
     * @param sysDeptFromDTO 部门表单实体列表对象
     * @return 部门实体
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDept toEntity(SysDeptFromDTO sysDeptFromDTO);

}
