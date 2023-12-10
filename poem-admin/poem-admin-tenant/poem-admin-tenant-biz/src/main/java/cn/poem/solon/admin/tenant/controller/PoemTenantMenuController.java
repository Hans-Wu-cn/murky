package cn.poem.solon.admin.tenant.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.tenant.domain.convert.PoemTenantMenuConvert;
import cn.poem.solon.admin.tenant.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantMenuFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantMenu;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantMenuTreeVo;
import cn.poem.solon.admin.tenant.service.IPoemTenantMenuService;
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
@Mapping("poemTenantMenu")
@Api("租户菜单管理")
public class PoemTenantMenuController extends BaseController<IPoemTenantMenuService> {
    @ApiOperation("租户菜单列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("tenantMenu")
    public ApiResult<List<PoemTenantMenuTreeVo>> list() {
        return ApiResult.ok(baseService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY,MenuType.BUTTON)));
    }

    @ApiOperation("租户菜单详情")
    @Get
    @Mapping("{tenantMenuId}")
    public ApiResult<PoemTenantMenu> info(Long tenantMenuId){
        return ApiResult.ok(baseService.getById(tenantMenuId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    @SaCheckPermission("tenantMenu:add")
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemTenantMenuFromDTO poemTenantMenuFromDTO){
        if(!ONode.loadStr(poemTenantMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemTenantMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemTenantMenu poemMenu = PoemTenantMenuConvert.INSTANCES.toEntity(poemTenantMenuFromDTO);
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        return toResult(baseService.save(poemMenu));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    @SaCheckPermission("tenantMenu:edit")
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemTenantMenuFromDTO poemSaasMenuFromDTO){
        if(!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemTenantMenu poemMenu = PoemTenantMenuConvert.INSTANCES.toEntity(poemSaasMenuFromDTO);
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        boolean result = baseService.updateById(poemMenu);
        return toResult(result);
    }

    @ApiOperation("菜单拖动")
    @Put
    @Mapping("drop")
    @SaCheckPermission("tenantMenu:edit")
    public ApiResult<?> drop(@Body @Validated PoemMenuDropDTO poemMenuDropDTO){
        return toResult(baseService.drop(poemMenuDropDTO));
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
