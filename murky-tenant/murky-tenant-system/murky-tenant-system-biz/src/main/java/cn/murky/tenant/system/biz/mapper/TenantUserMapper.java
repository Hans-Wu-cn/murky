package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.biz.domian.entity.TenantUser;
import cn.murky.tenant.system.biz.domian.entity.table.TenantUserTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

public interface TenantUserMapper extends BaseMapper<TenantUser> {


    /**
     * 根据账号查询
     * @param account 账号
     * @return
     */
    default TenantUser selectByAccount(String account){
        TenantUserTableDef TENANT_USER = TenantUserTableDef.TENANT_USER;
        return selectOneByQuery(QueryWrapper.create()
                .where(TENANT_USER.ACCOUNT.eq(account)));
    }
}
