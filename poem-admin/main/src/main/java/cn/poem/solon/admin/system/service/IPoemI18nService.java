package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.dto.PoemI18nFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemI18nPageDTO;
import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import cn.poem.solon.admin.system.domain.query.PoemI18nPageQuery;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.Map;

/**
 * i18n Service
 *
 * @author hans
 */
public interface IPoemI18nService extends IService<PoemI18n> {

    /**
     * 根据字典和tag数据节后的动态分页
     * @param poemI18nPageDTO
     * @return 返回的是根据字典内容指定的Map
     */
    Page<Map<String,String>> page(PoemI18nPageDTO poemI18nPageDTO);

    /**
     * 重写新增方法
     * @return 保存状态
     */
    boolean save(PoemI18nFromDTO poemI18nFromDTO);

    /**
     * 重写修改方法
     * @return 保存状态
     */
    boolean edit(PoemI18nFromDTO poemI18nFromDTO);

    /**
     * 重写删除方法
     * @return 保存状态
     */
    boolean remove(String i18nKey);
}
