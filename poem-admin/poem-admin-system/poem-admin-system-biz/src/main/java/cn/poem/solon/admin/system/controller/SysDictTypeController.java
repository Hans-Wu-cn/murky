package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.poem.solon.admin.system.domain.convert.SysDictConvert;
import cn.poem.solon.admin.system.domain.dto.SysDictTypeFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysDictTypePageDTO;
import cn.poem.solon.admin.system.domain.entity.SysDictType;
import cn.poem.solon.admin.system.domain.entity.table.SysDictTypeTableDef;
import cn.poem.solon.admin.system.service.ISysDictTypeService;
import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.core.validat.Insert;
import cn.poem.solon.core.validat.Update;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

/**
 * 字典类型Controller
 * @author hans
 */
@Controller
@Valid
@Mapping("dictType")
@Api("字典类型管理")
public class SysDictTypeController extends BaseController<ISysDictTypeService> {

    @ApiOperation("字典类型分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("dict")
    public ApiResult<Page<SysDictType>> page(SysDictTypePageDTO sysDictTypePageDTO) {
        SysDictTypeTableDef SYS_DICT_TYPE = SysDictTypeTableDef.SYS_DICT_TYPE;
        Page<SysDictType> result = baseService.page(sysDictTypePageDTO,
                QueryWrapper.create()
                        .and(SYS_DICT_TYPE.DICT_NAME.like(sysDictTypePageDTO.getDictName(), If::hasText))
                        .and(SYS_DICT_TYPE.DICT_TYPE.like(sysDictTypePageDTO.getDictType(), If::hasText))
                        .and(SYS_DICT_TYPE.STATUS.eq(sysDictTypePageDTO.getStatus(), If::notNull))
                        .orderBy(SYS_DICT_TYPE.CREATE_TIME.asc())
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
    public ApiResult<?> add(@Body @Validated(Insert.class) SysDictTypeFromDTO sysDictTypeFromDTO) {
        SysDictType entity = SysDictConvert.INSTANCES.toEntity(sysDictTypeFromDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改字典类型")
    @Put
    @Mapping
    @SaCheckPermission("dict:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysDictTypeFromDTO poemDictTypeFromDTO) {
        SysDictType entity = SysDictConvert.INSTANCES.toEntity(poemDictTypeFromDTO);
        return toResult(baseService.edit(entity));
    }

    @ApiOperation("删除字典类型")
    @Delete
    @Mapping("{dictTypeId}")
    @SaCheckPermission("dict:remove")
    public ApiResult<?> remove(Long dictTypeId) {
        return toResult(baseService.removeById(dictTypeId));
    }

    @ApiOperation("刷新字典")
    @Post
    @Mapping("refresh")
    @SaCheckPermission(mode = SaMode.OR,value=
            {"dict:add","dict:edit","dict:remove"})
    public ApiResult<?> refresh() {
        baseService.refreshDict();
        return ApiResult.ok();
    }

}
