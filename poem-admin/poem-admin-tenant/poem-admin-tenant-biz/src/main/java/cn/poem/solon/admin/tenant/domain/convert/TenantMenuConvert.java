package cn.poem.solon.admin.tenant.domain.convert;

import cn.poem.solon.admin.tenant.domain.dto.TenantMenuFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantMenu;
import cn.poem.solon.admin.tenant.domain.vo.TenantMenuTreeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemTenantMenu实体转化接口
 *
 * @author hans
 */
@Mapper
public interface TenantMenuConvert {
    TenantMenuConvert INSTANCES = Mappers.getMapper(TenantMenuConvert.class);


    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    TenantMenu toEntity(TenantMenuFromDTO tenantMenuFromDTO);

    List<TenantMenuTreeVo> toEntity(List<TenantMenu> tenantMenus);


}
