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

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.selectCountOne;

public interface PoemI18nMapper extends BaseMapper<PoemI18n> {
    PoemI18nTableDef POEM_I18N = PoemI18nTableDef.POEM_I18N;


    /**
     * I18n分页mapper
     */
    default Page<Map> page(PoemI18nPageQuery poemI18nPageQuery) {
        PoemI18nDTO poemI18NDTO = poemI18nPageQuery.getPoemI18nDTO();
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_I18N)
                .where(POEM_I18N.I18N_TAG.eq(poemI18NDTO.getI18nTag()))
                .and(POEM_I18N.I18N_KEY.like(poemI18NDTO.getI18nKey(), If::hasText))
                .groupBy(POEM_I18N.I18N_KEY, POEM_I18N.I18N_TAG).orderBy(POEM_I18N.I18N_KEY.desc())
                .select(POEM_I18N.I18N_KEY, POEM_I18N.I18N_TAG);
        for (String i18nKey : poemI18nPageQuery.getI18nKeys()) {
            String sql = MessageFormat.format("STRING_AGG(case " +
                    " when i18n = ''{0}'' then i18n_value " +
                    " else null " +
                    " end, " +
                    " '','' ) as \"{0}\"", i18nKey);
            queryWrapper.select(sql);
        }
        return paginateAs(poemI18NDTO.getPageNumber(), poemI18NDTO.getPageSize(), queryWrapper, Map.class);
    }


    /**
     * I18n详情数据
     */
    default PoemI18nVo info(PoemI18nPageQuery poemI18nPageQuery) {
        PoemI18nDTO poemI18NDTO = poemI18nPageQuery.getPoemI18nDTO();
        QueryWrapper queryWrapper = QueryWrapper.create().from(POEM_I18N)
                .where(POEM_I18N.I18N_TAG.eq(poemI18NDTO.getI18nTag()))
                .and(POEM_I18N.I18N_KEY.eq(poemI18NDTO.getI18nKey()));

        List<PoemI18n> poemI18nList = selectListByQuery(queryWrapper);
        PoemI18nVo poemI18nVo = new PoemI18nVo();
        if (!poemI18nList.isEmpty()) {
            poemI18nVo.setId(poemI18nList.get(0).getId())
                    .setI18nKey(poemI18nList.get(0).getI18nKey())
                    .setI18nTag(poemI18nList.get(0).getI18nTag())
                    .setI18nInputs(poemI18nList);
        }
        return poemI18nVo;
    }

    /**
     * 根据i18nKey, i18nTag查询数量
     */
    default long selectByKeyAndTag(String i18nKey, String i18nTag) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(count(POEM_I18N.ID))
                .where(POEM_I18N.I18N_KEY.eq(i18nKey))
                .and(POEM_I18N.I18N_TAG.eq(i18nTag));
        return selectCountByQuery(queryWrapper);
    }

    /**
     * 根据i18nKey, i18nTag, i18n查询数量
     */
    default PoemI18n selectByKeyAndTagAndi18n(String i18nKey, String i18nTag, String i18n) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(POEM_I18N.I18N_KEY.eq(i18nKey))
                .and(POEM_I18N.I18N_TAG.eq(i18nTag).and(POEM_I18N.I18N.eq(i18n))
                );
        return selectOneByQuery(queryWrapper);
    }

    /**
     * 根据i18n,i18nKey,i18nTag修改i18nValue
     *
     * @return 受影响行
     */
    default int updateI18nValue(String key, PoemI18n poemI18n) {
        return updateByQuery(poemI18n, QueryWrapper.create()
                .where(POEM_I18N.I18N_KEY.eq(key)).and(POEM_I18N.I18N.eq(poemI18n.getI18n())))
                ;
//        QueryCondition condition = QueryCondition.create(POEM_I18N.I18N_KEY, key)
//                .and(QueryCondition.create(POEM_I18N.I18N_TAG, poemI18n.getI18nTag()))
//                .and(QueryCondition.create(POEM_I18N.I18N, poemI18n.getI18n()));
//        return this.updateByCondition(poemI18n, condition);
    }

    /**
     * 根据 i18nKey删除
     *
     * @return 受影响行
     */
    default int deleteByI18nKey(String i18nKey) {
        return this.deleteByQuery(QueryWrapper.create().where(POEM_I18N.I18N_KEY.eq(i18nKey)));
    }

}
