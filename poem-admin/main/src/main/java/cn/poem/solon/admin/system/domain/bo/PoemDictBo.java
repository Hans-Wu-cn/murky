package cn.poem.solon.admin.system.domain.bo;

import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.enums.DictStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典业务类
 */
@Data
@Accessors(chain = true)
@ApiModel("字典业务类")
public class PoemDictBo {
    @ApiModelProperty("主键")
    private Long dictTypeId;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典状态")
    private DictStatus status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("字典数据")
    private List<PoemDictData> poemDictDatas;
}
