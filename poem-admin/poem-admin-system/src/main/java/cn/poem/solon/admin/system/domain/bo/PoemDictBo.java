package cn.poem.solon.admin.system.domain.bo;

import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.enums.DictStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典业务类
 */
@Data
@Accessors(chain = true)
public class PoemDictBo {
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
    private DictStatus status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字典数据
     */
    private List<PoemDictData> poemDictDatas;
}
