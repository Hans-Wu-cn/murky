package cn.poem.solon.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.system.domain.dto.LoginDto;

/**
 * 登录service
 *
 * @author hans
 */
public interface IPoemLoginService {

    SaTokenInfo login(LoginDto loginDto);

    UserInfo userInfo();

}
