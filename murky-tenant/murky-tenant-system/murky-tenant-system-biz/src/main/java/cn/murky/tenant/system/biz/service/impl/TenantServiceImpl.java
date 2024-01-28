package cn.murky.tenant.system.biz.service.impl;

import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.system.biz.domian.entity.Tenant;
import cn.murky.tenant.system.biz.domian.entity.TenantMenu;
import cn.murky.tenant.system.biz.mapper.TenantMapper;
import cn.murky.tenant.system.biz.service.ITenantService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

import java.util.List;

@Component
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

}
