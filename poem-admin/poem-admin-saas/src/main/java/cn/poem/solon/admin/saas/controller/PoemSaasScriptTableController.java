package cn.poem.solon.admin.saas.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasMenuConvert;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasScriptTableConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasMenuFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasRolePageDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTableFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTablePageDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasScriptTableTableDef;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasMenuTreeVo;
import cn.poem.solon.admin.saas.service.IPoemSaasScriptTableService;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.utils.ApiResult;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.snack.ONode;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

/***
 * poemSaasScriptTable Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemSaasScriptTable")
@Api("表格脚本管理")
public class PoemSaasScriptTableController extends BaseController<IPoemSaasScriptTableService> {
    PoemSaasScriptTableTableDef POEM_SAAS_SCRIPT_TABLE = PoemSaasScriptTableTableDef.POEM_SAAS_SCRIPT_TABLE;
    @ApiOperation("商户菜单列表查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("saasScript")
    public ApiResult<Page<PoemSaasScriptTable>> page(PoemSaasScriptTablePageDTO poemSaasScriptTablePageDTO) {
        Page<PoemSaasScriptTable> page = baseService.page(poemSaasScriptTablePageDTO, QueryWrapper.create()
                .where(POEM_SAAS_SCRIPT_TABLE.STATUS.eq(poemSaasScriptTablePageDTO.getStatus()))
                .and(POEM_SAAS_SCRIPT_TABLE.TAG.eq(poemSaasScriptTablePageDTO.getTag()))
        );
        return ApiResult.ok(page);
    }

    @ApiOperation("商户菜单详情")
    @Get
    @Mapping("{tableId}")
    public ApiResult info(Long tableId){
        return ApiResult.ok(baseService.getById(tableId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    @SaCheckPermission("saasScript:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemSaasScriptTableFromDTO poemSaasScriptTable){
        PoemSaasScriptTable entity = PoemSaasScriptTableConvert.INSTANCES.toEntity(poemSaasScriptTable);
        return ApiResult.ok(baseService.save(entity));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    @SaCheckPermission("saasScript:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemSaasScriptTableFromDTO poemSaasScriptTable){
        PoemSaasScriptTable entity = PoemSaasScriptTableConvert.INSTANCES.toEntity(poemSaasScriptTable);
        return ApiResult.ok(baseService.updateById(entity));
    }

    @ApiOperation("删除商户菜单")
    @Delete
    @Mapping("/{tableId}")
    @SaCheckPermission("saasScript:remove")
    public ApiResult<?> remove(Long saasMenuId){
        return toResult(baseService.removeById(saasMenuId));
    }
}
