package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.biz.domian.entity.SysI18n;
import cn.murky.tenant.system.biz.domian.entity.table.SysI18nTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface SysI18nMapper extends BaseMapper<SysI18n> {

    /**
     * 根据Language和Tag查询
     * @return 相关数据
     */
    default List<SysI18n> selectByLanguageAndTag(String i18nTag, String language) {
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        return this.selectListByQuery(QueryWrapper.create()
                .where(SYS_I18N.LANGUAGE.eq(language))
                .and(SYS_I18N.I18N_TAG.eq(i18nTag))
        );
    }
}
