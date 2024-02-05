package cn.murky.admin.tenant.mapper;

import cn.murky.admin.tenant.api.enums.EnvTypeEnum;
import cn.murky.admin.tenant.domain.entity.TenantEnv;
import cn.murky.admin.tenant.domain.entity.table.TenantEnvTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

public interface TenantEnvMapper extends BaseMapper<TenantEnv> {

    /**
     * 根据环境配置类型搜索
     * @param envTypeEnum 环境配置类型
     */
    default List<TenantEnv> selectByEnvType(EnvTypeEnum envTypeEnum){
        TenantEnvTableDef TENANT_ENV = TenantEnvTableDef.TENANT_ENV;
        return selectListByQuery(QueryWrapper.create().
                where(TENANT_ENV.ENV_TYPE.eq(envTypeEnum)));
    }

}
