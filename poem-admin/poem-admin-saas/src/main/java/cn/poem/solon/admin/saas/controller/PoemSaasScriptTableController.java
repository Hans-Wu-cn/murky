package cn.poem.solon.admin.saas.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasScriptTableConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTableFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTablePageDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasScriptTableTableDef;
import cn.poem.solon.admin.saas.service.IPoemSaasScriptTableService;
import cn.poem.solon.utils.ApiResult;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

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
    @ApiOperation("表格脚本分页查询")
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

    @ApiOperation("表格脚本详情")
    @Get
    @Mapping("{tableId}")
    public ApiResult info(Long tableId){
        return ApiResult.ok(baseService.getById(tableId));
    }

    @ApiOperation("新增表格脚本")
    @Post
    @Mapping
    @SaCheckPermission("saasScript:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemSaasScriptTableFromDTO poemSaasScriptTable){
        PoemSaasScriptTable entity = PoemSaasScriptTableConvert.INSTANCES.toEntity(poemSaasScriptTable);
        return ApiResult.ok(baseService.save(entity));
    }

    @ApiOperation("修改表格脚本")
    @Put
    @Mapping
    @SaCheckPermission("saasScript:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemSaasScriptTableFromDTO poemSaasScriptTable){
        PoemSaasScriptTable entity = PoemSaasScriptTableConvert.INSTANCES.toEntity(poemSaasScriptTable);
        return ApiResult.ok(baseService.updateById(entity));
    }

    @ApiOperation("删除表格脚本")
    @Delete
    @Mapping("/{tableId}")
    @SaCheckPermission("saasScript:remove")
    public ApiResult<?> remove(Long tableId){
        return toResult(baseService.removeById(tableId));
    }
}
