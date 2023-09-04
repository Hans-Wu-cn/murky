package cn.poem;


import cn.poem.entity.SecurityUser;

/**
 * 用户信息缓存接口
 *
 * @author hans
 */
public interface SecurityCache<T extends SecurityUser>  {
    /**
     * 缓存用户信息
     * @param securityUser 用户信息对象
     */
    void setUserInfo(T securityUser);

    /**
     * 获取缓存中的用户信息
     * @return 用户信息对象
     */
    T getUserInfo();

    /**
     * 删除用户信息
     */
    void delUserInfo();

    /**
     * 获取用户id
     * @return 返回用户id
     */
    Long getUserId();


}
