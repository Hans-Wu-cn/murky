package cn.murky.tenant.system.biz.convert;


import cn.murky.tenant.system.api.domain.bo.TenantMenuBO;
import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TenantMenuConvert {
    TenantMenuConvert INSTANCES = Mappers.getMapper(TenantMenuConvert.class);

    List<TenantMenuBO> toBOs(List<TenantMenu> tenantMenus);

    List<TenantMenuTreeVO> toTreeVOs(List<TenantMenu> tenantMenus);
}
