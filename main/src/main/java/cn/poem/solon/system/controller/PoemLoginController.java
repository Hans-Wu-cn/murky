package cn.poem.solon.system.controller;

import cn.poem.core.utils.ApiResult;
import cn.poem.solon.system.domain.dto.LoginDto;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.service.IPoemUserService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.annotation.*;
import org.noear.solon.validation.annotation.Valid;

import javax.management.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 登录Controller
 *
 * @author hans
 */
@Controller
@Valid
@Api("角色管理")
public class PoemLoginController {

    @Inject
    IPoemUserService iPoemUserService;

    @Post
    @Mapping("login")
    public ApiResult login(@Body LoginDto loginDto){
        System.out.println(loginDto);
        PoemUser one = iPoemUserService.getOne(QueryWrapper.create().where(
                PoemUserTableDef.POEM_USER.ACCOUNT.eq(loginDto.getUsername())
        ).and(PoemUserTableDef.POEM_USER.PASSWORD.eq(loginDto.getPassword())));
        if(one !=null){
            Map<String,String> map=new HashMap<>();
            map.put("token","MSHXEYEYDQMXBJUVGPIEYDYSKYCOEMCG");
            return ApiResult.ok(map);
        }
        return ApiResult.fail();
    }

    @Get
    @Mapping("admin_info")
    public ApiResult adminInfo(){
        Map<String,Object> result=new HashMap<>();
        result.put("userId","1");
        result.put("username","admin");
        result.put("realName","Admin");
//        result.put("avatar","1");
        result.put("desc","manager");
        result.put("password","password");
        result.put("token","MSHXEYEYDQMXBJUVGPIEYDYSKYCOEMCG");
        List<Vo> vos = new ArrayList<>();
        vos.add(new Vo().setLable("主控台").setValue("dashboard_console"));
        vos.add(new Vo().setLable("监控页").setValue("dashboard_monitor"));
        vos.add(new Vo().setLable("工作台").setValue("dashboard_workplace"));
        vos.add(new Vo().setLable("基础列表").setValue("basic_list"));
        vos.add(new Vo().setLable("基础列表删除").setValue("basic_list_delete"));
        result.put("permissions",vos);
        return ApiResult.ok(result);
    }
}
@Data
@Accessors(chain = true)
class Vo{
    private String lable;
    private String value;
}
