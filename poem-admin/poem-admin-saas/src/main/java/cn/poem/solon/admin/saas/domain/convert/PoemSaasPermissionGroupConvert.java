package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasPermissionGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemSaasPermissionGroup实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemSaasPermissionGroupConvert {
    PoemSaasPermissionGroupConvert INSTANCES = Mappers.getMapper(PoemSaasPermissionGroupConvert.class);

    /**
     * 将PoemSaasPermissionGroupFromDTO转为PoemSaasPermissionGroup
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemSaasPermissionGroup toEntity(PoemSaasPermissionGroupFromDTO poemRoleFromDTO);

    /**
     * 将PoemSaasPermissionGroup entity对象转化为PoemSaasPermissionGroupVo对象
     */
    @Mapping(target = "saasMenuIds", ignore = true)
    PoemSaasPermissionGroupVo toVo(PoemSaasPermissionGroup poemRole);
}
