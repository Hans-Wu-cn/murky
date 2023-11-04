package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemUserPageDTO;
import cn.poem.solon.admin.system.domain.dto.ResetPasswordDto;
import cn.poem.solon.admin.system.domain.vo.PoemUserPageVo;
import cn.poem.solon.admin.system.domain.vo.PoemUserVo;
import cn.poem.solon.admin.system.service.IPoemUserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

@Controller
@Valid
@Mapping("poemUser")
@Api("用户管理")
public class PoemUserController extends BaseController<IPoemUserService> {
    @ApiOperation("用户列表查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("user")
    public ApiResult<Page<PoemUserPageVo>> page(PoemUserPageDTO poemUserPageDTO) {
        return ApiResult.ok(baseService.page(poemUserPageDTO));
    }

    @ApiOperation("用户详情")
    @Get
    @Mapping("{userId}")
    @SaCheckPermission("user")
    public ApiResult<PoemUserVo> info(Long userId) {
        return ApiResult.ok(baseService.info(userId));
    }

    @ApiOperation("新增用户")
    @Post
    @Mapping
    @SaCheckPermission("user:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemUserFromDTO poemUserFromDTO) {
        return toResult(baseService.save(poemUserFromDTO));
    }

    @ApiOperation("修改用户")
    @Put
    @Mapping
    @SaCheckPermission("user:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemUserFromDTO poemUserFromDTO) {
        return toResult(baseService.update(poemUserFromDTO));
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
