package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemTenantPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.saas.domain.vo.PoemTenantPermissionGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemSaasPermissionGroup实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemTenantPermissionGroupConvert {
    PoemTenantPermissionGroupConvert INSTANCES = Mappers.getMapper(PoemTenantPermissionGroupConvert.class);

    /**
     * 将PoemSaasPermissionGroupFromDTO转为PoemSaasPermissionGroup
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemTenantPermissionGroup toEntity(PoemTenantPermissionGroupFromDTO poemRoleFromDTO);

    /**
     * 将PoemSaasPermissionGroup entity对象转化为PoemSaasPermissionGroupVo对象
     */
    @Mapping(target = "tenantMenuIds", ignore = true)
    PoemTenantPermissionGroupVo toVo(PoemTenantPermissionGroup poemRole);
}
