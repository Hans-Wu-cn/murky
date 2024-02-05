package cn.murky.admin.system.api.domian.bo;

import cn.murky.admin.common.enums.CommonStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典业务类
 */
@Data
@Accessors(chain = true)
public class SysDictBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典状态
     */
    private CommonStatus status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字典数据
     */
    private List<SysDictDataBO> sysDictDataList;
}
