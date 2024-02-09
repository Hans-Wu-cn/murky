package cn.murky.common.domain.bo;

import cn.murky.common.enums.CommonStatus;
import lombok.Data;

@Data
public class SysDictDataBO {

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

    /**
     * 状态
     */
    private CommonStatus status;

    /**
     * 备注
     */
    private String remark;
}
