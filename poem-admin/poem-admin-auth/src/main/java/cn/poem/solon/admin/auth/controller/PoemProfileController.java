package cn.poem.solon.admin.auth.controller;

import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.admin.system.api.SysUserApi;
import cn.poem.solon.admin.system.api.domian.UserProfile;
import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

/***
 * 个人信息Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("个人信息控制器")
@Mapping("profile")
public class PoemProfileController extends BaseController {
    @Inject
    private SysUserApi sysUserApi;
    @Post
    @ApiOperation("修改语言偏好")
    @Mapping("language/{language}")
    public ApiResult<?> setLanguage(String language) {
        return toResult(sysUserApi.setLanguage(language));
    }

    @Get
    @ApiOperation("获取用户信息")
    @Mapping
    public ApiResult<UserProfile> info() {
        return ApiResult.ok(sysUserApi.getProfile(SecurityUtils.getUserId()));
    }

}
