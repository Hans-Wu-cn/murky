package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.dto.SysI18nFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysI18nVo;
import cn.murky.admin.system.biz.domain.dto.SysI18nDTO;
import cn.murky.admin.system.biz.domain.entity.SysI18n;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.Map;

/**
 * i18n Service
 *
 * @author hans
 */
public interface ISys18nService extends IService<SysI18n> {

    /**
     * 根据字典和tag数据节后的动态分页
     * @param sysI18nDTO
     * @return 返回的是根据字典内容指定的Map
     */
    Page<Map> page(SysI18nDTO sysI18nDTO);

    /**
     * 根据i18nKey查询某一个国际化编码的详情信息
     * @return 返回的是根据字典内容指定的字段已经详细信息
     */
    SysI18nVo info(SysI18nDTO sysI18nDTO);

    /**
     * 重载新增方法
     * @return 保存状态
     */
    boolean save(SysI18nFromDTO sysI18nFromDTO);

    /**
     * 重写修改方法
     * @return 保存状态
     */
    boolean edit(SysI18nFromDTO sysI18nFromDTO);

    /**
     * 重写删除方法
     * @return 保存状态
     */
    boolean remove(String i18nKey);

    /**
     * 获取语言包
     * @param i18nTag 标签
     * @param laguage 语言
     * @return 语言包数据
     */
    Map<String,String> language(String i18nTag,String laguage);
}
