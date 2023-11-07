package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import com.mybatisflex.core.service.IService;

/**
 * 字典类型Service
 *
 * @author hans
 */
public interface IPoemDictTypeService extends IService<PoemDictType> {

    /**
     * 刷新缓存
     */
    void refreshDict();

    /**
     * 重写修改方法
     */
    boolean edit(PoemDictType poemDictType);
}
