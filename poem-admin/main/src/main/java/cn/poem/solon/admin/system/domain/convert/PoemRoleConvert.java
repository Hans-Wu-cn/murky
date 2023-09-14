package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.domain.vo.PoemRoleVo;
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

    /**
     * 将PoemRole entity对象转化为PoemRoleVo对象
     * @param poemRole
     * @return
     */
    PoemRoleVo toVo(PoemRole poemRole);
}
