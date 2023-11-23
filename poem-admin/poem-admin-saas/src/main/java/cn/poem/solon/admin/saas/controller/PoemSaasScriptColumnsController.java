package cn.poem.solon.admin.saas.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasScriptTableConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTableFromDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasScriptTablePageDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptColumns;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasScriptTable;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasScriptColumnsTableDef;
import cn.poem.solon.admin.saas.service.IPoemSaasScriptColumnsService;
import cn.poem.solon.utils.ApiResult;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.NotNull;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

import static cn.poem.solon.admin.saas.domain.entity.table.PoemSaasScriptTableTableDef.POEM_SAAS_SCRIPT_TABLE;

/***
 * PoemSaasScriptColumns Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemSaasScriptColumns")
@Api("字段脚本管理")
public class PoemSaasScriptColumnsController extends BaseController<IPoemSaasScriptColumnsService> {
    @ApiOperation("字段脚本列表查询")
    @Get
    @Mapping("list}")
    @SaCheckPermission("saasScript")
    public ApiResult<List<PoemSaasScriptColumns>> list(@Validated @NotNull Long tableId) {
        PoemSaasScriptColumnsTableDef POEM_SAAS_SCRIPT_COLUMNS = PoemSaasScriptColumnsTableDef.POEM_SAAS_SCRIPT_COLUMNS;

        List<PoemSaasScriptColumns> list = baseService.list(QueryWrapper.create().where(POEM_SAAS_SCRIPT_COLUMNS.TABLE_ID.eq(tableId)));
        return ApiResult.ok(list);
    }

}
