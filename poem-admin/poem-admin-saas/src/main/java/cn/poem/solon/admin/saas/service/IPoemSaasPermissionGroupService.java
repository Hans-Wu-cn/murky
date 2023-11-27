package cn.poem.solon.admin.saas.service;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasPermissionGroupVo;
import com.mybatisflex.core.service.IService;

/**
 * 商户权限组service
 *
 * @author hans
 */
public interface IPoemSaasPermissionGroupService extends IService<PoemSaasPermissionGroup> {


    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    PoemSaasPermissionGroupVo info(Long groupId);

    /**
     * 保存商户F以及商户权限组菜单关系
     * @param poemSaasPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean save(PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO);

    /**
     * 修改商户权限组以及商户权限组菜单关系
     * @param poemSaasPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    boolean update(PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO);
}
