package cn.poem.solon.admin.system.domain.query;

import cn.poem.solon.admin.system.domain.dto.PoemI18nPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * i18n分页查询query实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class PoemI18nPageQuery {

    /**
     * 前端分页视图参数
     */
    private PoemI18nPageDTO poemI18nPageDTO;

    /**
     * i18n语言集合
     */
    private List<String> i18nKeys;
}
