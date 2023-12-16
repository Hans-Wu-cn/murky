package cn.poem.solon.admin.system.domain.query;

import cn.poem.solon.admin.system.domain.dto.SysI18nDTO;
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
public class SysI18nPageQuery {

    /**
     * 前端分页视图参数
     */
    private SysI18nDTO sysI18nDTO;

    /**
     * i18n语言集合
     */
    private List<String> i18nKeys;
}
