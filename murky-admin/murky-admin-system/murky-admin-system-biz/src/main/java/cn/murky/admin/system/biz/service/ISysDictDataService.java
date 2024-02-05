package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.api.domian.bo.SysDictDataBO;
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
     */
    List<SysDictDataBO> getI18nDict();

    /**
     * 获取指定标签列表
     */
    List<SysDictDataBO> getDict(String dictType);

    /**
     * 获取所有字典
     */
    List<SysDictDataBO> getAllDict();

    /**
     * 修改字典
     * @return 修改状态
     */
    boolean updateDict(SysDictData sysDictData);

    /**
     * 新增字典
     * @return 新增状态
     */
    boolean addDict(SysDictData sysDictData);

    /**
     * 删除字典
     * @return 删除状态
     */
    boolean removeDict(Long id);
}
