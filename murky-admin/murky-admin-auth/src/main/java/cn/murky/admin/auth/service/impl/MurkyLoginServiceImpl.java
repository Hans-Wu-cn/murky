package cn.murky.admin.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.admin.auth.domain.dto.LoginDto;
import cn.murky.admin.auth.service.IMurkyLoginService;
import cn.murky.admin.common.entity.SecurityUserInfo;
import cn.murky.admin.flex.domin.SysUser;
import cn.murky.admin.system.api.SysUserApi;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.*;

@Component
public class MurkyLoginServiceImpl implements IMurkyLoginService {

    @Inject
    private SysUserApi sysUserApi;

    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        SysUser user = sysUserApi.getOneByAccount(loginDto.getAccount());
        //如果为空抛出异常
        Optional.ofNullable(user).orElseThrow(() -> new ServiceException("账号或密码错误"));
        String encryPassword = EncryptionUtil.userEncryption(new PasswordRecord(user.getSalt(), loginDto.getPassword()));
        if(!user.getPassword().equals(encryPassword)){
            throw new ServiceException("账号或密码错误");
        }
        // 第1步，先登录
        StpUtil.login(user.getId());
        // 第2步，获取 Token  相关参数
        return StpUtil.getTokenInfo();
    }

    /**
     * 获取用户登录信息
     *
     * @return 用户信息对象
     */
    @Override
    public SecurityUserInfo userInfo() {
        return sysUserApi.userInfo();
    }
}
