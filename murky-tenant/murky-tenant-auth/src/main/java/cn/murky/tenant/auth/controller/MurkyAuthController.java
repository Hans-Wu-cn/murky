package cn.murky.tenant.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.core.web.ApiResult;
import cn.murky.tenant.auth.domain.dto.LoginDto;
import cn.murky.tenant.core.utils.SecurityUtils;
import cn.murky.tenant.system.api.domain.vo.TenantMenuTreeVO;
import cn.murky.tenant.system.api.enums.MenuType;
import cn.murky.tenant.auth.service.IMurkyLoginService;
import cn.murky.tenant.auth.service.ITenantMenuService;
import cn.murky.tenant.core.SecurityTenantUserInfo;
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
    private ITenantMenuService iTenantMenuService;

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
//
    @Get
    @ApiOperation("获取菜单")
    @Mapping("menu")
    public ApiResult<List<TenantMenuTreeVO>> menu() {
        return ApiResult.ok(iTenantMenuService.treeSysMenu(Arrays.asList(MenuType.MENU, MenuType.DIRECTORY)));
    }

    @Get
    @ApiOperation("获取用户信息")
    @Mapping("info")
    public ApiResult<SecurityTenantUserInfo> userInfo() {
        return ApiResult.ok(iMurkyLoginService.userInfo());
    }
}
