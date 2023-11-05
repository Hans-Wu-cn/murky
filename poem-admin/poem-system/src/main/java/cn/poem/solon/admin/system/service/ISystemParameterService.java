package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import com.mybatisflex.core.service.IService;

public interface ISystemParameterService extends IService<SystemParameter> {


    /**
     * 刷新缓存
     */
    void refresh();

    String getDefaultUserPassword();
}
