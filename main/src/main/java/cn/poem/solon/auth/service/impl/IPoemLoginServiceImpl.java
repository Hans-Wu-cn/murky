package cn.poem.solon.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.poem.core.exception.ServiceException;
import cn.poem.core.utils.ApiResult;
import cn.poem.solon.auth.domain.vo.LoginUserInfoVO;
import cn.poem.solon.auth.service.IPoemLoginService;
import cn.poem.solon.system.domain.dto.LoginDto;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.service.IPoemMenuService;
import cn.poem.solon.system.service.IPoemUserService;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ProxyComponent;

import java.util.*;

@ProxyComponent
public class IPoemLoginServiceImpl implements IPoemLoginService {

    @Inject
    IPoemUserService iPoemUserService;

    @Inject
    IPoemMenuService iPoemMenuService;

    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        PoemUser user = iPoemUserService.getOne(QueryWrapper.create().where(
                PoemUserTableDef.POEM_USER.ACCOUNT.eq(loginDto.getUsername())
        ).and(PoemUserTableDef.POEM_USER.PASSWORD.eq(loginDto.getPassword())));

        //如果为空抛出异常
        Optional.ofNullable(user).orElseThrow(() -> new ServiceException("账号或密码错误"));

//        Map<String, String> map = new HashMap<>();
//        map.put("token", "MSHXEYEYDQMXBJUVGPIEYDYSKYCOEMCG");
//        return ApiResult.ok(map);
        // 第1步，先登录上
        StpUtil.login(user.getUserId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return tokenInfo;
    }

    @Override
    public LoginUserInfoVO userInfo() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Long loginId = StpUtil.getLoginIdAsLong();
        PoemUser poemUser = iPoemUserService.getById(loginId);
        return new LoginUserInfoVO().setUserId(loginId)
                .setUserName(poemUser.getUserName())
                .setToken(tokenInfo.getTokenValue());
    }

}
