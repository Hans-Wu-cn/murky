package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.SystemParameterDTO;
import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * SystemParameter实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SystemParameterConvert {
    SystemParameterConvert INSTANCES = Mappers.getMapper(SystemParameterConvert.class);
    /**
     * 将SystemParameterDTO转为SystemParameter
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SystemParameter toEntity(SystemParameterDTO PoemUserFromDTO);
}
