package cn.poem.solon.admin.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.admin.system.contant.SystemContant;
import cn.poem.solon.admin.system.domain.dto.SysRoleFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysRolePageDTO;
import cn.poem.solon.admin.system.domain.entity.SysRole;
import cn.poem.solon.admin.system.domain.entity.table.SysRoleTableDef;
import cn.poem.solon.admin.system.domain.vo.SysRoleVo;
import cn.poem.solon.admin.system.service.ISysRoleService;
import cn.poem.solon.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.validat.Insert;
import cn.poem.solon.validat.Update;
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
@Mapping("role")
@Api("角色管理")
public class SysRoleController extends BaseController<ISysRoleService> {
    @ApiOperation("角色列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("role")
    public ApiResult<Page<SysRole>> page(SysRolePageDTO SysRolePageDTO) {
        SysRoleTableDef SYS_ROLE = SysRoleTableDef.SYS_ROLE;
        Page<SysRole> result = baseService.page(SysRolePageDTO,
                QueryWrapper.create()
                        .and(SYS_ROLE.ROLE_CODE.like(SysRolePageDTO.getRoleCode(), If::hasText))
                        .and(SYS_ROLE.ROLE_NAME.like(SysRolePageDTO.getRoleName(), If::hasText))
                        .and(SYS_ROLE.ROLE_CODE.ne(SystemContant.ADMIN_ROLE_CODE))
                        .and(SYS_ROLE.ROLE_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .orderBy(SYS_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("角色列表查询")
    @Get
    @Mapping("list")
    public ApiResult<List<SysRole>> list(SysRolePageDTO SysRolePageDTO) {
        SysRoleTableDef SYS_ROLE = SysRoleTableDef.SYS_ROLE;
        List<SysRole> result = baseService.list(
                QueryWrapper.create()
                .and(SYS_ROLE.ROLE_CODE.ne(SystemContant.ADMIN_ROLE_CODE))
                .and(SYS_ROLE.ROLE_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .orderBy(SYS_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("角色详情")
    @Get
    @Mapping("{roleId}")
    @SaCheckPermission("role")
    public ApiResult<SysRoleVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增角色")
    @Post
    @Mapping
    @SaCheckPermission("role:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SysRoleFromDTO sysRoleFromDTO) {
        return toResult(baseService.save(sysRoleFromDTO));
    }

    @ApiOperation("修改角色")
    @Put
    @Mapping
    @SaCheckPermission("role:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysRoleFromDTO sysRoleFromDTO) {
        boolean result = baseService.update(sysRoleFromDTO);
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
