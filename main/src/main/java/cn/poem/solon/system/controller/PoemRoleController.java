package cn.poem.solon.system.controller;


import cn.poem.core.extension.BaseController;
import cn.poem.core.utils.ApiResult;
import cn.poem.core.validat.Insert;
import cn.poem.core.validat.Update;
import cn.poem.solon.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.system.domain.dto.PoemRolePageDTO;
import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.table.PoemRoleTableDef;
import cn.poem.solon.system.domain.vo.PoemRoleVo;
import cn.poem.solon.system.service.IPoemRoleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

/***
 * 角色Controller
 *
 * @author hans
 */
@Controller
@Valid
@Mapping("poemRole")
@Api("角色管理")
public class PoemRoleController extends BaseController<IPoemRoleService> {
    @ApiOperation("列表查询")
    @Get
    @Mapping("page")
    public ApiResult<Page<PoemRole>> page(PoemRolePageDTO poemRolePageDTO) {
        Page<PoemRole> result = baseService.page(poemRolePageDTO,
                QueryWrapper.create().
                orderBy(PoemRoleTableDef.POEM_ROLE.CREATE_TIME.asc())
        );
        return ApiResult.ok(result);
    }

    @ApiOperation("菜单详情")
    @Get
    @Mapping("{roleId}")
    public ApiResult<PoemRoleVo> info(Long roleId) {
        return ApiResult.ok(baseService.info(roleId));
    }

    @ApiOperation("新增菜单")
    @Post
    @Mapping
    public ApiResult<PoemRole> add(@Body @Validated(Insert.class) PoemRoleFromDTO poemRoleFromDTO) {
        if (baseService.save(poemRoleFromDTO)) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
    }

    @ApiOperation("修改菜单")
    @Put
    @Mapping
    public ApiResult<?> edit(@Body @Validated(Update.class) PoemRoleFromDTO poemRoleFromDTO) {
        boolean result = baseService.update(poemRoleFromDTO);
        return toResult(result);
    }

    @ApiOperation("删除菜单")
    @Delete
    @Mapping("/{roleId}")
    public ApiResult<?> remove(Long roleId) {
        boolean result = baseService.removeById(roleId);
        return toResult(result);
    }
}
