package cn.poem.solon.admin.tenant.domain.convert;

import cn.poem.solon.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.vo.TenantPermissionGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemTenantPermission实体转化接口
 *
 * @author hans
 */
@Mapper
public interface TenantPermissionGroupConvert {
    TenantPermissionGroupConvert INSTANCES = Mappers.getMapper(TenantPermissionGroupConvert.class);

    /**
     * 将PoemTenantPermissionFromDTO转为PoemTenantPermission
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    TenantPermissionGroup toEntity(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO);

    /**
     * 将PoemTenantPermission entity对象转化为PoemTenantPermissionVo对象
     */
    @Mapping(target = "tenantMenuIds", ignore = true)
    TenantPermissionGroupVo toVo(TenantPermissionGroup tenantPermissionGroup);
}
