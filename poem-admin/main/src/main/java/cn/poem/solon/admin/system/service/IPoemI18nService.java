package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.dto.PoemI18nFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemI18nDTO;
import cn.poem.solon.admin.system.domain.entity.PoemI18n;
import cn.poem.solon.admin.system.domain.vo.PoemI18nVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.noear.solon.validation.annotation.NotBlank;

import java.util.Map;

/**
 * i18n Service
 *
 * @author hans
 */
public interface IPoemI18nService extends IService<PoemI18n> {

    /**
     * 根据字典和tag数据节后的动态分页
     * @param poemI18nDTO
     * @return 返回的是根据字典内容指定的Map
     */
    Page<Map> page(PoemI18nDTO poemI18nDTO);

    /**
     * 根据i18nKey查询某一个国际化编码的详情信息
     * @return 返回的是根据字典内容指定的字段已经详细信息
     */
    PoemI18nVo info(PoemI18nDTO poemI18nDTO);

    /**
     * 重载新增方法
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

    Map<String,String> language(String i18nTag,String laguage);
}
