package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.dto.SysRoleFromDTO;
import cn.poem.solon.admin.system.domain.entity.SysRole;
import cn.poem.solon.admin.system.domain.vo.SysRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemRole实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCES = Mappers.getMapper(SysRoleConvert.class);

    /**
     * 将PoemRoleFromDTO转为PoemMenu
     */
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole toEntity(SysRoleFromDTO sysRoleFromDTO);

    /**
     * 将PoemRole entity对象转化为PoemRoleVo对象
     */
    @Mapping(target = "menuIds", ignore = true)
    @Mapping(target = "deptIds", ignore = true)
    SysRoleVo toVo(SysRole sysRole);
}
