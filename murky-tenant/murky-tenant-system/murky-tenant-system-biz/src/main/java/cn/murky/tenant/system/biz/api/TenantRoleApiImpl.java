package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantRoleApi;
import cn.murky.tenant.system.api.domain.bo.SysRoleBO;
import cn.murky.tenant.system.biz.convert.SysRoleConvert;
import cn.murky.tenant.system.biz.domian.entity.SysRole;
import cn.murky.tenant.system.biz.mapper.SysRoleMapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

/**
 * 租户角色API
 */
@Component
public class TenantRoleApiImpl implements TenantRoleApi {
    @Inject
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysRoleBO getSysRoleById(Long fkRoleId){
        SysRole sysRole = sysRoleMapper.selectOneById(fkRoleId);
        return SysRoleConvert.INSTANCES.toBO(sysRole);

    }
}
