package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.tenant.domain.entity.TenantUser;
import cn.poem.solon.admin.tenant.mapper.TenantUserMapper;
import cn.poem.solon.admin.tenant.service.ITenantUserService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

/**
 * PoemTenantUser Servuce
 *
 * @Author hans
 */
@Component
public class ITenantUserServiceImpl extends ServiceImpl<TenantUserMapper, TenantUser> implements ITenantUserService {
}
