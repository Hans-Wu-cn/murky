package cn.poem.solon.admin.system.domain.dto;

import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("i18n分页DTO实体")
public class PoemI18nPageDTO extends Page<PoemI18n> {
    @ApiModelProperty(value = "i18n标签",required = true)
    @NotBlank
    private String i18nTag;

    @ApiModelProperty("i18nKey")
    private String i18nKey;

}
