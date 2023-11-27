package cn.poem.solon.admin.saas.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasPermissionGroupPageDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasPermissionGroupTableDef;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasPermissionGroupVo;
import cn.poem.solon.admin.saas.service.IPoemSaasPermissionGroupService;
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
 * 商户权限组组Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("permissionGroup")
@Api("商户权限组管理")
public class PermissionGroupController extends BaseController<IPoemSaasPermissionGroupService> {
    PoemSaasPermissionGroupTableDef  POEM_SAAS_PERMISSION_GROUP = PoemSaasPermissionGroupTableDef. POEM_SAAS_PERMISSION_GROUP;

    @ApiOperation("商户权限组列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("permissionGroup")
    public ApiResult<Page<PoemSaasPermissionGroup>> page(PoemSaasPermissionGroupPageDTO poemSaasPermissionGroupPageDTO) {
        Page<PoemSaasPermissionGroup> result = baseService.page(poemSaasPermissionGroupPageDTO,
                QueryWrapper.create()
                        .and(POEM_SAAS_PERMISSION_GROUP.GROUP_NAME.like(poemSaasPermissionGroupPageDTO.getGroupName(), If::hasText))
                        .orderBy(POEM_SAAS_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("商户权限组列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("permissionGroup")
    public ApiResult<List<PoemSaasPermissionGroup>> list(PoemSaasPermissionGroupPageDTO poemSaasPermissionGroupPageDTO) {
        List<PoemSaasPermissionGroup> result = baseService.list(
                QueryWrapper.create()
                .and(POEM_SAAS_PERMISSION_GROUP.GROUP_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .and(POEM_SAAS_PERMISSION_GROUP.GROUP_NAME.like(poemSaasPermissionGroupPageDTO.getGroupName()))
                        .orderBy(POEM_SAAS_PERMISSION_GROUP.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("商户权限组详情")
    @Get
    @Mapping("{roleId}")
    @SaCheckPermission("permissionGroup")
    public ApiResult<PoemSaasPermissionGroupVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增商户权限组")
    @Post
    @Mapping
    @SaCheckPermission("permissionGroup:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO) {
        return toResult(baseService.save(poemSaasPermissionGroupFromDTO));
    }

    @ApiOperation("修改商户权限组")
    @Put
    @Mapping
    @SaCheckPermission("permissionGroup:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO) {
        boolean result = baseService.update(poemSaasPermissionGroupFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除商户权限组")
    @Delete
    @Mapping("/{groupId}")
    @SaCheckPermission("permissionGroup:remove")
    public ApiResult<?> remove(Long groupId) {
        boolean result = baseService.removeById(groupId);
        return toResult(result);
    }
}
