package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.dto.PoemI18nDTO;
import cn.poem.solon.admin.system.domain.dto.PoemI18nFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import cn.poem.solon.admin.system.domain.entity.table.PoemI18nTableDef;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import cn.poem.solon.admin.system.domain.vo.PoemI18nVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface PoemI18nMapper extends BaseMapper<PoemI18n> {
    PoemI18nTableDef POEM_I18N=PoemI18nTableDef.POEM_I18N;


    /**
     * I18n分页mapper
     */
    default Page<Map> page(PoemI18nPageQuery poemI18nPageQuery) {
        PoemI18nDTO poemI18NDTO = poemI18nPageQuery.getPoemI18nDTO();
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_I18N)
                .where(POEM_I18N.I18N_TAG.eq(poemI18NDTO.getI18nTag()))
                .and(POEM_I18N.I18N_KEY.like(poemI18NDTO.getI18nKey(),If::hasText))
                .groupBy(POEM_I18N.I18N_KEY,POEM_I18N.I18N_TAG).orderBy(POEM_I18N.I18N_KEY.desc())
                .select(POEM_I18N.I18N_KEY,POEM_I18N.I18N_TAG)
                ;
        for (String i18nKey : poemI18nPageQuery.getI18nKeys()) {
            String sql = MessageFormat.format("STRING_AGG(case " +
                    " when i18n = ''{0}'' then i18n_value " +
                    " else null " +
                    " end, " +
                    " '','' ) as \"{0}\"", i18nKey);
            queryWrapper.select(sql);
        }
        return paginateAs(poemI18NDTO.getPageNumber(), poemI18NDTO.getPageSize(),queryWrapper, Map.class);
    }


    /**
     * I18n详情数据
     */
    default PoemI18nVo info(PoemI18nPageQuery poemI18nPageQuery) {
        PoemI18nDTO poemI18NDTO = poemI18nPageQuery.getPoemI18nDTO();
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_I18N)
                .where(POEM_I18N.I18N_TAG.eq(poemI18NDTO.getI18nTag()))
                .and(POEM_I18N.I18N_KEY.eq(poemI18NDTO.getI18nKey()))
//                .groupBy(POEM_I18N.I18N_KEY).orderBy(POEM_I18N.I18N_KEY.desc())
//                .select(POEM_I18N.I18N_KEY)
                ;
//        for (String i18nKey : poemI18nPageQuery.getI18nKeys()) {
//            String sql = MessageFormat.format("STRING_AGG(case " +
//                    " when i18n = ''{0}'' then i18n_value " +
//                    " else null " +
//                    " end, " +
//                    " '','' ) as \"{0}\"", i18nKey);
//            queryWrapper.select(sql);
//        }
        List<PoemI18n> poemI18nList = selectListByQuery(queryWrapper);
        PoemI18nVo poemI18nVo = new PoemI18nVo();
        if(!poemI18nList.isEmpty()){
            poemI18nVo.setI18nKey(poemI18nList.get(0).getI18nKey());
            poemI18nVo.setI18nTag(poemI18nList.get(0).getI18nTag());
            poemI18nVo.setI18nInputs(poemI18nList);
        }
        return poemI18nVo;
    }

    /**
     * 根据i18n,i18nKey,i18nTag修改i18nValue
     * @return 受影响行
     */
    default int updateI18nValue(PoemI18n poemI18n){
        QueryCondition condition = QueryCondition.create(POEM_I18N.I18N_KEY, poemI18n.getI18nKey())
                .and(QueryCondition.create(POEM_I18N.I18N_TAG, poemI18n.getI18nTag()))
                .and(QueryCondition.create(POEM_I18N.I18N, poemI18n.getI18n()))
                ;
        return this.updateByCondition(poemI18n,condition);
    }

    /**
     * 根据 i18nKey删除
     * @return 受影响行
     */
    default int deleteByI18nKey(String i18nKey){
        return this.deleteByQuery(QueryWrapper.create().where(POEM_I18N.I18N_KEY.eq(i18nKey)));
    }

}
