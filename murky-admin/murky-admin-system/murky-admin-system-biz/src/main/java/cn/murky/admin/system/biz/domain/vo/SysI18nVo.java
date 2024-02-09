package cn.murky.admin.system.biz.domain.vo;

import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.domain.entity.SysI18n;
import cn.murky.common.domain.bo.SysDictDataBO;
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

    public void setI18nInputs(List<SysI18n> sysI18nList){
        i18nInputs=new ArrayList<>();
        for (SysI18n sysI18n : sysI18nList) {
            i18nInputs.add(new I18nInput()
                            .setId(sysI18n.getId())
                    .setLanguage(sysI18n.getLanguage())
                    .setI18nValue(sysI18n.getI18nValue()));
        }
    }

    public void pushI18nInputs(SysDictData sysDictData){
        if(i18nInputs==null){
            i18nInputs=new ArrayList<>();
        }
        i18nInputs.add(new I18nInput()
                .setLanguage(sysDictData.getDictValue())
                .setI18nValue(null)
        );
    }

    public void pushI18nInputs(SysDictDataBO sysDictData){
        if(i18nInputs==null){
            i18nInputs=new ArrayList<>();
        }
        i18nInputs.add(new I18nInput()
                .setLanguage(sysDictData.getDictValue())
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
