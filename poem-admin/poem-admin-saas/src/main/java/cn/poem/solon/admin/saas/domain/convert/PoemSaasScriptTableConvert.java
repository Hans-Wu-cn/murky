package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTableFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 商户表格脚本Converter
 */
@Mapper
public interface PoemSaasScriptTableConvert {
    PoemSaasScriptTableConvert INSTANCES = Mappers.getMapper(PoemSaasScriptTableConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemSaasScriptTable toEntity(PoemSaasScriptTableFromDTO poemSaasScriptTableFromDTO);
}
