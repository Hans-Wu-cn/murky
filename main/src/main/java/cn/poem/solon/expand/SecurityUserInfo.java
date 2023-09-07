package cn.poem.solon.expand;

import cn.poem.entity.SecurityUser;
import cn.poem.solon.system.enums.DataScope;
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
public class SecurityUserInfo extends SecurityUser implements Serializable {

    /**
     * 是否是超级管理员
     */
    private Boolean admin = false;

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
