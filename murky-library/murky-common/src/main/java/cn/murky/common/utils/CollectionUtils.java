package cn.murky.common.utils;

import org.noear.solon.Utils;

import java.util.Collection;

public class CollectionUtils {

    /**
     * 判断集合长度是否等于0
     */
    public static boolean isEmpty(Collection collection) {
        return Utils.isEmpty(collection);
    }

    /**
     * 判断集合长度是否大于0
     */
    public static boolean isNotEmpty(Collection collection) {
        return Utils.isNotEmpty(collection);
    }
}
