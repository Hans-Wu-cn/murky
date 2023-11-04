package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.enums.DictStatus;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("字典数据分页DTO")
public class PoemDictDataPageDTO extends Page<PoemDictData> {
    @ApiModelProperty(value = "字典标签",required = true)
    private String dictLabel;

    @ApiModelProperty("字典类型")
    @NotBlank
    private String dictType;

    @ApiModelProperty("字典状态")
    private DictStatus status;
}
