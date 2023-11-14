package cn.poem.solon.admin.saas.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.poem.solon.admin.core.extension.BaseController;
import cn.poem.solon.admin.core.validat.Insert;
import cn.poem.solon.admin.core.validat.Update;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasMenuConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemMenuDropDTO;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasMenuFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasMenuTreeVo;
import cn.poem.solon.admin.saas.service.IPoemSaasMenuService;
import cn.poem.solon.admin.security.enums.MenuType;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.utils.ApiResult;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.snack.ONode;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

/**
 * 商户菜单Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemSaasMenu")
@Api("商户菜单管理")
public class PoemSaasMenuController extends BaseController<IPoemSaasMenuService> {
    @ApiOperation("商户菜单列表查询")
    @Get
    @Mapping("list")
    @SaCheckPermission("saasMenu")
    public ApiResult<List<PoemSaasMenuTreeVo>> list() {
        return ApiResult.ok(baseService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY,MenuType.BUTTON)));
    }

    @ApiOperation("商户菜单详情")
    @Get
    @Mapping("{saasMenuId}")
    public ApiResult<PoemSaasMenu> info(Long saasMenuId){
        return ApiResult.ok(baseService.getById(saasMenuId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    public ApiResult<?> add(@Body @Validated(Insert.class) PoemSaasMenuFromDTO poemSaasMenuFromDTO){
        if(!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemSaasMenu poemMenu = PoemSaasMenuConvert.INSTANCES.toEntity(poemSaasMenuFromDTO);
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        return toResult(baseService.save(poemMenu));
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemSaasMenuFromDTO poemSaasMenuFromDTO){
        if(!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isUndefined()&&!ONode.loadStr(poemSaasMenuFromDTO.getQuery()).isObject()){
            return ApiResult.fail("query参数格式不正确");
        }
        PoemSaasMenu poemMenu = PoemSaasMenuConvert.INSTANCES.toEntity(poemSaasMenuFromDTO);
//        poemMenu.setDeptId(SecurityUtils.getUserInfo().getDeptId());
        if(MenuType.DIRECTORY==poemMenu.getType()){
            poemMenu.setComponent("LAYOUT");
        }
        boolean result = baseService.updateById(poemMenu);
        return toResult(result);
    }

    @ApiOperation("菜单拖动")
    @Put
    @Mapping("drop")
    public ApiResult<?> drop(@Body @Validated PoemMenuDropDTO poemMenuDropDTO){
        return toResult(baseService.drop(poemMenuDropDTO));
    }

    @ApiOperation("删除商户菜单")
    @Delete
    @Mapping("/{saasMenuId}")
    public ApiResult<?> remove(Long saasMenuId){
        boolean result = baseService.removeById(saasMenuId);
        return toResult(result);
    }
}
