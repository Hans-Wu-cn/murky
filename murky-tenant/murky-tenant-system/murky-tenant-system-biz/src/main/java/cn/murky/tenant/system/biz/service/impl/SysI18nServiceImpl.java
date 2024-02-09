package cn.murky.tenant.system.biz.service.impl;

import cn.murky.tenant.system.biz.domian.entity.SysI18n;
import cn.murky.tenant.system.biz.mapper.SysI18nMapper;
import cn.murky.tenant.system.biz.service.ISysI18nService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;

import java.util.List;
import java.util.Map;

@Component
public class SysI18nServiceImpl extends ServiceImpl<SysI18nMapper, SysI18n> implements ISysI18nService {
    @Override
    public Map<String, String> language(String i18nTag, String laguage) {
        List<SysI18n> sysI18ns = mapper.selectByLanguageAndTag(i18nTag, laguage);
        ONode node = new ONode();
        for (SysI18n sysI18n : sysI18ns) {
            node.set(sysI18n.getI18nKey(), sysI18n.getI18nValue());
        }
        return node.toObject(Map.class);
    }
}
