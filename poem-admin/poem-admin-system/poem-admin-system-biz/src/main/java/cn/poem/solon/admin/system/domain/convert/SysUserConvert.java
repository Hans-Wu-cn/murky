package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.system.domain.dto.SysUserFromDTO;
import cn.poem.solon.admin.system.domain.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemUser实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCES = Mappers.getMapper(SysUserConvert.class);

    /**
     * 将PoemUserFromDTO转为PoemUser
     */
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser toEntity(SysUserFromDTO sysUserFromDTO);

    /**
     * 将PoemUser转为PoemUserVo
     */
    @Mapping(target = "roleIds", ignore = true)
    SysUserVo toVo(SysUser sysUser);
}
