package cn.poem.solon.admin.saas.mapper;

import cn.poem.solon.admin.saas.domain.entity.PoemTenantUser;
import cn.poem.solon.admin.saas.domain.entity.table.PoemTenantUserTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * PoemTenantUser Mapper
 *
 * @Author hans
 */
public interface PoemTenantUserMapper extends BaseMapper<PoemTenantUser> {
    PoemTenantUserTableDef POEM_TENANT_USER=PoemTenantUserTableDef.POEM_TENANT_USER;

    /**
     * 根据账号查询数量
     * @param account 账号
     * @return 数量
     */
    default long selectCountByAccount(String account){
        return selectCountByQuery(QueryWrapper
                .create().where(POEM_TENANT_USER.ACCOUNT.eq(account)));
    }
}
