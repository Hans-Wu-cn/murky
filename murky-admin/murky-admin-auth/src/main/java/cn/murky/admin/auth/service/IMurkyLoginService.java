package cn.murky.admin.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.murky.admin.auth.domain.dto.LoginDto;
import cn.murky.admin.common.entity.SecurityUserInfo;


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
    SecurityUserInfo userInfo();

}
