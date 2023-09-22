package cn.poem.solon.admin.gen.domain.convert;

import cn.poem.solon.admin.gen.domain.dto.GenTableDTO;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.system.domain.entity.PoemDept;
import cn.poem.solon.admin.system.domain.vo.PoemDeptTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * genTable相关转化类
 *
 * @author hans
 */
@Mapper
public interface GenTableConvert {
    GenTableConvert INSTANCES = Mappers.getMapper(GenTableConvert.class);

    /**
     * 将PoemDept转为PoemDeptTreeVO
     * @param genTableDTO 部门实体对象
     * @return 部门树视图对象
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    GenTable toEntity(GenTableDTO genTableDTO);
}
