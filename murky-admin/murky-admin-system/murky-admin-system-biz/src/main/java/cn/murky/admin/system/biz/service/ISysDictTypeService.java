package cn.murky.admin.system.biz.service;

import cn.murky.common.domain.bo.SysDictBO;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典类型Service
 *
 * @author hans
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 刷新缓存
     */
    List<SysDictBO> refreshDict();

    /**
     * 重写修改方法
     */
    boolean edit(SysDictType sysDictType);
}
