package cn.poem.solon.admin.saas.service.impl;

import cn.poem.solon.admin.saas.domain.entity.PoemTenantUser;
import cn.poem.solon.admin.saas.mapper.PoemTenantUserMapper;
import cn.poem.solon.admin.saas.service.IPoemTenantUserService;
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
