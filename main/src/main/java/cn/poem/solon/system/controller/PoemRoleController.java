package cn.poem.solon.system.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.core.utils.ApiResult;
import cn.poem.solon.core.validat.Insert;
import cn.poem.solon.core.validat.Update;
import cn.poem.solon.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.table.PoemRoleTableDef;
import cn.poem.solon.system.service.IPoemRoleService;
import cn.poem.solon.system.domain.dto.PoemRolePageDTO;
import cn.poem.solon.system.domain.vo.PoemRoleVo;
import cn.poem.solon.utils.SecurityUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.noCondition;

/***
 * 角色Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemRole")
@Api("角色管理")
public class PoemRoleController extends BaseController<IPoemRoleService> {
    @ApiOperation("角色列表分页查询")
    @Get
    @Mapping("page")
    public ApiResult<Page<PoemRole>> page(PoemRolePageDTO poemRolePageDTO) {
        Page<PoemRole> result = baseService.page(poemRolePageDTO,
                QueryWrapper.create()
                        .where(PoemRoleTableDef.POEM_ROLE.CREATE_USER.eq(SecurityUtil.getUserId()))
                        .and(PoemRoleTableDef.POEM_ROLE.ROLE_CODE.likeRight(poemRolePageDTO.getRoleCode(), If::hasText))
                        .and(PoemRoleTableDef.POEM_ROLE.ROLE_CODE.likeRight(poemRolePageDTO.getRoleName(), If::hasText))
                        .orderBy(PoemRoleTableDef.POEM_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("角色列表查询")
    @Get
    @Mapping("list")
    public ApiResult<List<PoemRole>> list(PoemRolePageDTO poemRolePageDTO) {
        List<PoemRole> result = baseService.list(
                QueryWrapper.create().
                        orderBy(PoemRoleTableDef.POEM_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("角色详情")
    @Get
    @Mapping("{roleId}")
    public ApiResult<PoemRoleVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增角色")
    @Post
    @Mapping
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemRoleFromDTO poemRoleFromDTO) {
        return toResult(baseService.save(poemRoleFromDTO));
    }

    @ApiOperation("修改角色")
    @Put
    @Mapping
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemRoleFromDTO poemRoleFromDTO) {
        boolean result = baseService.update(poemRoleFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除角色")
    @Delete
    @Mapping("/{roleId}")
    public ApiResult<?> remove(Long roleId) {
        boolean result = baseService.removeById(roleId);
        return toResult(result);
    }
}
