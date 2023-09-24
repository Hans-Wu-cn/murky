package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.system.domain.dto.PoemMenuFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemMenu实体转化接口
 *
 * @author hans
 */
@Mapper
public interface PoemMenuConvert {
    PoemMenuConvert INSTANCES = Mappers.getMapper(PoemMenuConvert.class);

    /**
     * 将PoemMenuFromDTO转为PoemMenu
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    PoemMenu toEntity(PoemMenuFromDTO poemMenuSaveDTO);

    /**
     * PoemMenu 菜单实体转树形菜单vo
     */
    List<PoemMenuTreeVO> toEntity(List<PoemMenu> poemMenu);
}
