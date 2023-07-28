package cn.poem.solon.system.controller;

import cn.poem.core.utils.ApiResult;
import cn.poem.solon.system.domain.dto.LoginDto;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PoemLoginController {

    @Post
    @Mapping("login")
    public ApiResult login(@Body LoginDto loginDto){
        System.out.println(loginDto);
        Map<String,String> map=new HashMap<>();
        map.put("token","MSHXEYEYDQMXBJUVGPIEYDYSKYCOEMCG");
        return ApiResult.ok(map);
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
