package cn.murky.admin.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.admin.auth.domain.dto.LoginDto;
import cn.murky.admin.auth.service.IMurkyLoginService;
import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.admin.system.api.SysMenuApi;
import cn.murky.admin.system.api.domian.SysMenuTree;
import cn.murky.admin.system.api.enums.MenuType;
import cn.murky.core.web.ApiResult;
import cn.murky.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

import java.util.Arrays;
import java.util.List;

/***
 * 登录Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("安全控制器")
@Mapping("auth")
public class MurkyAuthController {

    @Inject
    private IMurkyLoginService iMurkyLoginService;
    @Inject
    private SysMenuApi sysMenuApi;

    @Post
    @ApiOperation("登录")
    @Mapping("login")
    public ApiResult<String> login(@Body LoginDto loginDto) {
        SaTokenInfo tokenInfo = iMurkyLoginService.login(loginDto);
        return ApiResult.ok(tokenInfo.getTokenValue());
    }

    @Post
    @ApiOperation("登出")
    @Mapping("logout")
    public ApiResult<?> logout() {
//        SecurityUtils.delUserMenu();
        SecurityUtils.delUserInfo();
        StpUtil.logout();
        return ApiResult.ok();
    }

    @Get
    @ApiOperation("获取菜单")
    @Mapping("menu")
    public ApiResult<List<SysMenuTree>> menu() {
        return ApiResult.ok(sysMenuApi.treeSysMenu(Arrays.asList(MenuType.MENU, MenuType.DIRECTORY)));
    }

    @Get
    @ApiOperation("获取用户信息")
    @Mapping("info")
    public ApiResult<SecurityUserInfo> userInfo() {
        return ApiResult.ok(iMurkyLoginService.userInfo());
    }
}
