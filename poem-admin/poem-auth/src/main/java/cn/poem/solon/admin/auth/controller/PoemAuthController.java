package cn.poem.solon.admin.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.auth.domain.dto.LoginDto;
import cn.poem.solon.admin.auth.service.IPoemLoginService;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.event.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.admin.event.system.MenuEvent;
import cn.poem.solon.admin.event.system.enums.MenuType;
import cn.poem.solon.admin.utils.SecurityUtils;
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
public class PoemAuthController {

    @Inject
    private IPoemLoginService iPoemLoginService;
    @Inject
    private MenuEvent menuEvent;
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
        SecurityUtils.delUserInfo();
        StpUtil.logout();
        return ApiResult.ok();
    }

    @Get
    @ApiOperation("获取菜单")
    @Mapping("menu")
    public ApiResult<List<PoemMenuTreeVO>> menu() {
        return ApiResult.ok(menuEvent.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY)));
    }

    @Get
    @ApiOperation("获取用户信息")
    @Mapping("info")
    public ApiResult<SecurityUserInfo> userInfo() {
        return ApiResult.ok(iPoemLoginService.userInfo());
    }
}
