package cn.murky.admin.system.biz.domain.bo;

import cn.murky.admin.core.enums.CommonStatus;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典业务类
 */
@Data
@Accessors(chain = true)
public class SysDictBo {
    /**
     * 主键
     */
    private Long dictTypeId;

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
    private List<SysDictData> sysDictDataList;
}
