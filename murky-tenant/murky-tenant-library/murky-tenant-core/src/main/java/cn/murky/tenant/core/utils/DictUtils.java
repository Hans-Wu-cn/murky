package cn.murky.tenant.core.utils;

import cn.murky.common.domain.sdo.SysDictDataSDO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DictUtils {

    private static Map<String, List<SysDictDataSDO>> DICT_MAP = new ConcurrentHashMap<>();

    public static List<SysDictDataSDO> get(String dictKey) {
        return DICT_MAP.get(dictKey);
    }

    public static void refresh(Map<String, List<SysDictDataSDO>> map) {
        DICT_MAP=map;
    }
}
