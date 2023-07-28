//package cn.poem.solon.admin.controller;
//
//import cn.poem.solon.admin.entity.Demo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import cn.poem.solon.admin.service.DemoService;
//import org.noear.solon.annotation.*;
//
//@Controller
//@Api("demo")
//public class DemoController {
//    @Inject
//    DemoService demoService;
//    @Mapping("/hello")
//    @ApiOperation("demo")
//    public String hello(@Param(defaultValue = "world") String name) {
//        System.out.println("111111111111");
//        demoService.insert(new Demo());
//        return String.format("Hello %s!", name);
//    }
//
//}