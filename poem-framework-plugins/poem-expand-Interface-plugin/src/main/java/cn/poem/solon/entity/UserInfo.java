package cn.poem.solon.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
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
    private Boolean admin = false;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录token
     */
    private String token;

    /**
     * 所属部门id
     */
    private Long deptId;

    /**
     * 权限关联部门
     */
    private Set<Long> deptIds;

    /**
     * 所属部门的数据权限
     */
    private Set<Integer> dataScope;

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

    public void addDataScope(Integer dataScope) {
        if(this.dataScope == null){
            this.dataScope=new HashSet<>();
            this.dataScope.add(dataScope);
        }
        this.dataScope.add(dataScope);
    }
}
