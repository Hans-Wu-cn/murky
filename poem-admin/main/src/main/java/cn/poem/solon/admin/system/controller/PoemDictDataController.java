package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.convert.PoemDictConvert;
import cn.poem.solon.admin.system.domain.dto.PoemDictDataFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemDictDataPageDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.domain.entity.table.PoemDictDataTableDef;
import cn.poem.solon.admin.system.service.IPoemDictDataService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

/**
 * 字典数据Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemDictData")
@Api("字典数据管理")
public class PoemDictDataController extends BaseController<IPoemDictDataService> {
    @ApiOperation("字典数据分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("dict")
    public ApiResult<Page<PoemDictData>> page(PoemDictDataPageDTO poemDictDataPageDTO) {
        PoemDictDataTableDef POEM_DATA_DATA = PoemDictDataTableDef.POEM_DICT_DATA;
        Page<PoemDictData> result = baseService.page(poemDictDataPageDTO,
                QueryWrapper.create()
                        .and(POEM_DATA_DATA.DICT_TYPE.eq(poemDictDataPageDTO.getDictType()))
                        .and(POEM_DATA_DATA.DICT_LABEL.like(poemDictDataPageDTO.getDictLabel(), If::hasText))
                        .and(POEM_DATA_DATA.STATUS.eq(poemDictDataPageDTO.getStatus(), If::notNull))
                        .orderBy(POEM_DATA_DATA.DICT_SORT.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("获取指定字典数据")
    @Get
    @Mapping("dict/{dictType}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型")
    })
    public ApiResult<List<PoemDictData>> list(String dictType) {
        return ApiResult.ok(baseService.getDict(dictType));
    }

    @ApiOperation("字典数据详情信息")
    @Get
    @Mapping("{dictCode}")
    @SaCheckPermission("dict")
    public ApiResult<?> add(Long dictCode) {
        return ApiResult.ok(baseService.getById(dictCode));
    }

    @ApiOperation("新增字典类型")
    @Post
    @Mapping
    @SaCheckPermission("dict:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemDictDataFromDTO poemDictDataFromDTO) {
        PoemDictData entity = PoemDictConvert.INSTANCES.toEntity(poemDictDataFromDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改字典类型")
    @Put
    @Mapping
    @SaCheckPermission("dict:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemDictDataFromDTO poemDictDataFromDTO) {
        PoemDictData entity = PoemDictConvert.INSTANCES.toEntity(poemDictDataFromDTO);
        return toResult(baseService.updateById(entity));
    }
    @ApiOperation("删除字典类型")
    @Delete
    @Mapping("{dictCode}")
    @SaCheckPermission("dict:remove")
    public ApiResult<?> remove(Long dictCode) {
        return toResult(baseService.removeById(dictCode));
    }

}
