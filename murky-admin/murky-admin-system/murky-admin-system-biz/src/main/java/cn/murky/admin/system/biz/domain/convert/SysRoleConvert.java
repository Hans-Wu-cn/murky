package cn.murky.admin.system.biz.domain.convert;

import cn.murky.admin.system.biz.domain.dto.SysRoleFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysRoleVo;
import cn.murky.admin.system.biz.domain.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * SysRole实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCES = Mappers.getMapper(SysRoleConvert.class);

    /**
     * 将SysRoleFromDTO转为SysRole
     */
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole toEntity(SysRoleFromDTO sysRoleFromDTO);

    /**
     * 将SysRole对象转化为SysRoleVo对象
     */
    @Mapping(target = "menuIds", ignore = true)
    @Mapping(target = "deptIds", ignore = true)
    SysRoleVo toVo(SysRole sysRole);
}
