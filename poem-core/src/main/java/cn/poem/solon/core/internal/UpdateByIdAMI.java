//package cn.poem.solon.core.internal;
//
//import cn.poem.solon.core.enetity.BaseEntity;
//import org.beetl.sql.core.SQLManager;
//import org.beetl.sql.mapper.MapperInvoke;
//
//import java.lang.reflect.Method;
//import java.util.Date;
//
//public class UpdateByIdAMI extends MapperInvoke {
//    @Override
//    public Object call(SQLManager sm, Class entityClass, Method m, Object[] args) {
//        if(args[0] instanceof BaseEntity){
//            Date date = new Date();
//            ((BaseEntity) args[0]).setUpdateTime(date);
//        }
//        return  sm.updateById(args[0]);
//    }
//}
