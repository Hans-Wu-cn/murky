package cn.murky.tenant.core.utils;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.murky.security.SecurityCache;
import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.tenant.core.SecurityTenantUserInfo;
import org.noear.solon.Solon;

import static cn.murky.tenant.core.constant.Constants.TENANT_ID_HEADER;


public class SecurityUtils {
    static SecurityCache<SecurityTenantUserInfo> securityCache;

    static {
        Solon.context().getBeanAsync(SecurityCache.class, systemSecurityCache ->{
            securityCache=systemSecurityCache;
        });
    }

    /**
     * 获取当前登录用户ID
     * @return 用户id
     */
    public static Long getUserId(){
        return securityCache.getUserId();
    }

    /**
     * 获取当前登录租户id
     * @return 用户id
     */
    public static Long getTenantId(){
        return SaHolder.getStorage().getLong(TENANT_ID_HEADER);
    }

    /**
     * 设置当前登录租户id
     */
    public static void setTenantId(Long tenantId){
        SaHolder.getStorage().set(TENANT_ID_HEADER,tenantId);
    }

    /**
     * 删除当前登录租户id
     */
    public static void deleteTenantId(){
        SaHolder.getStorage().delete(TENANT_ID_HEADER);
    }

    /**
     * 获取当前登录用户信息
     * @return  当前登录用户信息
     */
    public static SecurityTenantUserInfo getUserInfo() throws NotLoginException {
        return securityCache.getUserInfo();
    }

    /**
     * 保存当前登录用户信息
     * @param securityUser 登录用户信息
     */
    public static void setUserInfo(SecurityTenantUserInfo securityUser){
        securityCache.setUserInfo(securityUser);
    }

    /**
     * 修改当前登录用户信息
     */
    public static void delUserInfo(){
        securityCache.delUserInfo();
    }

    public static Boolean isAdmin() throws NotLoginException {
        return securityCache.admin();
    }


}
