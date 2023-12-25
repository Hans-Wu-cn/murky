package cn.poem.solon.admin.system.service;

import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import com.mybatisflex.core.service.IService;

public interface ISystemParameterService extends IService<SystemParameter> {


    /**
     * 刷新缓存
     */
    void refresh();

    /**
     * 根据参数key获取默认配置
     */
    String getDefaultByKey(String key);

    /**
     * 获取默认密码
     * @return 密码
     */
    String getDefaultUserPassword();

}
