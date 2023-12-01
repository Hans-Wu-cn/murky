package cn.poem.solon.admin.tenant.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPermissionGroupPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.entity.table.PoemTenantPermissionGroupTableDef;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantPermissionGroupVo;
import cn.poem.solon.admin.tenant.service.IPoemTenantPermissionGroupService;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.utils.ApiResult;
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
 * 租户权限组组Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("permissionGroup")
@Api("租户权限组管理")
public class PermissionGroupController extends BaseController<IPoemTenantPermissionGroupService> {
    PoemTenantPermissionGroupTableDef POEM_TENANT_PERMISSION_GROUP = PoemTenantPermissionGroupTableDef.POEM_TENANT_PERMISSION_GROUP;

    @ApiOperation("租户权限组列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("permissionGroup")
    public ApiResult<Page<PoemTenantPermissionGroup>> page(PoemTenantPermissionGroupPageDTO poemTenantPermissionGroupPageDTO) {
        Page<PoemTenantPermissionGroup> result = baseService.page(poemTenantPermissionGroupPageDTO,
                QueryWrapper.create()
                        .and(POEM_TENANT_PERMISSION_GROUP.GROUP_NAME.like(poemTenantPermissionGroupPageDTO.getGroupName(), If::hasText))
                        .orderBy(POEM_TENANT_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("租户权限组列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("permissionGroup")
    public ApiResult<List<PoemTenantPermissionGroup>> list(PoemTenantPermissionGroupPageDTO poemTenantPermissionGroupPageDTO) {
        List<PoemTenantPermissionGroup> result = baseService.list(
                QueryWrapper.create()
                .and(POEM_TENANT_PERMISSION_GROUP.GROUP_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .and(POEM_TENANT_PERMISSION_GROUP.GROUP_NAME.like(poemTenantPermissionGroupPageDTO.getGroupName()))
                        .orderBy(POEM_TENANT_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("租户权限组详情")
    @Get
    @Mapping("{roleId}")
    @SaCheckPermission("permissionGroup")
    public ApiResult<PoemTenantPermissionGroupVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增租户权限组")
    @Post
    @Mapping
    @SaCheckPermission("permissionGroup:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO) {
        return toResult(baseService.save(poemTenantPermissionGroupFromDTO));
    }

    @ApiOperation("修改租户权限组")
    @Put
    @Mapping
    @SaCheckPermission("permissionGroup:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO) {
        boolean result = baseService.update(poemTenantPermissionGroupFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除租户权限组")
    @Delete
    @Mapping("/{groupId}")
    @SaCheckPermission("permissionGroup:remove")
    public ApiResult<?> remove(Long groupId) {
        boolean result = baseService.removeById(groupId);
        return toResult(result);
    }
}
