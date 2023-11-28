package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemTenantPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.saas.domain.vo.PoemTenantPermissionGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemTenantPermission实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemTenantPermissionGroupConvert {
    PoemTenantPermissionGroupConvert INSTANCES = Mappers.getMapper(PoemTenantPermissionGroupConvert.class);

    /**
     * 将PoemTenantPermissionFromDTO转为PoemTenantPermission
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemTenantPermissionGroup toEntity(PoemTenantPermissionGroupFromDTO poemRoleFromDTO);

    /**
     * 将PoemTenantPermission entity对象转化为PoemTenantPermissionVo对象
     */
    @Mapping(target = "tenantMenuIds", ignore = true)
    PoemTenantPermissionGroupVo toVo(PoemTenantPermissionGroup poemRole);
}
