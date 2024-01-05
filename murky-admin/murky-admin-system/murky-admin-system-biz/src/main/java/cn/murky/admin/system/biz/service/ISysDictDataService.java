package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.entity.SysDictData;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典数据Service
 *
 * @author hans
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 获取国际化字典内容
     * @return
     */
    List<SysDictData> getI18nDict();

    /**
     * 获取指定标签列表
     * @return
     */
    List<SysDictData> getDict(String dictType);
}
