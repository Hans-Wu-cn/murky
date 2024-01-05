package cn.murky.admin.system.biz.service;

import cn.murky.admin.system.biz.domain.entity.SystemParameter;
import com.mybatisflex.core.service.IService;

public interface ISystemParameterService extends IService<SystemParameter> {


    /**
     * 刷新缓存
     */
    void refresh();

    /**
     * 获取默认密码
     * @return
     */
    String getDefaultUserPassword();
}
