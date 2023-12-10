package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.vo.PoemUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     */
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemUser toEntity(PoemUserFromDTO PoemUserFromDTO);

    /**
     * 将PoemUser转为PoemUserVo
     */
    @Mapping(target = "roleIds", ignore = true)
    PoemUserVo toVo(PoemUser poemUser);
}
