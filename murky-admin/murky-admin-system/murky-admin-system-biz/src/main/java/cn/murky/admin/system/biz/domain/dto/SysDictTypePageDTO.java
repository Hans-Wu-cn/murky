package cn.murky.admin.system.biz.domain.dto;

import cn.murky.admin.core.enums.CommonStatus;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("字典类型分页DTO实体")
public class SysDictTypePageDTO extends Page<SysDictType> {

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典状态")
    private CommonStatus status;
}
