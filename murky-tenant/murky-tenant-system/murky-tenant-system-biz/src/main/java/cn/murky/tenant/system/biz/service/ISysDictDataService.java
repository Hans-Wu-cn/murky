package cn.murky.tenant.system.biz.service;

import cn.murky.tenant.system.api.domain.bo.SysDictDataBO;
import cn.murky.tenant.system.biz.domian.entity.SysDictData;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典数据Service
 *
 * @author hans
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 获取指定标签列表
     */
    List<SysDictDataBO> getDict(String dictType);

}
