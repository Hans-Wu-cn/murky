package cn.murky.admin.system.biz.domain.dto;

import cn.murky.admin.system.biz.domain.entity.SysI18n;
import cn.murky.core.validat.Info;
import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel("i18n分页DTO实体")
public class SysI18nDTO extends Page<SysI18n> {
    @ApiModelProperty(value = "i18n标签(字典：i18n:tag)",required = true)
    @NotBlank
    private String i18nTag;

    @ApiModelProperty(value = "i18nValue")
    private String i18nValue;

    @ApiModelProperty("i18nKey")
    @NotBlank(groups = Info.class)
    private String i18nKey;

}
