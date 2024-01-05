package cn.murky.admin.system.biz.domain.convert;

import cn.murky.admin.system.biz.domain.dto.SysDictDataFromDTO;
import cn.murky.admin.system.biz.domain.dto.SysDictTypeFromDTO;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * SysDict实体转化接口
 * @author hans
 */
@Mapper
public interface SysDictConvert {
    SysDictConvert INSTANCES = Mappers.getMapper(SysDictConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDictType toEntity(SysDictTypeFromDTO sysDictTypeFromDTO);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDictData toEntity(SysDictDataFromDTO sysDictDataFromDTO);
}
