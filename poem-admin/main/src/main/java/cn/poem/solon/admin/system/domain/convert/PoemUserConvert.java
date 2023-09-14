package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.vo.PoemUserVo;
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

    /**
     * 将PoemUser转为PoemUserVo
     * @param poemUser
     * @return PoemUserVo
     */
    PoemUserVo toVo(PoemUser poemUser);
}
