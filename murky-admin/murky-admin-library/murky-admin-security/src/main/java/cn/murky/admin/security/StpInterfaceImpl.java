package cn.murky.admin.security;

import cn.dev33.satoken.stp.StpInterface;
import cn.murky.admin.security.utils.SecurityUtils;
import org.noear.solon.annotation.Component;

import java.util.List;

/**
 * sa-token 权限拓展实现
 *
 * @author hans
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return SecurityUtils.getUserInfo().getPermissions();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return SecurityUtils.getUserInfo().getRoleCodes();
    }
}
