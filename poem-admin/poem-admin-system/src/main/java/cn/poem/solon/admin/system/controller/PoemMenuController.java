package cn.poem.solon.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.security.entity.PoemMenuTree;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.system.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.system.domain.dto.PoemMenuFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.service.IPoemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.snack.ONode;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

/***
 * 菜单Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemMenu")
@Api("菜单管理")
public class PoemMenuController extends BaseController<IPoemMenuService> {

    @ApiOperation("获取用户菜单")
    @Get
    @Mapping("list")
    @SaCheckPermission("menu")
    public ApiResult<List<PoemMenuTree>> list(){
        List<PoemMenuTree> result = baseService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY,MenuType.BUTTON));
        return ApiResult.ok(result);
    }

    @ApiOperation("菜单详情")
    @Get
    @Mapping("{menuId}")
    @SaCheckPermission("menu")
    public ApiResult<PoemMenu> info(Long menuId){
        return ApiResult.ok(baseService.getById(menuId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    @SaCheckPermission("menu:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemMenuFromDTO poemMenuFromDTO){
        if(!ONode.loadStr(poemMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemMenu poemMenu = poemMenuFromDTO.toEntity();
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        return toResult(baseService.save(poemMenu));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    @SaCheckPermission("menu:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemMenuFromDTO poemMenuFromDTO){
        if(!ONode.loadStr(poemMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemMenu poemMenu = poemMenuFromDTO.toEntity();
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        boolean result = baseService.updateById(poemMenu);
        return toResult(result);
    }

    @ApiOperation("菜单拖动")
    @Put
    @Mapping("drop")
    @SaCheckPermission("menu:edit")
    public ApiResult<?> drop(@Body @Validated PoemMenuDropDTO poemMenuDropDTO){
        return toResult(baseService.drop(poemMenuDropDTO));
    }

    @ApiOperation("删除菜单")
    @Delete
    @Mapping("/{menuId}")
    @SaCheckPermission("menu:remove")
    public ApiResult<?> remove(Long menuId){
        boolean result = baseService.removeById(menuId);
        return toResult(result);
    }

}
