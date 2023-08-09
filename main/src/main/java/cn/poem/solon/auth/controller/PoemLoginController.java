package cn.poem.solon.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.poem.core.utils.ApiResult;
import cn.poem.solon.auth.service.IPoemLoginService;
import cn.poem.solon.system.domain.dto.LoginDto;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.domain.vo.PoemMenuTreeVO;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.service.IPoemMenuService;
import cn.poem.solon.system.service.IPoemUserService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

import java.util.*;

/***
 * 登录Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("登录控制器")
@Mapping("auth")
public class PoemLoginController {

    @Inject
    IPoemLoginService iPoemLoginService;
    @Inject
    IPoemMenuService iPoemMenuService;
    @Post
    @Mapping("login")
    public ApiResult login(@Body LoginDto loginDto) {
        System.out.println(loginDto);
        SaTokenInfo tokenInfo = iPoemLoginService.login(loginDto);
        Map<String, String> map = new HashMap<>();
        map.put("token", tokenInfo.getTokenValue());
        return ApiResult.ok(map);
    }

    @Get
    @Mapping("menu")
    public ApiResult<List<PoemMenuTreeVO>> menu() {
        return ApiResult.ok(iPoemMenuService.treePoemMenu(Arrays.asList(MenuType.MENU,MenuType.DIRECTORY)));
    }

    @Get
    @Mapping("info")
    public ApiResult userInfo() {
//        Map<String, Object> result = new HashMap<>();
//        result.put("userId", "1");
//        result.put("username", "admin");
//        result.put("realName", "Admin");
////        result.put("avatar","1");
//        result.put("desc", "manager");
//        result.put("password", "password");
//        result.put("token", "MSHXEYEYDQMXBJUVGPIEYDYSKYCOEMCG");
//        List<Vo> vos = new ArrayList<>();
//        vos.add(new Vo().setLable("主控台").setValue("dashboard_console"));
//        vos.add(new Vo().setLable("监控页").setValue("dashboard_monitor"));
//        vos.add(new Vo().setLable("工作台").setValue("dashboard_workplace"));
//        vos.add(new Vo().setLable("基础列表").setValue("basic_list"));
//        vos.add(new Vo().setLable("基础列表删除").setValue("basic_list_delete"));
//        result.put("permissions", vos);

        return ApiResult.ok(iPoemLoginService.userInfo());
    }
}

@Data
@Accessors(chain = true)
class Vo {
    private String lable;
    private String value;
}
