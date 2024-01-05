package cn.murky.admin.tenant.domain.convert;

import cn.murky.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.murky.admin.tenant.domain.vo.TenantPermissionGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * TenantPermission实体转化接口
 *
 * @author hans
 */
@Mapper
public interface TenantPermissionGroupConvert {
    TenantPermissionGroupConvert INSTANCES = Mappers.getMapper(TenantPermissionGroupConvert.class);

    /**
     * 将TenantPermissionGroupFromDTO转为TenantPermissionGroup
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    TenantPermissionGroup toEntity(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO);

    /**
     * 将TenantPermissionGroup entity对象转化为TenantPermissionGroupVo对象
     */
    @Mapping(target = "tenantMenuIds", ignore = true)
    TenantPermissionGroupVo toVo(TenantPermissionGroup tenantPermissionGroup);
}
