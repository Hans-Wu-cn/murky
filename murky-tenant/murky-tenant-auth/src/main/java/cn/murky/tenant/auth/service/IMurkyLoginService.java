package cn.murky.tenant.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.tenant.auth.domain.dto.LoginDto;
import cn.murky.tenant.core.SecurityTenantUserInfo;


/**
 * 登录service
 *
 * @author hans
 */
public interface IMurkyLoginService {

    /**
     * 登录
     */
    SaTokenInfo login(LoginDto loginDto);

    /**
     * 获取用户登录信息
     *
     * @return 用户信息对象
     */
    SecurityTenantUserInfo userInfo();

}
