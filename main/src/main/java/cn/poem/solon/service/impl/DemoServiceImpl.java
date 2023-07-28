//package cn.poem.solon.admin.service.impl;
//
//import cn.poem.solon.admin.entity.Demo;
//import org.beetl.sql.solon.annotation.Db;
//import cn.poem.solon.admin.mapper.DemoMapper;
//import cn.poem.solon.admin.service.DemoService;
//import org.noear.solon.annotation.ProxyComponent;
//
//@ProxyComponent
//public class DemoServiceImpl implements DemoService {
//   @Db
////   @Inject
//   DemoMapper demoMapper;
//    @Override
//    public Demo findById(Long id) {
//        return demoMapper.single(id);
//    }
//
//    @Override
//    public int insert(Demo demo) {
//        demo.setId(2L);
//        demo.setName("22");
//        demoMapper.insert(demo);
//        return 0;
//    }
////    @Db
////    BaseMapper<Demo> appBaseMapper;
//}
