package cn.poem.solon.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.entity.SecurityUser;
import cn.poem.solon.expand.SecurityUserInfo;
import cn.poem.solon.system.domain.dto.LoginDto;

/**
 * 登录service
 *
 * @author hans
 */
public interface IPoemLoginService {

    SaTokenInfo login(LoginDto loginDto);

    /**
     * 获取用户登录信息
     *
     * @return 用户信息对象
     */
    SecurityUserInfo userInfo();

}
