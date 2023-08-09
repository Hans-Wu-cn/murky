package cn.poem.solon.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.core.utils.ApiResult;
import cn.poem.solon.auth.domain.vo.LoginUserInfoVO;
import cn.poem.solon.system.domain.dto.LoginDto;
import org.noear.solon.annotation.Body;

/**
 * 登录service
 *
 * @author hans
 */
public interface IPoemLoginService {

    SaTokenInfo login(LoginDto loginDto);

    LoginUserInfoVO userInfo();

}
