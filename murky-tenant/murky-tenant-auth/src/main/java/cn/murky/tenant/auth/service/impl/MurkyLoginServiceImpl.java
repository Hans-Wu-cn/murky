package cn.murky.tenant.auth.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.common.enums.DataScope;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import cn.murky.tenant.auth.domain.dto.LoginDto;
import cn.murky.tenant.auth.service.IMurkyLoginService;
import cn.murky.tenant.core.SecurityTenantUserInfo;
import cn.murky.tenant.core.utils.SecurityUtils;
import cn.murky.tenant.system.api.TenantMenuApi;
import cn.murky.tenant.system.api.TenantRoleApi;
import cn.murky.tenant.system.api.TenantUserApi;
import cn.murky.tenant.system.api.domain.bo.TenantMenuBO;
import cn.murky.tenant.system.api.domain.bo.SysRoleBO;
import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import cn.murky.tenant.system.api.enums.MenuType;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static cn.murky.tenant.auth.constant.ErrorConstant.ACCOUNT_PASSWORD_ERROR;
import static cn.murky.tenant.core.constant.Constants.TENANT_ID_HEADER;
import static cn.murky.tenant.system.api.constant.TenantSystemConstant.TENANT_SYSTEM_ADMIN_FK_ROLE_ID;
import static cn.murky.tenant.system.api.constant.TenantSystemConstant.TENANT_SYSTEM_ADMIN_ROLE_CODE;

@Component
public class MurkyLoginServiceImpl implements IMurkyLoginService {
    @Inject
    private TenantRoleApi tenantRoleApi;
    @Inject
    private TenantUserApi tenantUserApi;
    @Inject
    private TenantMenuApi tenantMenuApi;

    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        TenantUserBO tenantUser = tenantUserApi.getOneByAccount(loginDto.getAccount());
        //如果为空抛出异常
        Optional.ofNullable(tenantUser).orElseThrow(() -> new ServiceException(ACCOUNT_PASSWORD_ERROR));
        String encryPassword = EncryptionUtil.userEncryption(new PasswordRecord(tenantUser.getSalt(), loginDto.getPassword()));
        if (!tenantUser.getPassword().equals(encryPassword)) {
            throw new ServiceException(ACCOUNT_PASSWORD_ERROR);
        }
        SecurityUtils.setTenantId(tenantUser.getFkTenantId());
        // 第1步，先登录
        StpUtil.login(tenantUser.getId());
        // 第2步，获取 Token  相关参数
        return StpUtil.getTokenInfo();
    }

    /**
     * 获取用户登录信息
     *
     * @return 用户信息对象
     */
    @Override
    public SecurityTenantUserInfo userInfo() {
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
//        SecurityTenantUserInfo userInfoCache = SecurityUtils.getUserInfo();
        SecurityTenantUserInfo userInfoCache = (SecurityTenantUserInfo)StpUtil.getTokenSession().get("userInfo");

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            TenantUserBO tenantUser = tenantUserApi.getById(loginId);
            SecurityTenantUserInfo userInfo = new SecurityTenantUserInfo()
                    .setUserId(loginId)
                    .setEmail(tenantUser.getEmail())
                    .setUserName(tenantUser.getUserName())
                    .setLanguage(tenantUser.getLanguage())
                    .setFkDeptId(tenantUser.getFkDeptId())
                    .setFkRoleId(tenantUser.getFkRoleId())
                    .setToken(tokenInfo.getTokenValue())
                    .setTenantId(tenantUser.getFkTenantId())
                    .setAdmin(tenantUser.getAdmin());
            if (tenantUser.getAdmin()) {
                userInfo.setRoleCode(TENANT_SYSTEM_ADMIN_ROLE_CODE);
                userInfo.setFkRoleId(TENANT_SYSTEM_ADMIN_FK_ROLE_ID);
                userInfo.setDataScope(DataScope.ALL);
                List<String> permissions = tenantMenuApi.getByFkTenantId(tenantUser.getFkTenantId())
                        .stream().map(TenantMenuBO::getAuth).toList();
                userInfo.setPermissions(permissions);
            } else {
                //查询角色code列表
                SysRoleBO sysRoleBO = tenantRoleApi.getSysRoleById(tenantUser.getFkRoleId());
                userInfo.setRoleCode(sysRoleBO.getRoleCode());
                //查询数据权限信息
                userInfo.setDataScope(sysRoleBO.getDataScope());
                //查询权限列表
                List<String> permissions = tenantMenuApi.getByFkRoleId(
                                Arrays.asList(MenuType.BUTTON, MenuType.MENU, MenuType.DIRECTORY)
                                , sysRoleBO.getId())
                        .stream().map(TenantMenuBO::getAuth).toList();
                userInfo.setPermissions(permissions);
            }
            SecurityUtils.setUserInfo(userInfo);
            return userInfo;
        });
    }
}
