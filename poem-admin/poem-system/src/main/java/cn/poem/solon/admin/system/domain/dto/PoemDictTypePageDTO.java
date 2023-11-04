package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.enums.DictStatus;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("字典类型分页DTO实体")
public class PoemDictTypePageDTO  extends Page<PoemDictType> {

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典状态")
    private DictStatus status;
}
