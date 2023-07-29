package cn.poem.solon.system.domain.convert;

import cn.poem.solon.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.system.domain.entity.PoemUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * PoemUser实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemUserConvert {
    PoemUserConvert INSTANCES = Mappers.getMapper(PoemUserConvert.class);

    /**
     * 将PoemUserFromDTO转为PoemUser
     * @param PoemUserFromDTO
     * @return
     */
    PoemUser toEntity(PoemUserFromDTO PoemUserFromDTO);
}
