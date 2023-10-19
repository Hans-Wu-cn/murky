package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.core.util.SqlUtil;

import java.util.List;

/**
 * 字典数据Service
 *
 * @author hans
 */
public interface IPoemDictDataService extends IService<PoemDictData> {

    /**
     * 获取国际化字典内容
     * @return
     */
    List<PoemDictData> getI18nDict();
}
