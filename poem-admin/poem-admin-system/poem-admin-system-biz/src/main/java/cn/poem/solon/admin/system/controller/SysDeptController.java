package cn.poem.solon.admin.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.poem.solon.admin.system.domain.dto.SysDeptFromDTO;
import cn.poem.solon.admin.system.domain.entity.SysDept;
import cn.poem.solon.admin.system.domain.vo.SysDeptTreeVO;
import cn.poem.solon.admin.system.service.ISysDeptService;
import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.core.validat.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

@Controller
@Valid
@Mapping("dept")
@Api("部门管理")
public class SysDeptController extends BaseController<ISysDeptService> {

    @ApiOperation("获取部门树形菜单")
    @Get
    @Mapping("list")
    @SaCheckPermission(value = {"dept","user"},mode = SaMode.OR)
    public ApiResult<List<SysDeptTreeVO>> list(){
        List<SysDeptTreeVO> result = baseService.treeDept();
        return ApiResult.ok(result);
    }

    @ApiOperation("获取部门信息详情")
    @Get
    @Mapping("{deptId}")
    public ApiResult<SysDept> list(Long deptId){
        SysDept result = baseService.getById(deptId);
        return ApiResult.ok(result);
    }

    @ApiOperation("拖动排序")
    @Put
    @Mapping("drop")
    public ApiResult<?> drop(List<Long> deptIds){
        Boolean b = baseService.drop(deptIds);
        return toResult(b);
    }


    @ApiOperation("添加部门")
    @Post
    @Mapping
    @SaCheckPermission("dept:add")
    public ApiResult<?> add(@Body SysDeptFromDTO sysDeptFromDTO){
        boolean b = baseService.save(sysDeptFromDTO);
        return toResult(b);
    }

    @ApiOperation("修改部门")
    @Put
    @Mapping
    @SaCheckPermission("dept:edit")
    public ApiResult<?> edit(@Body  @Validated(Update.class) SysDeptFromDTO sysDeptFromDTO){
        boolean b = baseService.edit(sysDeptFromDTO);
        return toResult(b);
    }

    @ApiOperation("删除部门")
    @Delete
    @Mapping("{deptId}")
    @SaCheckPermission("dept:remove")
    public ApiResult<?> remove(Long deptId){
        boolean b = baseService.remove(deptId);
        return toResult(b);
    }
}
