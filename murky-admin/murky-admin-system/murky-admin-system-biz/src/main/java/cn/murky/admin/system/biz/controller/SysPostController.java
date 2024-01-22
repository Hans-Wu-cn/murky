package cn.murky.admin.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.murky.admin.system.biz.domain.convert.SysPostConvert;
import cn.murky.admin.system.biz.domain.dto.SysPostFromDTO;
import cn.murky.admin.system.biz.domain.dto.SysPostPageDTO;
import cn.murky.admin.system.biz.domain.entity.SysPost;
import cn.murky.admin.system.biz.domain.entity.table.SysPostTableDef;
import cn.murky.admin.system.biz.service.ISysPostService;
import cn.murky.core.validat.Insert;
import cn.murky.core.validat.Update;
import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;
import java.util.List;

/***
 * 岗位Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("post")
@Api("岗位管理管理")
public class SysPostController extends BaseController<ISysPostService> {

    @ApiOperation("获取用户岗位分页")
    @Get
    @Mapping("page")
    @SaCheckPermission("post")
    public ApiResult<Page<SysPost>> page(SysPostPageDTO sysPostPageDTO){
        Page<SysPost> page = baseService.page(sysPostPageDTO);
        return ApiResult.ok(page);
    }

    @ApiOperation("获取用户岗位分页")
    @Get
    @Mapping("list")
    public ApiResult<List<SysPost>> list(SysPostPageDTO sysPostPageDTO){
        SysPostTableDef SYS_POST = SysPostTableDef.SYS_POST;
        List<SysPost> list = baseService.list(QueryWrapper.create()
                .and(SYS_POST.POST_NAME.like(sysPostPageDTO.getPostName()))
                .orderBy(SYS_POST.POST_NAME.asc()));
        return ApiResult.ok(list);
    }

    @ApiOperation("岗位详情")
    @Get
    @Mapping("{id}")
    @SaCheckPermission("post")
    public ApiResult<SysPost> info(Long id){
        return ApiResult.ok(baseService.getById(id));
    }

    @ApiOperation("新增岗位")
    @Post
    @Mapping
    @SaCheckPermission("post:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SysPostFromDTO sysPostFromDTO){
        SysPost entity = SysPostConvert.INSTANCES.toEntity(sysPostFromDTO);
        return toResult(baseService.save(entity));
    }

    @ApiOperation("修改岗位")
    @Put
    @Mapping
    @SaCheckPermission("post:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysPostFromDTO sysPostFromDTO){
        SysPost entity = SysPostConvert.INSTANCES.toEntity(sysPostFromDTO);
        return toResult(baseService.updateById(entity));
    }

    @ApiOperation("删除岗位")
    @Delete
    @Mapping("/{id}")
    @SaCheckPermission("post:remove")
    public ApiResult<?> remove(Long id){
        return toResult(baseService.removeById(id));
    }
}
