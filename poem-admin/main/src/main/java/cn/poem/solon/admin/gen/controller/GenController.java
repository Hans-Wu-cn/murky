package cn.poem.solon.admin.gen.controller;

import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.gen.domain.dto.GenTableDTO;
import cn.poem.solon.admin.gen.domain.vo.GenTableVo;
import cn.poem.solon.admin.gen.domain.dto.GenTablePageDTO;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.domain.entity.table.GenTableTableDef;
import cn.poem.solon.admin.gen.service.IGenTableColumnService;
import cn.poem.solon.admin.gen.service.IGenTableService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Valid
@Mapping("gen")
@Api("代码生成")
public class GenController extends BaseController<IGenTableService> {

    @Inject
    private IGenTableColumnService iGenTableColumnService;

    @ApiOperation("查询代码生成列表")
    @Get
    @Mapping("/list")
    public ApiResult<Page<GenTable>> genList(GenTablePageDTO genTablePageDTO)
    {
        GenTableTableDef GEN_TABLE = GenTableTableDef.GEN_TABLE;
        QueryWrapper queryWrapper = QueryWrapper.create().from(GEN_TABLE)
                .where(GEN_TABLE.TABLE_NAME.eq(genTablePageDTO.getTableName()))
                .and(GEN_TABLE.TABLE_COMMENT.eq(genTablePageDTO.getTableComment()))
                ;
        Page<GenTable> page = baseService.page(genTablePageDTO, queryWrapper);
        return ApiResult.ok(page);
    }

    /**
     * 修改代码生成业务
     */
    @Get
    @Mapping(value = "/{tableId}")
    public ApiResult<Map<String, Object>> info(Long tableId)
    {
        GenTableVo table = baseService.selectGenTableById(tableId);
        List<GenTableVo> tables = baseService.selectGenTableAll();
        List<GenTableColumn> list = iGenTableColumnService.getByTableId(tableId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return ApiResult.ok(map);
    }

    /**
     * 查询数据库列表
     */
    @Get
    @Mapping("/db/list")
    public ApiResult<Page<GenTable>> dataList(GenTablePageDTO genTablePageDTO)
    {
        Page<GenTable> genTablePage = baseService.selectDbTableList(genTablePageDTO);
        return ApiResult.ok(genTablePage);
    }

    /**
     * 查询数据表字段列表
     */
    @Get
    @Mapping(value = "/column/{tableId}")
    public ApiResult<List<GenTableColumn>> columnList(Long tableId)
    {

        List<GenTableColumn> list = iGenTableColumnService.getByTableId(tableId);
        return ApiResult.ok(list);
    }

    /**
     * 导入表结构（保存）
     */
    @Post
    @Mapping("/importTable")
    public ApiResult<?> importTableSave(String tables)
    {
        // 查询表信息
        List<GenTable> tableList = baseService.selectDbTableList(tables);
        baseService.importGenTable(tableList);
        return ApiResult.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @Put
    @Mapping
    public ApiResult<?> editSave(@Validated @Body GenTableDTO genTableDTO)
    {
        baseService.validateEdit(genTableDTO);
        baseService.updateGenTable(genTableDTO);
        return ApiResult.ok();
    }

    /**
     * 删除代码生成
     */
    @Delete
    @Mapping("/{tableIds}")
    public ApiResult<?> remove(Long[] tableIds)
    {
        baseService.deleteGenTableByIds(tableIds);
        return ApiResult.ok();
    }

}
