package cn.murky.security;

import cn.dev33.satoken.stp.StpInterface;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.Collections;
import java.util.List;

/**
 * sa-token 权限拓展实现
 *
 * @author hans
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Inject
    private SecurityCache securityCache;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return securityCache.getUserInfo().getPermissions();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return Collections.singletonList(securityCache.getUserInfo().getRoleCode());
    }
}
