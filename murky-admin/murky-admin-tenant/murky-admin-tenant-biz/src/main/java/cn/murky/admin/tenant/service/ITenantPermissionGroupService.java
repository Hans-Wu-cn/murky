package cn.murky.admin.tenant.service;

import cn.murky.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.murky.admin.tenant.domain.vo.TenantPermissionGroupVo;
import com.mybatisflex.core.service.IService;

/**
 * 商户权限组service
 *
 * @author hans
 */
public interface ITenantPermissionGroupService extends IService<TenantPermissionGroup> {


    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    TenantPermissionGroupVo info(Long groupId);

    /**
     * 保存商户F以及商户权限组菜单关系
     * @param tenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean save(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO);

    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param tenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean update(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO);
}
