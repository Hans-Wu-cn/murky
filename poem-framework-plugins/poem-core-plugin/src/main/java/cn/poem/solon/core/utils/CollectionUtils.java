package cn.poem.solon.core.utils;

import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 *
 * @author hans
 */
public class CollectionUtils {


    /**
     * 判断Collection是否为空  包含 List Set
     * @param collection 任何Collection接口的实现类
     * @return 如果集合不为空则返回true
     */
    public static boolean isNotEmpty(Collection<?> collection){
        if(collection != null && !collection.isEmpty()){
            return true;
        }
        return false;
    }
}
