package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.domain.vo.PoemRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemRole实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemRoleConvert {
    PoemRoleConvert INSTANCES = Mappers.getMapper(PoemRoleConvert.class);

    /**
     * 将PoemRoleFromDTO转为PoemMenu
     */
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemRole toEntity(PoemRoleFromDTO poemRoleFromDTO);

    /**
     * 将PoemRole entity对象转化为PoemRoleVo对象
     */
    @Mapping(target = "menuIds", ignore = true)
    @Mapping(target = "deptIds", ignore = true)
    PoemRoleVo toVo(PoemRole poemRole);
}
