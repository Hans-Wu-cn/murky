package cn.murky.admin.auth.controller;

import cn.murky.admin.auth.domain.dto.EditPasswordDTO;
import cn.murky.admin.system.api.SysUserApi;
import cn.murky.admin.system.api.domian.UserProfile;
import cn.murky.admin.system.api.domian.dto.ProfileFromDTO;
import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import cn.murky.admin.core.utils.SecurityUtils;
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
public class MurkyProfileController extends BaseController {
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

    @Put
    @ApiOperation("修改用户信息")
    @Mapping
    public ApiResult<?> editProfile(@Body ProfileFromDTO profileFromDTO) {
        return toResult(sysUserApi.setProfile(profileFromDTO));
    }

    @Put
    @ApiOperation("修改密码")
    @Mapping
    public ApiResult<?> editProfile(@Body EditPasswordDTO editPasswordDTO) {
        return toResult(sysUserApi.setPassword(editPasswordDTO.getOldPassword(),editPasswordDTO.getPassword(),editPasswordDTO.getSurePassword()));
    }

}
