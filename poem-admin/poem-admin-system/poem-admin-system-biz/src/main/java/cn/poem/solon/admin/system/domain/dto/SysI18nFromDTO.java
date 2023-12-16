package cn.poem.solon.admin.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.util.List;

/**
 * i18n表单DTO实体
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("i18n表单DTO实体")
public class SysI18nFromDTO {

    @ApiModelProperty("i18n_key编码")
    @NotBlank
    private String i18nKey;

    @ApiModelProperty("i18n_tag标签")
    @NotBlank
    private String i18nTag;

    @ApiModelProperty("i18n参数")
    @NotNull
    private List<I18nInput> i18nInputs;

    @Data
    @Accessors(chain = true)
    @ApiModel("i18n参数实体")
    public class I18nInput{
        @ApiModelProperty("id")
        private Long id;

        @ApiModelProperty("i18n字典")
        private String language;

        @ApiModelProperty("i18n值")
        private String i18nValue;
    }
}
