package cn.poem.solon.system.domain.convert;

import cn.poem.solon.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.system.domain.entity.PoemRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * PoemRole实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemRoleConvert {
    PoemRoleConvert INSTANCES = Mappers.getMapper(PoemRoleConvert.class);

    /**
     * 将PoemRoleFromDTO转为PoemMenu
     * @param poemRoleFromDTO
     * @return
     */
    PoemRole toEntity(PoemRoleFromDTO poemRoleFromDTO);
}
