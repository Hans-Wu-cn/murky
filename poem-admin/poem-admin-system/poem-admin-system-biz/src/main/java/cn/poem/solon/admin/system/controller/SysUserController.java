package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.system.domain.dto.SysUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysUserPageDTO;
import cn.poem.solon.admin.system.domain.dto.ResetPasswordDto;
import cn.poem.solon.admin.system.domain.vo.SysUserPageVo;
import cn.poem.solon.admin.system.domain.vo.SysUserVo;
import cn.poem.solon.admin.system.service.ISysUserService;
import cn.poem.solon.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.validat.Insert;
import cn.poem.solon.validat.Update;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

@Controller
@Valid
@Mapping("user")
@Api("用户管理")
public class SysUserController extends BaseController<ISysUserService> {
    @ApiOperation("用户列表查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("user")
    public ApiResult<Page<SysUserPageVo>> page(SysUserPageDTO sysUserPageDTO) {
        return ApiResult.ok(baseService.page(sysUserPageDTO));
    }

    @ApiOperation("用户详情")
    @Get
    @Mapping("{userId}")
    @SaCheckPermission("user")
    public ApiResult<SysUserVo> info(Long userId) {
        return ApiResult.ok(baseService.info(userId));
    }

    @ApiOperation("新增用户")
    @Post
    @Mapping
    @SaCheckPermission("user:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SysUserFromDTO sysUserFromDTO) {
        return toResult(baseService.save(sysUserFromDTO));
    }

    @ApiOperation("修改用户")
    @Put
    @Mapping
    @SaCheckPermission("user:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysUserFromDTO sysUserFromDTO) {
        return toResult(baseService.update(sysUserFromDTO));
    }

    @ApiOperation("重置密码")
    @Put
    @Mapping("restPassword")
    public ApiResult<?> resetPassword(@Body @Validated ResetPasswordDto resetPasswordDto) {
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            return ApiResult.fail("确认密码不一致");
        }
        return toResult(baseService.resetPassword(resetPasswordDto.getUserId(),resetPasswordDto.getPassword()));
    }

    @ApiOperation("删除用户")
    @Delete
    @Mapping("/{userId}")
    @SaCheckPermission("user:remove")
    public ApiResult<?> remove(Long userId) {
        boolean result = baseService.removeById(userId);
        return toResult(result);
    }
}
