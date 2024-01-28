package cn.murky.tenant.auth.service;

import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.api.enums.MenuType;

import java.util.List;

public interface ITenantMenuService {
    List<TenantMenuTreeVO> treeSysMenu(List<MenuType> menuTypes);

}
