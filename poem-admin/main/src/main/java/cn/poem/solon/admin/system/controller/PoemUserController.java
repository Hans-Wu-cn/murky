package cn.poem.solon.admin.system.controller;

import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.domin.table.PoemUserTableDef;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemUserPageDTO;
import cn.poem.solon.admin.system.domain.dto.ResetPasswordDto;
import cn.poem.solon.admin.system.domain.vo.PoemUserPageVo;
import cn.poem.solon.admin.system.domain.vo.PoemUserVo;
import cn.poem.solon.admin.system.service.IPoemUserService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
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
    public ApiResult<Page<PoemUserPageVo>> page(PoemUserPageDTO poemUserPageDTO) {
        return ApiResult.ok(baseService.page(poemUserPageDTO));
    }

    @ApiOperation("用户详情")
    @Get
    @Mapping("{userId}")
    public ApiResult<PoemUserVo> info(Long userId) {
        return ApiResult.ok(baseService.info(userId));
    }

    @ApiOperation("新增用户")
    @Post
    @Mapping
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemUserFromDTO poemUserFromDTO) {
        return toResult(baseService.save(poemUserFromDTO));
    }

    @ApiOperation("修改用户")
    @Put
    @Mapping
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemUserFromDTO poemUserFromDTO) {
        return toResult(baseService.update(poemUserFromDTO));
    }

    @ApiOperation("重置密码")
    @Put
    @Mapping
    public ApiResult<?> resetPassword(@Body @Validated ResetPasswordDto resetPasswordDto) {
        return toResult(baseService.resetPassword(resetPasswordDto.getUserId(),resetPasswordDto.getPassword()));
    }

    @ApiOperation("删除用户")
    @Delete
    @Mapping("/{userId}")
    public ApiResult<?> remove(Long userId) {
        boolean result = baseService.removeById(userId);
        return toResult(result);
    }
}
