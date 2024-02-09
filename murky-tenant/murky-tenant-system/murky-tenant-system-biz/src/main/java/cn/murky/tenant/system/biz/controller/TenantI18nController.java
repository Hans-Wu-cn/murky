package cn.murky.tenant.system.biz.controller;

import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import cn.murky.tenant.system.biz.service.ISysI18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.Valid;

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
public class TenantI18nController extends BaseController<ISysI18nService> {
    @ApiOperation("i18n语言包")
    @Get
    @Mapping("language")
    @Valid
    public ApiResult<Map<String, String>> language(@NotBlank String i18nTag, @NotBlank String laguage) {
        return ApiResult.ok(baseService.language(i18nTag, laguage));
    }
}
