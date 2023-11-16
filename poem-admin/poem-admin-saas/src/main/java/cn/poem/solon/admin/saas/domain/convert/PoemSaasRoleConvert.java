package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasRoleFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PoemRole实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemSaasRoleConvert {
    PoemSaasRoleConvert INSTANCES = Mappers.getMapper(PoemSaasRoleConvert.class);

    /**
     * 将PoemRoleFromDTO转为PoemMenu
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemSaasRole toEntity(PoemSaasRoleFromDTO poemRoleFromDTO);

    /**
     * 将PoemRole entity对象转化为PoemRoleVo对象
     */
    @Mapping(target = "menuIds", ignore = true)
    PoemSaasRoleVo toVo(PoemSaasRole poemRole);
}
