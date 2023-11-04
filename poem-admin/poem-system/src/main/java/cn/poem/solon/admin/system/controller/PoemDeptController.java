package cn.poem.solon.admin.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.dto.PoemDeptFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemDept;
import cn.poem.solon.admin.system.domain.vo.PoemDeptTreeVO;
import cn.poem.solon.admin.system.service.IPoemDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

@Controller
@Valid
@Mapping("poemDept")
@Api("部门管理")
public class PoemDeptController extends BaseController<IPoemDeptService> {

    @ApiOperation("获取部门树形菜单")
    @Get
    @Mapping("list")
    @SaCheckPermission(value = {"dept","user"},mode = SaMode.OR)
    public ApiResult<List<PoemDeptTreeVO>> list(){
        List<PoemDeptTreeVO> result = baseService.treeDept();
        return ApiResult.ok(result);
    }

    @ApiOperation("获取部门信息详情")
    @Get
    @Mapping("{deptId}")
    public ApiResult<PoemDept> list(Long deptId){
        PoemDept result = baseService.getById(deptId);
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
    public ApiResult<?> add(@Body PoemDeptFromDTO poemDeptFromDTO){
        boolean b = baseService.save(poemDeptFromDTO);
        return toResult(b);
    }

    @ApiOperation("修改部门")
    @Put
    @Mapping
    @SaCheckPermission("dept:edit")
    public ApiResult<?> edit(@Body  @Validated(Update.class)PoemDeptFromDTO poemDeptFromDTO){
        boolean b = baseService.edit(poemDeptFromDTO);
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
