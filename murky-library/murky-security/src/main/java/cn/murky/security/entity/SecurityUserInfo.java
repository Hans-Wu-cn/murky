package cn.murky.security.entity;

import cn.murky.common.enums.DataScope;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理后台安全实体
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public class SecurityUserInfo implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录token
     */
    private String token;

    /**
     * 是否是超级管理员
     */
    private Boolean admin;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 语言偏好
     */
    private String language;

    /**
     * 所属部门id
     */
    private Long deptId;

    /**
     * 所属部门的数据权限
     */
    private Set<DataScope> dataScope;

    /**
     * 角色ID集合
     */
    private Set<Long> roleIds;

    /**
     * 角色code集合
     */
    private List<String> roleCodes;

    /**
     * 权限码
     */
    private List<String> permissions;

    public void addDataScope(DataScope dataScope) {
        if(this.dataScope == null){
            this.dataScope=new HashSet<>();
            this.dataScope.add(dataScope);
        }
        this.dataScope.add(dataScope);
    }
}
