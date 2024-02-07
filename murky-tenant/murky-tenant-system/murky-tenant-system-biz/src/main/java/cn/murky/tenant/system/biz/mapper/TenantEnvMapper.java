package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.api.enums.EnvTypeEnum;
import cn.murky.tenant.system.biz.domian.entity.TenantEnv;
import cn.murky.tenant.system.biz.domian.entity.table.TenantEnvTableDef;
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
