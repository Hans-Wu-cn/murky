package cn.murky.admin.tenant.mapper;

import cn.murky.admin.tenant.domain.entity.TenantUser;
import cn.murky.admin.tenant.domain.entity.table.TenantUserTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * TenantUserMapper
 *
 * @Author hans
 */
public interface TenantUserMapper extends BaseMapper<TenantUser> {

    /**
     * 根据账号查询数量
     * @param account 账号
     * @return 数量
     */
    default long selectCountByAccount(String account){
        TenantUserTableDef TENANT_USER =TenantUserTableDef.TENANT_USER;
        return selectCountByQuery(QueryWrapper
                .create().where(TENANT_USER.ACCOUNT.eq(account)));
    }
}
