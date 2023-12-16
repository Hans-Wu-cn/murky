package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.system.domain.dto.SysI18nDTO;
import cn.poem.solon.admin.system.domain.dto.SysI18nFromDTO;
import cn.poem.solon.admin.system.domain.vo.SysI18nVo;
import cn.poem.solon.admin.system.service.ISys18nService;
import cn.poem.solon.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.validat.Info;
import cn.poem.solon.validat.Insert;
import cn.poem.solon.validat.Update;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.NotBlank;
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
@Mapping("i18n")
@Api("i18n管理")
public class SysI18nController extends BaseController<ISys18nService> {

    @ApiOperation("i18n分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("i18n")
    public ApiResult<Page<Map>> page(SysI18nDTO sysI18nDTO) {
        return ApiResult.ok(baseService.page(sysI18nDTO));
    }

    @ApiOperation("i18n详情")
    @Get
    @Mapping("info")
    @SaCheckPermission("i18n")
    public ApiResult<SysI18nVo> info(@Validated(Info.class) SysI18nDTO sysI18nDTO) {
        return ApiResult.ok(baseService.info(sysI18nDTO));
    }

    @ApiOperation("i18n语言包")
    @Get
    @Mapping("language")
    @Valid
    public ApiResult<Map<String, String>> language(@NotBlank String i18nTag,@NotBlank String laguage) {
        return ApiResult.ok(baseService.language(i18nTag, laguage));
    }

    @ApiOperation("新增i18n")
    @Post
    @Mapping
    @SaCheckPermission("i18n:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SysI18nFromDTO sysI18nFromDTO) {
        return toResult(baseService.save(sysI18nFromDTO));
    }

    @ApiOperation("修改i18n")
    @Put
    @Mapping
    @SaCheckPermission("i18n:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysI18nFromDTO sysI18nFromDTO) {
        return toResult(baseService.edit(sysI18nFromDTO));
    }

    @ApiOperation("删除i18n")
    @Delete
    @Mapping("{i18nKey}")
    @SaCheckPermission("i18n:remove")
    public ApiResult<?> remove(String i18nKey) {
        return toResult(baseService.remove(i18nKey));
    }
}
