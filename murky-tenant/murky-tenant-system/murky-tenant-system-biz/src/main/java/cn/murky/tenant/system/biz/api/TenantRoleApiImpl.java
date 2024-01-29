package cn.murky.tenant.system.biz.api;

import cn.murky.tenant.system.api.TenantRoleApi;
import cn.murky.tenant.system.api.domain.bo.SysRoleBO;
import cn.murky.tenant.system.biz.convert.SysRoleConvert;
import cn.murky.tenant.system.biz.domian.entity.SysRole;
import org.noear.solon.annotation.Component;

/**
 * 租户角色API
 */
@Component
public class TenantRoleApiImpl implements TenantRoleApi {

    @Override
    public SysRoleBO getSysRoleById(Long fkRoleId){
        SysRole sysRole = new SysRole().where(SysRole::getId).eq(fkRoleId).oneById();
        return SysRoleConvert.INSTANCES.toBO(sysRole);

    }
}
