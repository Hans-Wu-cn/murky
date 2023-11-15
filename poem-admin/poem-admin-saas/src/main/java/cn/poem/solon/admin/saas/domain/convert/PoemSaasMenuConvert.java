package cn.poem.solon.admin.saas.domain.convert;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasMenuFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasMenuTreeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemSaasMenu实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemSaasMenuConvert {
    PoemSaasMenuConvert INSTANCES = Mappers.getMapper(PoemSaasMenuConvert.class);


    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemSaasMenu toEntity(PoemSaasMenuFromDTO poemMenuSaveDTO);

    List<PoemSaasMenuTreeVo> toEntity(List<PoemSaasMenu> poemSaasMenu);


}
