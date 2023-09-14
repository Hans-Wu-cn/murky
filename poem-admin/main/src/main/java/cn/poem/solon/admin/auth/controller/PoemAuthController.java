package cn.poem.solon.admin.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.entity.SecurityUserInfo;
import cn.poem.solon.admin.system.domain.dto.LoginDto;
import cn.poem.solon.admin.system.enums.MenuType;
import cn.poem.solon.admin.system.service.IPoemMenuService;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.auth.service.IPoemLoginService;
import cn.poem.solon.admin.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("安全控制器")
@Mapping("auth")
public class PoemAuthController {

    @Inject
    IPoemLoginService iPoemLoginService;
    @Inject
    IPoemMenuService iPoemMenuService;
    @Post
    @ApiOperation("登录")
    @Mapping("login")
    public ApiResult<String> login(@Body LoginDto loginDto) {
        SaTokenInfo tokenInfo = iPoemLoginService.login(loginDto);
        return ApiResult.ok(tokenInfo.getTokenValue());
    }

    @Post
    @ApiOperation("登出")
    @Mapping("logout")
    public ApiResult<?> logout() {
        StpUtil.logout();
        SecurityUtils.delUserInfo();
        return ApiResult.ok();
    }

    @Get
    @ApiOperation("获取菜单")
    @Mapping("menu")
    public ApiResult<List<PoemMenuTreeVO>> menu() {
        return ApiResult.ok(iPoemMenuService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY)));
    }

    @Get
    @ApiOperation("获取用户信息")
    @Mapping("info")
    public ApiResult<SecurityUserInfo> userInfo() {
        return ApiResult.ok(iPoemLoginService.userInfo());
    }
}
