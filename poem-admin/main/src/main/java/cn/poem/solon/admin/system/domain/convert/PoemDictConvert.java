package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.dto.PoemDictDataFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemDictTypeFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemMenuFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemDict实体转化接口
 * @author hans
 */
@Mapper
public interface PoemDictConvert {
    PoemDictConvert INSTANCES = Mappers.getMapper(PoemDictConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemDictType toEntity(PoemDictTypeFromDTO poemDictTypeFromDTO);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemDictData toEntity(PoemDictDataFromDTO poemDictDataFromDTO);
}
