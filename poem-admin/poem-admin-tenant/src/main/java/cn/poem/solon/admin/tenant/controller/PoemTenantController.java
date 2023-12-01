package cn.poem.solon.admin.tenant.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenant;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantVo;
import cn.poem.solon.admin.tenant.service.IPoemTenantService;
import cn.poem.solon.utils.ApiResult;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

/**
 * 租户 Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemTenant")
@Api("租户管理")
public class PoemTenantController extends BaseController<IPoemTenantService> {
    @ApiOperation("租户列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("tenant")
    public ApiResult<Page<PoemTenantVo>> page(PoemTenantPageDTO poemTenantPageDTO) {
        return ApiResult.ok(baseService.page(poemTenantPageDTO));
    }

    @ApiOperation("租户详情")
    @Get
    @Mapping("{tenantId}")
    @SaCheckPermission("tenant")
    public ApiResult<PoemTenantVo> info(Long tenantId) {
        return ApiResult.ok(baseService.info(tenantId));
    }

    @ApiOperation("新增租户")
    @Post
    @Mapping
    @SaCheckPermission("tenant:add")
    public ApiResult<Boolean> add(@Body PoemTenantFromDTO poemTenantFromDTO) {
        return ApiResult.ok(baseService.add(poemTenantFromDTO));
    }

    @ApiOperation("停用/启用租户")
    @Post
    @Mapping("{tenantId}")
    @SaCheckPermission("tenant:edit")
    public ApiResult<?> edit(Long tenantId) {
        PoemTenant poemTenant = baseService.getById(tenantId);
        CommonStatus status = poemTenant.getStatus().cut();
        poemTenant.setStatus(status);
        return toResult(baseService.updateById(poemTenant));
    }
}
