package cn.murky.admin.system.biz.domain.dto;

import cn.murky.common.enums.CommonStatus;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("字典数据分页DTO")
public class SysDictDataPageDTO extends Page<SysDictData> {
    @ApiModelProperty(value = "字典标签",required = true)
    private String dictLabel;

    @ApiModelProperty("字典类型")
    @NotBlank
    private String dictType;

    @ApiModelProperty("字典状态")
    private CommonStatus status;
}
