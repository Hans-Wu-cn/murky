package cn.poem.solon.admin.system.domain.vo;

import cn.poem.solon.admin.system.domain.entity.SysDictData;
import cn.poem.solon.admin.system.domain.entity.SysI18n;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * i18n vo
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("i18n vo")
public class SysI18nVo implements Serializable {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("i18n_key编码")
    private String i18nKey;

    @ApiModelProperty("i18n_tag标签")
    private String i18nTag;

    @ApiModelProperty("i18n参数")
    private List<I18nInput> i18nInputs;

    public void setI18nInputs(List<SysI18n> poemI18nList){
        i18nInputs=new ArrayList<>();
        for (SysI18n poemI18n : poemI18nList) {
            i18nInputs.add(new I18nInput()
                            .setId(poemI18n.getId())
                    .setLanguage(poemI18n.getLanguage())
                    .setI18nValue(poemI18n.getI18nValue()));
        }
    }

    public void pushI18nInputs(SysDictData poemDictData){
        if(i18nInputs==null){
            i18nInputs=new ArrayList<>();
        }
        i18nInputs.add(new I18nInput()
                .setLanguage(poemDictData.getDictValue())
                .setI18nValue(null)
        );
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("i18n参数实体")
    public class I18nInput{
        @ApiModelProperty("i18n字典")
        private Long id;

        @ApiModelProperty("i18n字典")
        private String language;

        @ApiModelProperty("i18n值")
        private String i18nValue;
    }

}
