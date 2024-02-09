package cn.murky.tenant.system.biz.service;

import cn.murky.tenant.system.biz.domian.entity.SysI18n;
import com.mybatisflex.core.service.IService;

import java.util.Map;

public interface ISysI18nService extends IService<SysI18n> {

    /**
     * 获取语言包
     * @param i18nTag 标签
     * @param laguage 语言
     * @return 语言包数据
     */
    Map<String,String> language(String i18nTag, String laguage);
}
