package cn.poem.solon.system.controller;


import cn.poem.solon.core.extension.BaseController;
import cn.poem.solon.core.utils.ApiResult;
import cn.poem.solon.core.validat.Update;
import cn.poem.solon.system.domain.dto.PoemDeptFromDTO;
import cn.poem.solon.system.domain.entity.PoemDept;
import cn.poem.solon.system.domain.vo.PoemDeptTreeVO;
import cn.poem.solon.system.service.IPoemDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.List;

@Controller
@Valid
@Mapping("PoemDept")
@Api("部门管理")
public class PoemDeptController extends BaseController<IPoemDeptService> {

    @ApiOperation("获取部门树形菜单")
    @Get
    @Mapping("list")
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


    @ApiOperation("添加部门")
    @Post
    @Mapping
    public ApiResult<?> add(@Body PoemDeptFromDTO poemDeptFromDTO){
        PoemDept poemDept = poemDeptFromDTO.toEntity();
        boolean b = baseService.save(poemDept);
        return toResult(b);
    }

    @ApiOperation("修改部门")
    @Put
    @Mapping
    public ApiResult<?> edit(@Body  @Validated(Update.class)PoemDeptFromDTO poemDeptFromDTO){
        PoemDept poemDept = poemDeptFromDTO.toEntity();
        boolean b = baseService.updateById(poemDept);
        return toResult(b);
    }

    @ApiOperation("删除部门")
    @Delete
    @Mapping("{deptId}")
    public ApiResult<?> remove(Long deptId){
        boolean b = baseService.removeById(deptId);
        return toResult(b);
    }
}
