package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.system.domain.convert.SysDictConvert;
import cn.poem.solon.admin.system.domain.dto.SysDictDataFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysDictDataPageDTO;
import cn.poem.solon.admin.system.domain.entity.SysDictData;
import cn.poem.solon.admin.system.domain.entity.table.SysDictDataTableDef;
import cn.poem.solon.admin.system.service.ISysDictDataService;
import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.core.validat.Insert;
import cn.poem.solon.core.validat.Update;
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
@Mapping("dictData")
@Api("字典数据管理")
public class SysDictDataController extends BaseController<ISysDictDataService> {
    @ApiOperation("字典数据分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("dict")
    public ApiResult<Page<SysDictData>> page(SysDictDataPageDTO sysDictDataPageDTO) {
        SysDictDataTableDef SYS_DICT_DATA = SysDictDataTableDef.SYS_DICT_DATA;
        Page<SysDictData> result = baseService.page(sysDictDataPageDTO,
                QueryWrapper.create()
                        .and(SYS_DICT_DATA.DICT_TYPE.eq(sysDictDataPageDTO.getDictType()))
                        .and(SYS_DICT_DATA.DICT_LABEL.like(sysDictDataPageDTO.getDictLabel(), If::hasText))
                        .and(SYS_DICT_DATA.STATUS.eq(sysDictDataPageDTO.getStatus(), If::notNull))
                        .orderBy(SYS_DICT_DATA.DICT_SORT.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("获取指定字典数据")
    @Get
    @Mapping("dict/{dictType}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型")
    })
    public ApiResult<List<SysDictData>> list(String dictType) {
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
    public ApiResult<?> add(@Body @Validated(Insert.class) SysDictDataFromDTO sysDictDataFromDTO) {
        SysDictData entity = SysDictConvert.INSTANCES.toEntity(sysDictDataFromDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改字典类型")
    @Put
    @Mapping
    @SaCheckPermission("dict:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysDictDataFromDTO sysDictDataFromDTO) {
        SysDictData entity = SysDictConvert.INSTANCES.toEntity(sysDictDataFromDTO);
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
