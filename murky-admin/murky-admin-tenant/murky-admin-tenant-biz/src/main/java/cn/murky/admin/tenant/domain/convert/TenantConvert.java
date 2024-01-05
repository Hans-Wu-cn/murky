package cn.murky.admin.tenant.domain.convert;

import cn.murky.admin.tenant.domain.dto.TenantFromDTO;
import cn.murky.admin.tenant.domain.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Tenant实体转化接口
 *
 * @author hans
 */
@Mapper
public interface TenantConvert {
    TenantConvert INSTANCES = Mappers.getMapper(TenantConvert.class);

    Tenant toEntity(TenantFromDTO tenantFromDTO);
}
