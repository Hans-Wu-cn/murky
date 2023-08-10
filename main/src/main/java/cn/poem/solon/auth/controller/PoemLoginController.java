package cn.poem.solon.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.core.utils.ApiResult;
import cn.poem.solon.auth.service.IPoemLoginService;
import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.system.domain.dto.LoginDto;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.service.IPoemMenuService;
import cn.poem.solon.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.utils.SecurityUtil;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

import java.util.*;

/***
 * 登录Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("登录控制器")
@Mapping("auth")
public class PoemLoginController {

    @Inject
    IPoemLoginService iPoemLoginService;
    @Inject
    IPoemMenuService iPoemMenuService;
    @Post
    @Mapping("login")
    public ApiResult login(@Body LoginDto loginDto) {
        System.out.println(loginDto);
        SaTokenInfo tokenInfo = iPoemLoginService.login(loginDto);
        Map<String, String> map = new HashMap<>();
        map.put("token", tokenInfo.getTokenValue());
        return ApiResult.ok(map);
    }

    @Post
    @Mapping("logout")
    public ApiResult<?> logout() {
        StpUtil.logout();
        SecurityUtil.publishAsyncEvent();
        return ApiResult.ok();
    }

    @Get
    @Mapping("menu")
    public ApiResult<List<PoemMenuTreeVO>> menu() {
        return ApiResult.ok(iPoemMenuService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY)));
    }

    @Get
    @Mapping("info")
    public ApiResult<UserInfo> userInfo() {
        return ApiResult.ok(iPoemLoginService.userInfo());
    }
}

@Data
@Accessors(chain = true)
class Vo {
    private String lable;
    private String value;
}
