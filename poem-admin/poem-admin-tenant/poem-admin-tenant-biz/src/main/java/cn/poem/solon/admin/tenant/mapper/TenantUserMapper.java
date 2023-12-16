package cn.poem.solon.admin.tenant.mapper;

import cn.poem.solon.admin.tenant.domain.entity.TenantUser;
import cn.poem.solon.admin.tenant.domain.entity.table.TenantUserTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * PoemTenantUser Mapper
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
