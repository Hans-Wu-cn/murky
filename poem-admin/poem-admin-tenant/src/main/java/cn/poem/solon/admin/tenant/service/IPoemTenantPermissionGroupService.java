package cn.poem.solon.admin.tenant.service;

import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantPermissionGroupVo;
import com.mybatisflex.core.service.IService;

/**
 * 商户权限组service
 *
 * @author hans
 */
public interface IPoemTenantPermissionGroupService extends IService<PoemTenantPermissionGroup> {


    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    PoemTenantPermissionGroupVo info(Long groupId);

    /**
     * 保存商户F以及商户权限组菜单关系
     * @param poemTenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean save(PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO);

    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param poemTenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean update(PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO);
}
