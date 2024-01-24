package cn.murky.admin.system.biz.domain.convert;

import cn.murky.admin.system.api.domian.bo.SysUserBO;
import cn.murky.admin.system.biz.domain.entity.SysUser;
import cn.murky.admin.system.biz.domain.dto.SysUserFromDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * MurkyUser实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCES = Mappers.getMapper(SysUserConvert.class);

    /**
     * 将SysUserFromDTO转为SysUser
     */
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser toEntity(SysUserFromDTO sysUserFromDTO);

    SysUserBO toBO(SysUser sysUser);
}
