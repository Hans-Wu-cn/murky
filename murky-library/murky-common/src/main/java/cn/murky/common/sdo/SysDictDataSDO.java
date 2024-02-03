package cn.murky.common.sdo;

import lombok.Data;

@Data
public class SysDictDataSDO {
    /**
     * 字典编码
     */
    private Long dictCode;

    /**
     * 字典排序
     */
    private Short dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

}
