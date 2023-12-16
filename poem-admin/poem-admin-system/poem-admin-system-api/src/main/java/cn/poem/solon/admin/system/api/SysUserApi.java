package cn.poem.solon.admin.system.api;


import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.domin.SysUser;

/**
 * 用户Api
 */
public interface SysUserApi {
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    SysUser getOneByAccount(String account);

    /**
     * 获取用户详情事件
     * @return
     */
    SecurityUserInfo userInfo();

    /**
     * 设置用户语言偏好
     * @return
     */
    boolean setLanguage(String language);
}