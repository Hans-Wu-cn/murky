package cn.poem.solon.admin.tenant.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.tenant.domain.convert.TenantMenuConvert;
import cn.poem.solon.admin.tenant.domain.dto.TenantMenuDropDTO;
import cn.poem.solon.admin.tenant.domain.dto.TenantMenuFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantMenu;
import cn.poem.solon.admin.tenant.domain.vo.TenantMenuTreeVo;
import cn.poem.solon.admin.tenant.enums.TenantMenuType;
import cn.poem.solon.admin.tenant.service.ITenantMenuService;
import cn.poem.solon.extension.BaseController;
import cn.poem.solon.utils.ApiResult;
import cn.poem.solon.validat.Insert;
import cn.poem.solon.validat.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.snack.ONode;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

/**
 * 租户菜单Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("tenantMenu")
@Api("租户菜单管理")
public class TenantMenuController extends BaseController<ITenantMenuService> {
    @ApiOperation("租户菜单列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("tenantMenu")
    public ApiResult<List<TenantMenuTreeVo>> list() {
        return ApiResult.ok(baseService.treePoemMenu(Arrays.asList(TenantMenuType.MENU,TenantMenuType.DIRECTORY,TenantMenuType.BUTTON)));
    }

    @ApiOperation("租户菜单详情")
    @Get
    @Mapping("{tenantMenuId}")
    public ApiResult<TenantMenu> info(Long tenantMenuId){
        return ApiResult.ok(baseService.getById(tenantMenuId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    @SaCheckPermission("tenantMenu:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) TenantMenuFromDTO tenantMenuFromDTO){
        if(!ONode.loadStr(tenantMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(tenantMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        TenantMenu tenantMenu = TenantMenuConvert.INSTANCES.toEntity(tenantMenuFromDTO);
        if(TenantMenuType.DIRECTORY== tenantMenu.getType()){
            tenantMenu.setComponent("LAYOUT");
        }
        return toResult(baseService.save(tenantMenu));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    @SaCheckPermission("tenantMenu:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) TenantMenuFromDTO tenantMenuFromDTO){
        if(!ONode.loadStr(tenantMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(tenantMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        TenantMenu tenantMenu = TenantMenuConvert.INSTANCES.toEntity(tenantMenuFromDTO);
        if(TenantMenuType.DIRECTORY== tenantMenu.getType()){
            tenantMenu.setComponent("LAYOUT");
        }
        boolean result = baseService.updateById(tenantMenu);
        return toResult(result);
    }

    @ApiOperation("菜单拖动")
    @Put
    @Mapping("drop")
    @SaCheckPermission("tenantMenu:edit")
    public ApiResult<?> drop(@Body @Validated TenantMenuDropDTO tenantMenuDropDTO){
        return toResult(baseService.drop(tenantMenuDropDTO));
    }

    @ApiOperation("删除租户菜单")
    @Delete
    @Mapping("/{tenantMenuId}")
    @SaCheckPermission("tenantMenu:remove")
    public ApiResult<?> remove(Long tenantMenuId){
        boolean result = baseService.removeById(tenantMenuId);
        return toResult(result);
    }
}
