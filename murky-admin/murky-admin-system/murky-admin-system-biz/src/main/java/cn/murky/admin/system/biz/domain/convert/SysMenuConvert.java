package cn.murky.admin.system.biz.domain.convert;

import cn.murky.admin.system.biz.domain.dto.SysMenuFromDTO;
import cn.murky.admin.system.biz.domain.entity.SysMenu;
import cn.murky.admin.system.api.domian.SysMenuTree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysMenu实体转化接口
 *
 * @author hans
 */
@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCES = Mappers.getMapper(SysMenuConvert.class);

    /**
     * 将SysMenuFromDTO转为SysMenu
     */
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysMenu toEntity(SysMenuFromDTO SysMenuSaveDTO);

    /**
     * SysMenu 菜单实体转树形菜单vo
     */
    List<SysMenuTree> toEntity(List<SysMenu> SysMenu);
}
