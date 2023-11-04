package cn.poem.solon.admin.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.auth.domain.dto.LoginDto;
import cn.poem.solon.admin.auth.service.IPoemLoginService;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.core.record.PasswordRecord;
import cn.poem.solon.admin.core.utils.EncryptionUtil;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.event.system.UserEvent;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.*;

@Component
public class IPoemLoginServiceImpl implements IPoemLoginService {

    @Inject
    private UserEvent userEvent;

    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        PoemUser user = userEvent.getOneByAccount(loginDto.getAccount());
        //如果为空抛出异常
        Optional.ofNullable(user).orElseThrow(() -> new ServiceException("账号或密码错误"));
        String encryPassword = EncryptionUtil.userEncryption(new PasswordRecord(user.getSalt(), loginDto.getPassword()));
        if(!user.getPassword().equals(encryPassword)){
            throw new ServiceException("账号或密码错误");
        }
        // 第1步，先登录
        StpUtil.login(user.getUserId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return tokenInfo;
    }

    /**
     * 获取用户登录信息
     *
     * @return 用户信息对象
     */
    @Override
    public SecurityUserInfo userInfo() {
        return userEvent.userInfo();
    }
}
