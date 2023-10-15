package cn.poem.solon.admin.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.system.domain.dto.LoginDto;

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
