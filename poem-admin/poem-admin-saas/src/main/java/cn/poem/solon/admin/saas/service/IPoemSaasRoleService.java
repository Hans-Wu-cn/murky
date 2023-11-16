package cn.poem.solon.admin.saas.service;

import cn.poem.solon.admin.saas.domain.dto.PoemSaasRoleFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasRoleVo;
import com.mybatisflex.core.service.IService;

/**
 * 商户角色service
 *
 * @author hans
 */
public interface IPoemSaasRoleService extends IService<PoemSaasRole> {


    /**
     * 修改商户角色以及商户角色菜单关系
     * @param saasRoleId 商户角色id
     * @return 商户角色视图对象，包含菜单信息
     */
    PoemSaasRoleVo info(Long saasRoleId);

    /**
     * 保存商户角色以及商户角色菜单关系
     * @param poemSaasRoleFromDTO 商户角色表单对象
     * @return 保存成功状态
     */
    boolean save(PoemSaasRoleFromDTO poemSaasRoleFromDTO);

    /**
     * 修改商户角色以及商户角色菜单关系
     * @param poemSaasRoleFromDTO 商户角色表单对象
     * @return 保存成功状态
     */
    boolean update(PoemSaasRoleFromDTO poemSaasRoleFromDTO);
}
