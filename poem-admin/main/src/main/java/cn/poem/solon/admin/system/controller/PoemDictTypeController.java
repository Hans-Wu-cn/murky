package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.convert.PoemDictConvert;
import cn.poem.solon.admin.system.domain.dto.PoemDictTypeFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemDictTypePageDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.domain.entity.table.PoemDictTypeTableDef;
import cn.poem.solon.admin.system.service.IPoemDictTypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

@Controller
@Valid
@Mapping("poemDictType")
@Api("字典类型管理")
public class PoemDictTypeController extends BaseController<IPoemDictTypeService> {

    @ApiOperation("字典类型分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("dict")
    public ApiResult<Page<PoemDictType>> page(PoemDictTypePageDTO poemDictTypePageDTO) {
        PoemDictTypeTableDef POEM_DICT_TYPE = PoemDictTypeTableDef.POEM_DICT_TYPE;
        Page<PoemDictType> result = baseService.page(poemDictTypePageDTO,
                QueryWrapper.create()
                        .and(POEM_DICT_TYPE.DICT_NAME.like(poemDictTypePageDTO.getDictName(), If::hasText))
                        .and(POEM_DICT_TYPE.DICT_TYPE.like(poemDictTypePageDTO.getDictType(), If::hasText))
                        .and(POEM_DICT_TYPE.STATUS.eq(poemDictTypePageDTO.getStatus(), If::notNull))
                        .orderBy(POEM_DICT_TYPE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("字典类型详情信息")
    @Get
    @Mapping("{dictTypeId}")
    @SaCheckPermission("dict")
    public ApiResult<?> add(Long dictTypeId) {
        return ApiResult.ok(baseService.getById(dictTypeId));
    }

    @ApiOperation("新增字典类型")
    @Post
    @Mapping
    @SaCheckPermission("dict:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemDictTypeFromDTO poemDictTypeFromDTO) {
        PoemDictType entity = PoemDictConvert.INSTANCES.toEntity(poemDictTypeFromDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改字典类型")
    @Put
    @Mapping
    @SaCheckPermission("dict:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemDictTypeFromDTO poemDictTypeFromDTO) {
        PoemDictType entity = PoemDictConvert.INSTANCES.toEntity(poemDictTypeFromDTO);
        return toResult(baseService.updateById(entity));
    }

    @ApiOperation("删除字典类型")
    @Delete
    @Mapping("{dictTypeId}")
    @SaCheckPermission("dict:remove")
    public ApiResult<?> remove(Long dictTypeId) {
        return toResult(baseService.removeById(dictTypeId));
    }

}
