package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;

import cn.poem.solon.admin.core.validat.Info;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.dto.PoemI18nFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemI18nDTO;
import cn.poem.solon.admin.system.domain.vo.PoemI18nVo;
import cn.poem.solon.admin.system.service.IPoemI18nService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.Map;

/***
 * i18n Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemI18n")
@Api("i18n管理")
public class PoemI18nController extends BaseController<IPoemI18nService> {

    @ApiOperation("i18n分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("i18n")
    public ApiResult<Page<Map>> page(PoemI18nDTO poemI18nDTO) {
        return ApiResult.ok(baseService.page(poemI18nDTO));
    }

    @ApiOperation("i18n详情")
    @Get
    @Mapping("info")
    @SaCheckPermission("i18n")
    public ApiResult<PoemI18nVo> info(@Validated(Info.class) PoemI18nDTO poemI18nDTO) {
        return ApiResult.ok(baseService.info(poemI18nDTO));
    }

    @ApiOperation("新增i18n")
    @Post
    @Mapping
    @SaCheckPermission("i18n:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemI18nFromDTO poemI18nFromDTO) {
        return toResult(baseService.save(poemI18nFromDTO));
    }

    @ApiOperation("修改i18n")
    @Put
    @Mapping
    @SaCheckPermission("i18n:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class)PoemI18nFromDTO poemI18nFromDTO) {
        return toResult(baseService.edit(poemI18nFromDTO));
    }

    @ApiOperation("删除i18n")
    @Delete
    @Mapping("{i18nKey}")
    @SaCheckPermission("i18n:remove")
    public ApiResult<?> remove(String i18nKey) {
        return toResult(baseService.remove(i18nKey));
    }
}
