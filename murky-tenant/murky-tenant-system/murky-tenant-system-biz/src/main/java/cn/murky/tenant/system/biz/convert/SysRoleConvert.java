package cn.murky.tenant.system.biz.convert;

import cn.murky.tenant.system.api.domain.bo.SysRoleBO;
import cn.murky.tenant.system.biz.domian.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCES = Mappers.getMapper(SysRoleConvert.class);

    SysRoleBO toBO(SysRole sysRole);
}
