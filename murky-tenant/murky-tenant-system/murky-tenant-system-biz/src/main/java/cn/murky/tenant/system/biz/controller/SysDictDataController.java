package cn.murky.tenant.system.biz.controller;

import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import cn.murky.tenant.system.api.domain.bo.SysDictDataBO;
import cn.murky.tenant.system.biz.domian.entity.SysDictData;
import cn.murky.tenant.system.biz.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

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

    @ApiOperation("获取指定字典数据")
    @Get
    @Mapping("dict/{dictType}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型")
    })
    public ApiResult<List<SysDictDataBO>> list(String dictType) {
        List<SysDictDataBO> result = baseService.getDict(dictType);
        return ApiResult.ok(result);
    }

}
