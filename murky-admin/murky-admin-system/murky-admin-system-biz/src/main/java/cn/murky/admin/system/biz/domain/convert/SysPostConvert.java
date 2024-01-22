package cn.murky.admin.system.biz.domain.convert;

import cn.murky.admin.system.biz.domain.dto.SysPostFromDTO;
import cn.murky.admin.system.biz.domain.entity.SysPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * SysPost实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysPostConvert {
    SysPostConvert INSTANCES = Mappers.getMapper(SysPostConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysPost toEntity(SysPostFromDTO sysPostFromDTO);
}
