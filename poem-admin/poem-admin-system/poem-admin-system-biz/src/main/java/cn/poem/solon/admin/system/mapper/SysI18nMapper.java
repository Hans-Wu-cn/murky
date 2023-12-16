package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.dto.SysI18nDTO;
import cn.poem.solon.admin.system.domain.entity.SysI18n;
import cn.poem.solon.admin.system.domain.entity.table.SysI18nTableDef;
import cn.poem.solon.admin.system.domain.query.SysI18nPageQuery;
import cn.poem.solon.admin.system.domain.vo.SysI18nVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static com.mybatisflex.core.query.QueryMethods.count;

public interface SysI18nMapper extends BaseMapper<SysI18n> {

    /**
     * I18n分页mapper
     */
    default Page<Map> page(SysI18nPageQuery sysI18nPageQuery) {
        SysI18nDTO poemI18NDTO = sysI18nPageQuery.getSysI18nDTO();
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        QueryWrapper queryWrapper = QueryWrapper.create().from(SYS_I18N)
                .where(SYS_I18N.I18N_TAG.eq(poemI18NDTO.getI18nTag()))
                .and(SYS_I18N.I18N_KEY.like(poemI18NDTO.getI18nKey(), If::hasText))
                .and(SYS_I18N.I18N_VALUE.like(poemI18NDTO.getI18nValue(), If::hasText))
                .groupBy(SYS_I18N.I18N_KEY, SYS_I18N.I18N_TAG).orderBy(SYS_I18N.I18N_KEY.desc())
                .select(SYS_I18N.I18N_KEY, SYS_I18N.I18N_TAG);
        for (String i18nKey : sysI18nPageQuery.getI18nKeys()) {
            String sql = MessageFormat.format("STRING_AGG(case " +
                    " when language = ''{0}'' then i18n_value " +
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
    default SysI18nVo info(SysI18nPageQuery sysI18nPageQuery) {
        SysI18nDTO sysI18nDTO = sysI18nPageQuery.getSysI18nDTO();
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        QueryWrapper queryWrapper = QueryWrapper.create().from(SYS_I18N)
                .where(SYS_I18N.I18N_TAG.eq(sysI18nDTO.getI18nTag()))
                .and(SYS_I18N.I18N_KEY.eq(sysI18nDTO.getI18nKey()));

        List<SysI18n> poemI18nList = selectListByQuery(queryWrapper);
        SysI18nVo poemI18nVo = new SysI18nVo();
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
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(count(SYS_I18N.ID))
                .where(SYS_I18N.I18N_KEY.eq(i18nKey))
                .and(SYS_I18N.I18N_TAG.eq(i18nTag));
        return selectCountByQuery(queryWrapper);
    }

    /**
     * 根据i18nKey, i18nTag, i18n查询数量
     */
    default SysI18n selectByKeyAndTagAndi18n(String i18nKey, String i18nTag, String i18n) {
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(SYS_I18N.I18N_KEY.eq(i18nKey))
                .and(SYS_I18N.I18N_TAG.eq(i18nTag).and(SYS_I18N.LANGUAGE.eq(i18n))
                );
        return selectOneByQuery(queryWrapper);
    }

    /**
     * 根据i18n,i18nKey,i18nTag修改i18nValue
     *
     * @return 受影响行
     */
    default int updateI18nValue(String key, SysI18n poemI18n) {
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        return updateByQuery(poemI18n, QueryWrapper.create()
                .where(SYS_I18N.I18N_KEY.eq(key)).and(SYS_I18N.LANGUAGE.eq(poemI18n.getLanguage())))
                ;
//        QueryCondition condition = QueryCondition.create(SYS_I18N.I18N_KEY, key)
//                .and(QueryCondition.create(SYS_I18N.I18N_TAG, poemI18n.getI18nTag()))
//                .and(QueryCondition.create(SYS_I18N.I18N, poemI18n.getI18n()));
//        return this.updateByCondition(poemI18n, condition);
    }

    /**
     * 根据 i18nKey删除
     *
     * @return 受影响行
     */
    default int deleteByI18nKey(String i18nKey) {
        SysI18nTableDef SYS_I18N = SysI18nTableDef.SYS_I18N;
        return this.deleteByQuery(QueryWrapper.create().where(SYS_I18N.I18N_KEY.eq(i18nKey)));
    }

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
