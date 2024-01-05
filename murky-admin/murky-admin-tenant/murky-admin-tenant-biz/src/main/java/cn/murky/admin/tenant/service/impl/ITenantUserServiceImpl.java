package cn.murky.admin.tenant.service.impl;

import cn.murky.admin.tenant.mapper.TenantUserMapper;
import cn.murky.admin.tenant.domain.entity.TenantUser;
import cn.murky.admin.tenant.service.ITenantUserService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

/**
 * TenantUserService
 *
 * @Author hans
 */
@Component
public class ITenantUserServiceImpl extends ServiceImpl<TenantUserMapper, TenantUser> implements ITenantUserService {
}
