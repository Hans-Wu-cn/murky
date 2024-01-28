package cn.murky.tenant.system.biz.domian.entity;

import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色菜单关系实体类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
@ApiModel("角色菜单关系实体类")
@Table(value = "tenant_role_menu")
public class TenantRoleMenu {

    /**
     * 角色id
     */
    private Long fkRoleId;

    /**
     * 菜单id
     */
    private Long fkMenuId;
}
