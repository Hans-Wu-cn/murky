package cn.poem.solon.admin.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.contant.SystemContant;
import cn.poem.solon.admin.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemRolePageDTO;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.domain.entity.table.PoemRoleTableDef;
import cn.poem.solon.admin.system.domain.vo.PoemRoleVo;
import cn.poem.solon.admin.system.service.IPoemRoleService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

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
    @SaCheckPermission("role")
    public ApiResult<Page<PoemRole>> page(PoemRolePageDTO poemRolePageDTO) {
        PoemRoleTableDef POEM_ROLE = PoemRoleTableDef.POEM_ROLE;
        Page<PoemRole> result = baseService.page(poemRolePageDTO,
                QueryWrapper.create()
                        .and(POEM_ROLE.ROLE_CODE.like(poemRolePageDTO.getRoleCode(), If::hasText))
                        .and(POEM_ROLE.ROLE_NAME.like(poemRolePageDTO.getRoleName(), If::hasText))
                        .and(POEM_ROLE.ROLE_CODE.ne(SystemContant.ADMIN_ROLE_CODE))
                        .and(POEM_ROLE.ROLE_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .orderBy(POEM_ROLE.CREATE_TIME.asc())
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
    @SaCheckPermission("role")
    public ApiResult<PoemRoleVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增角色")
    @Post
    @Mapping
    @SaCheckPermission("role:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemRoleFromDTO poemRoleFromDTO) {
        return toResult(baseService.save(poemRoleFromDTO));
    }

    @ApiOperation("修改角色")
    @Put
    @Mapping
    @SaCheckPermission("role:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemRoleFromDTO poemRoleFromDTO) {
        boolean result = baseService.update(poemRoleFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除角色")
    @Delete
    @Mapping("/{roleId}")
    @SaCheckPermission("role:remove")
    public ApiResult<?> remove(Long roleId) {
        boolean result = baseService.removeById(roleId);
        return toResult(result);
    }
}
