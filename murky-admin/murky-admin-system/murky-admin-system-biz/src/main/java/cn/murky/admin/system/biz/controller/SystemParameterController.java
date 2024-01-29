package cn.murky.admin.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.murky.admin.system.biz.domain.dto.SystemParameterDTO;
import cn.murky.admin.system.biz.domain.dto.SystemParameterPageDTO;
import cn.murky.admin.system.biz.convert.SystemParameterConvert;
import cn.murky.admin.system.biz.domain.entity.SystemParameter;
import cn.murky.admin.system.biz.domain.entity.table.SystemParameterTableDef;
import cn.murky.admin.system.biz.service.ISystemParameterService;
import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import cn.murky.core.validat.Insert;
import cn.murky.core.validat.Update;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

/***
 * 系统参数Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("systemParameter")
@Api("系统配置管理")
public class SystemParameterController extends BaseController<ISystemParameterService> {
    private final SystemParameterTableDef SYSTEM_PARAMETER=SystemParameterTableDef.SYSTEM_PARAMETER;
    @ApiOperation("系统配置分页查询")
    @Get
    @Mapping("page")
    @SaCheckPermission("systemParameter")
    public ApiResult<Page<SystemParameter>> page(SystemParameterPageDTO page) {
        Page<SystemParameter> result = baseService.page(page,
                QueryWrapper.create().where(SYSTEM_PARAMETER.KEY.eq(page.getKey(), If::hasText))
                        .orderBy(SYSTEM_PARAMETER.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("系统配置详情")
    @Get
    @Mapping("{id}")
    @SaCheckPermission("systemParameter")
    public ApiResult<SystemParameter> info(Long id) {
        return ApiResult.ok(baseService.getById(id));
    }

    @ApiOperation("刷新系统配置缓存")
    @Post
    @Mapping("refresh")
    @SaCheckPermission(mode = SaMode.OR,value=
            {"systemParameter:add","systemParameter:edit","systemParameter:remove"})
    public ApiResult<?> refresh() {
        baseService.refresh();
        return ApiResult.ok();
    }

    @ApiOperation("新增系统配置")
    @Post
    @Mapping
    @SaCheckPermission("systemParameter:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SystemParameterDTO systemParameterDTO) {
        SystemParameter one = baseService.getOne(QueryWrapper.create().where(SYSTEM_PARAMETER.KEY.eq(systemParameterDTO.getKey())));
        if(one!=null){
            return ApiResult.fail("该key已存在");
        }
        SystemParameter entity = SystemParameterConvert.INSTANCES.toEntity(systemParameterDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改系统配置")
    @Put
    @Mapping
    @SaCheckPermission("systemParameter:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class)SystemParameterDTO systemParameterDTO) {
        SystemParameter one = baseService.getOne(QueryWrapper.create().where(SYSTEM_PARAMETER.KEY.eq(systemParameterDTO.getKey())));
        if(one==null){
            return ApiResult.fail("该key不存在");
        }
        SystemParameter entity = SystemParameterConvert.INSTANCES.toEntity(systemParameterDTO);
        return toResult(baseService.updateById(entity));
    }

    @ApiOperation("删除系统配置")
    @Delete
    @Mapping("{id}")
    @SaCheckPermission("systemParameter:remove")
    public ApiResult<?> remove(Long id) {
        return toResult(baseService.removeById(id));
    }
}
