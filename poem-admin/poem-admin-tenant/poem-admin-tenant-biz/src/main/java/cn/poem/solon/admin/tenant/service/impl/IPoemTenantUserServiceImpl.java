package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.tenant.domain.entity.PoemTenantUser;
import cn.poem.solon.admin.tenant.mapper.PoemTenantUserMapper;
import cn.poem.solon.admin.tenant.service.IPoemTenantUserService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

/**
 * PoemTenantUser Servuce
 *
 * @Author hans
 */
@Component
public class IPoemTenantUserServiceImpl extends ServiceImpl<PoemTenantUserMapper, PoemTenantUser> implements IPoemTenantUserService {
}
