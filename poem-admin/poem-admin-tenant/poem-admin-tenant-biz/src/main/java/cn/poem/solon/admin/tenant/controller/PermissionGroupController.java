package cn.poem.solon.admin.tenant.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.TenantPermissionGroupPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.entity.table.TenantPermissionGroupTableDef;
import cn.poem.solon.admin.tenant.domain.vo.TenantPermissionGroupVo;
import cn.poem.solon.admin.tenant.service.ITenantPermissionGroupService;
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
 * 租户权限组组Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("permissionGroup")
@Api("租户权限组管理")
public class PermissionGroupController extends BaseController<ITenantPermissionGroupService> {

    @ApiOperation("租户权限组列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("permissionGroup")
    public ApiResult<Page<TenantPermissionGroup>> page(TenantPermissionGroupPageDTO tenantPermissionGroupPageDTO) {
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        Page<TenantPermissionGroup> result = baseService.page(tenantPermissionGroupPageDTO,
                QueryWrapper.create()
                        .and(TENANT_PERMISSION_GROUP.GROUP_NAME.like(tenantPermissionGroupPageDTO.getGroupName(), If::hasText))
                        .orderBy(TENANT_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("租户权限组列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("permissionGroup")
    public ApiResult<List<TenantPermissionGroup>> list(TenantPermissionGroupPageDTO tenantPermissionGroupPageDTO) {
        TenantPermissionGroupTableDef TENANT_PERMISSION_GROUP = TenantPermissionGroupTableDef.TENANT_PERMISSION_GROUP;
        List<TenantPermissionGroup> result = baseService.list(
                QueryWrapper.create()
                .and(TENANT_PERMISSION_GROUP.GROUP_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .and(TENANT_PERMISSION_GROUP.GROUP_NAME.like(tenantPermissionGroupPageDTO.getGroupName()))
                        .orderBy(TENANT_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("租户权限组详情")
    @Get
    @Mapping("{roleId}")
    @SaCheckPermission("permissionGroup")
    public ApiResult<TenantPermissionGroupVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增租户权限组")
    @Post
    @Mapping
    @SaCheckPermission("permissionGroup:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO) {
        return toResult(baseService.save(tenantPermissionGroupFromDTO));
    }

    @ApiOperation("修改租户权限组")
    @Put
    @Mapping
    @SaCheckPermission("permissionGroup:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO) {
        boolean result = baseService.update(tenantPermissionGroupFromDTO);
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
