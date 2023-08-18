package cn.poem.solon.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 用户信息类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class UserInfo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否是超级管理员
     */
    private Boolean isAdmin = false;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录token
     */
    private String token;

    /**
     * 角色ID集合
     */
    private Set<Long> roleIds;

    /**
     * 角色code集合
     */
    private Set<String> roleCodes;

    /**
     * 权限码
     */
    private Set<String> permissions;
}
