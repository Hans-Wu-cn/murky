package cn.poem.solon.admin.tenant.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.tenant.domain.dto.TenantFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.TenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.Tenant;
import cn.poem.solon.admin.tenant.domain.vo.TenantVo;
import cn.poem.solon.admin.tenant.service.ITenantService;
import cn.poem.solon.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.validat.Insert;
import cn.poem.solon.validat.Update;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

/**
 * 租户 Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("tenant")
@Api("租户管理")
public class TenantController extends BaseController<ITenantService> {
    @ApiOperation("租户列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("tenant")
    public ApiResult<Page<TenantVo>> page(TenantPageDTO tenantPageDTO) {
        return ApiResult.ok(baseService.page(tenantPageDTO));
    }

    @ApiOperation("租户详情")
    @Get
    @Mapping("{tenantId}")
    @SaCheckPermission("tenant")
    public ApiResult<TenantVo> info(Long tenantId) {
        return ApiResult.ok(baseService.info(tenantId));
    }

    @ApiOperation("新增租户")
    @Post
    @Mapping
    @SaCheckPermission("tenant:add")
    public ApiResult<Boolean> add(@Validated(Insert.class) @Body TenantFromDTO tenantFromDTO) {
        return ApiResult.ok(baseService.add(tenantFromDTO));
    }

    @ApiOperation("修改租户")
    @Put
    @Mapping
    @SaCheckPermission("tenant:edit")
    public ApiResult<Boolean> edit(@Validated(Update.class) @Body TenantFromDTO tenantFromDTO) {
        return ApiResult.ok(baseService.edit(tenantFromDTO));
    }

    @ApiOperation("停用/启用租户")
    @Post
    @Mapping("{tenantId}")
    @SaCheckPermission("tenant:edit")
    public ApiResult<Tenant> edit(Long tenantId) {
        Tenant poemTenant = baseService.getById(tenantId);
        CommonStatus status = poemTenant.getStatus().cut();
        poemTenant.setStatus(status);
        boolean b = baseService.updateById(poemTenant);
        if(b){
            return ApiResult.ok(poemTenant);
        }
        return ApiResult.fail();
    }
}
