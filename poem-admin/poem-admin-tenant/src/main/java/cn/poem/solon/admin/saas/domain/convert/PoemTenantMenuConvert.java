package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemTenantMenuFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemTenantMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemTenantMenuTreeVo;
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
public interface PoemTenantMenuConvert {
    PoemTenantMenuConvert INSTANCES = Mappers.getMapper(PoemTenantMenuConvert.class);


    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemTenantMenu toEntity(PoemTenantMenuFromDTO poemMenuSaveDTO);

    List<PoemTenantMenuTreeVo> toEntity(List<PoemTenantMenu> poemSaasMenu);


}
