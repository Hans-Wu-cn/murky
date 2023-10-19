package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.dto.PoemI18nPageDTO;

import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import cn.poem.solon.admin.system.domain.entity.table.PoemI18nTableDef;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public interface PoemI18nMapper extends BaseMapper<PoemI18n> {
    PoemI18nTableDef POEM_I18N=PoemI18nTableDef.POEM_I18N;


    /**
     * I18n分页mapper
     */
    default Page<Map<String, String>> page(PoemI18nPageQuery poemI18nPageQuery) {
        PoemI18nPageDTO poemI18nPageDTO = poemI18nPageQuery.getPoemI18nPageDTO();
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_I18N)
                .where(POEM_I18N.I18N_TAG.eq(poemI18nPageDTO.getI18nTag()))
                .and(POEM_I18N.I18N_KEY.like(poemI18nPageDTO.getI18nKey(),If::hasText))
                .groupBy(POEM_I18N.I18N_KEY).orderBy(POEM_I18N.I18N_KEY.desc())
                .select(POEM_I18N.I18N_KEY)
                ;
        for (String i18nKey : poemI18nPageQuery.getI18nKeys()) {
            String sql = MessageFormat.format("STRING_AGG(case " +
                    " when i18n = ''{0}'' then i18n_value " +
                    " else null " +
                    " end, " +
                    " '','' ) as {0}", i18nKey);
            queryWrapper.select(sql);
        }
        return (Page<Map<String, String>>) paginateAs(poemI18nPageDTO.getPageNumber(), poemI18nPageDTO.getPageSize(),queryWrapper,(new HashMap<String,String>()).getClass());
    }

    /**
     * 根据i18n,i18nKey,i18nTag修改i18nValue
     * @param poemI18n
     * @return
     */
    default int updateI18nValue(PoemI18n poemI18n){
        QueryCondition condition = QueryCondition.create(POEM_I18N.I18N_KEY, poemI18n.getI18nKey())
                .and(QueryCondition.create(POEM_I18N.I18N_TAG, poemI18n.getI18nTag()))
                .and(QueryCondition.create(POEM_I18N.I18N, poemI18n.getI18n()))
                ;
        return this.updateByCondition(poemI18n,condition);
    }

    default int deleteByI18nKey(String i18nKey){
        return this.deleteByQuery(QueryWrapper.create().where(POEM_I18N.I18N_KEY.eq(i18nKey)));
    }

}
