package cn.murky.tenant.system.biz.convert;

import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import cn.murky.tenant.system.biz.domian.entity.TenantUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantUserConvert {
    TenantUserConvert INSTANCES = Mappers.getMapper(TenantUserConvert.class);

    TenantUserBO toBO(TenantUser tenantUser);
}
