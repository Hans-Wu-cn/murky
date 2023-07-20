//package cn.poem.solon.core.extension;
//
//import cn.poem.solon.core.internal.InsertAMI;
//import cn.poem.solon.core.internal.UpdateByIdAMI;
//import org.beetl.sql.mapper.BaseMapper;
//import org.beetl.sql.mapper.annotation.AutoMapper;
//
//public interface PoemMapper<T> extends BaseMapper<T> {
//
//    @Override
//    @AutoMapper(InsertAMI.class)
//    void insert(T entity);
//
//    @Override
//    @AutoMapper(UpdateByIdAMI.class)
//    int updateById(T entity);
//
//}
