package cn.poem.solon.system.controller;

import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.core.utils.ApiResult;
import cn.poem.solon.core.validat.Insert;
import cn.poem.solon.core.validat.Update;
import cn.poem.solon.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.system.domain.dto.PoemUserPageDTO;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.domain.vo.PoemUserVo;
import cn.poem.solon.system.service.IPoemUserService;
import cn.poem.solon.expand.SystemSecurityCache;
import cn.poem.solon.utils.SecurityUtils;
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
    public ApiResult<Page<PoemUser>> page(PoemUserPageDTO poemUserPageDTO) {
        Page<PoemUser> result = baseService.page(poemUserPageDTO,
                QueryWrapper.create()
                        .where(PoemUserTableDef.POEM_USER.CREATE_USER.eq(SecurityUtils.getUserId()))
                        .and(PoemUserTableDef.POEM_USER.USER_NAME.likeRight(poemUserPageDTO.getUserName(), If::notNull))
                        .and(PoemUserTableDef.POEM_USER.DEPT_ID.eq(poemUserPageDTO.getDeptId(),If::notNull))
                        .orderBy(PoemUserTableDef.POEM_USER.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
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

    @ApiOperation("删除用户")
    @Delete
    @Mapping("/{userId}")
    public ApiResult<?> remove(Long userId) {
        boolean result = baseService.removeById(userId);
        return toResult(result);
    }
}
