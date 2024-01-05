package cn.murky.admin.system.api;


import cn.murky.admin.common.entity.SecurityUserInfo;
import cn.murky.admin.flex.domin.SysUser;
import cn.murky.admin.system.api.domian.UserProfile;
import cn.murky.admin.system.api.domian.dto.ProfileFromDTO;

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
     * 获取用户详情
     * @return
     */
    SecurityUserInfo userInfo();

    /**
     * 获取用户profile
     * @return
     */
    UserProfile getProfile(Long userId);

    /**
     * 修改用户profile
     *
     * @return
     */
    boolean setProfile(ProfileFromDTO profileFromDTO);

    /**
     * 修改密码
     *  @param oldPassword 旧密码
     *  @param password 新密码
     *  @param surePassword 确定新密码
     * @return 修改状态
     */
    boolean setPassword(String oldPassword,String password,String surePassword);

    /**
     * 设置用户语言偏好
     * @return
     */
    boolean setLanguage(String language);
}