//package cn.poem.solon.core.extension;
//
//import org.beetl.sql.mapper.BaseMapper;
//import org.noear.solon.data.annotation.Tran;
//
///**
// * 通用基础service
// * @param <T>
// */
//public interface IService<T> {
//
//    /**
//     * 通用保存接口
//     * @param t
//     * @return T
//     */
//    @Tran
//    default T save(T t){
//        getPoemMapper().insert(t);
//        return t;
//    }
//
//    /**
//     * 通用修改接口 根据主键删除
//     * @param t
//     * @return boolean
//     */
//    @Tran
//    default boolean updateById(T t){
//        return getPoemMapper().updateById(t)>0;
//    }
//
//    /**
//     * 通用删除接口 传入删除主键
//     * @param id
//     * @return boolean
//     */
//    @Tran
//    default boolean deleteById(Long id){
//        return getPoemMapper().deleteById(id)>0;
//    }
//
//    /**
//     * 通用查询接口 根据主键查询
//     * @param id
//     * @return T
//     */
//    default T getById(Long id){
//        return getPoemMapper().single(id);
//    }
//
//
//    BaseMapper<T> getPoemMapper();
//}
