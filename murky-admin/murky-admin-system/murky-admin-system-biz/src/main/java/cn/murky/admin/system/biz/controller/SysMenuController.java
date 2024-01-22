package cn.murky.admin.system.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.murky.admin.system.biz.domain.dto.SysMenuDropDTO;
import cn.murky.admin.system.biz.domain.dto.SysMenuFromDTO;
import cn.murky.admin.system.api.domian.SysMenuTree;
import cn.murky.admin.system.api.enums.MenuType;
import cn.murky.admin.system.biz.domain.entity.SysMenu;
import cn.murky.admin.system.biz.service.ISysMenuService;
import cn.murky.core.web.ApiResult;
import cn.murky.core.web.BaseController;
import cn.murky.core.validat.Insert;
import cn.murky.core.validat.Update;
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
@Mapping("menu")
@Api("菜单管理")
public class SysMenuController extends BaseController<ISysMenuService> {

    @ApiOperation("获取用户菜单")
    @Get
    @Mapping("list")
    @SaCheckPermission("menu")
    public ApiResult<List<SysMenuTree>> list(){
        List<SysMenuTree> result = baseService.treeSysMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY,MenuType.BUTTON));
        return ApiResult.ok(result);
    }

    @ApiOperation("菜单详情")
    @Get
    @Mapping("{menuId}")
    @SaCheckPermission("menu")
    public ApiResult<SysMenu> info(Long menuId){
        return ApiResult.ok(baseService.getById(menuId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    @SaCheckPermission("menu:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) SysMenuFromDTO sysMenuFromDTO){
        if(!ONode.loadStr(sysMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(sysMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        SysMenu sysMenu = sysMenuFromDTO.toEntity();
        if(MenuType.DIRECTORY== sysMenu.getType()){
            sysMenu.setComponent("LAYOUT");
        }
        return toResult(baseService.save(sysMenu));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    @SaCheckPermission("menu:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) SysMenuFromDTO sysMenuFromDTO){
        if(!ONode.loadStr(sysMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(sysMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        SysMenu sysMenu = sysMenuFromDTO.toEntity();
        if(MenuType.DIRECTORY== sysMenu.getType()){
            sysMenu.setComponent("LAYOUT");
        }
        boolean result = baseService.updateById(sysMenu);
        return toResult(result);
    }

    @ApiOperation("菜单拖动")
    @Put
    @Mapping("drop")
    @SaCheckPermission("menu:edit")
    public ApiResult<?> drop(@Body @Validated SysMenuDropDTO sysMenuDropDTO){
        return toResult(baseService.drop(sysMenuDropDTO));
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
