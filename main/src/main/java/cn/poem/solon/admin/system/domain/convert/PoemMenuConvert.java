package cn.poem.solon.admin.system.domain.convert;

import cn.poem.solon.admin.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.system.domain.dto.PoemMenuFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PoemMenu实体转化接口
 *
 * @author 吴志豪
 */
@Mapper
public interface PoemMenuConvert {
    PoemMenuConvert INSTANCES = Mappers.getMapper(PoemMenuConvert.class);

    /**
     * 将PoemMenuFromDTO转为PoemMenu
     * @param poemMenuSaveDTO
     * @return
     */
    PoemMenu toPoemMenu(PoemMenuFromDTO poemMenuSaveDTO);

    /**
     * poemMenuToPoemMenuTreeVO 菜单实体转树形菜单vo
     * @param poemMenu
     * @return
     */
    List<PoemMenuTreeVO> poemMenuToPoemMenuTreeVO(List<PoemMenu> poemMenu);
}
