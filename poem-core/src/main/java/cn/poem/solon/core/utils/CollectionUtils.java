package cn.poem.solon.core.utils;

import java.util.List;

/**
 * 集合工具类
 *
 * @author hans
 */
public class CollectionUtils {


    /**
     * 判断List是否为空
     * @param list 任何List接口的实现类
     * @return 如果集合不为空则返回true
     */
    public static boolean isNotEmpty(List<?> list){
        if(list != null && list.size()>0){
            return true;
        }
        return false;
    }
}
