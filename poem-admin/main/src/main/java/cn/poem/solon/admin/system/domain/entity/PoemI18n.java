package cn.poem.solon.admin.system.domain.entity;

import cn.poem.solon.admin.domin.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * i18n实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("i18n实体类")
@Table(value = "poem_i18n")
public class PoemI18n extends BaseEntity {
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("i18n编码")
    private String i18nKey;

    @ApiModelProperty("i18n值")
    private String i18nValue;

    @ApiModelProperty("地区编码(字典:i18n)")
    private String language;

    @ApiModelProperty("标签(字典:i18n:tag)")
    private String i18nTag;
}
