package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.system.domain.entity.SysDictData;
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
