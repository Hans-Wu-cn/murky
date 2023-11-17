package cn.poem.solon.admin.saas.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasRoleFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasRolePageDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasRoleTableDef;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasRoleVo;
import cn.poem.solon.admin.saas.service.IPoemSaasRoleService;
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
 * 商户角色Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemSaasRole")
@Api("商户角色管理")
public class PoemSaasRoleController extends BaseController<IPoemSaasRoleService> {
    PoemSaasRoleTableDef POEM_ROLE = PoemSaasRoleTableDef.POEM_SAAS_ROLE;

    @ApiOperation("商户角色列表分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("saasRole")
    public ApiResult<Page<PoemSaasRole>> page(PoemSaasRolePageDTO poemSaasRolePageDTO) {
        Page<PoemSaasRole> result = baseService.page(poemSaasRolePageDTO,
                QueryWrapper.create()
                        .and(POEM_ROLE.SAAS_ROLE_CODE.like(poemSaasRolePageDTO.getSaasRoleCode(), If::hasText))
                        .and(POEM_ROLE.SAAS_ROLE_NAME.like(poemSaasRolePageDTO.getSaasRoleName(), If::hasText))
                        .orderBy(POEM_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("商户角色列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("saasRole")
    public ApiResult<List<PoemSaasRole>> list(PoemSaasRolePageDTO poemRolePageDTO) {
        List<PoemSaasRole> result = baseService.list(
                QueryWrapper.create()
                .and(POEM_ROLE.SAAS_ROLE_ID.notIn(SecurityUtils.getUserInfo().getRoleIds()))
                        .orderBy(POEM_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("商户角色详情")
    @Get
    @Mapping("{roleId}")
    @SaCheckPermission("saasRole")
    public ApiResult<PoemSaasRoleVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增商户角色")
    @Post
    @Mapping
    @SaCheckPermission("saasRole:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemSaasRoleFromDTO poemSaasRoleFromDTO) {
        return toResult(baseService.save(poemSaasRoleFromDTO));
    }

    @ApiOperation("修改商户角色")
    @Put
    @Mapping
    @SaCheckPermission("saasRole:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemSaasRoleFromDTO poemSaasRoleFromDTO) {
        boolean result = baseService.update(poemSaasRoleFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除商户角色")
    @Delete
    @Mapping("/{roleId}")
    @SaCheckPermission("saasRole:remove")
    public ApiResult<?> remove(Long roleId) {
        boolean result = baseService.removeById(roleId);
        return toResult(result);
    }
}
