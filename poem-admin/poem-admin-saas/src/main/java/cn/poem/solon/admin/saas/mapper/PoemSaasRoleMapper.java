package cn.poem.solon.admin.saas.mapper;

import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import cn.poem.solon.admin.saas.domain.entity.table.PoemSaasRoleTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * 商户角色mapper
 */
public interface PoemSaasRoleMapper extends BaseMapper<PoemSaasRole> {
    PoemSaasRoleTableDef POEM_SAAS_ROLE = PoemSaasRoleTableDef.POEM_SAAS_ROLE;


    /**
     * 根据商户角色码和商户角色名查询数量
     *
     * @return 返回数量
     */
    default PoemSaasRole selectByRoleNameAndRoleCode(String name, String code) {
        return this.selectOneByQuery(QueryWrapper.create()
                .from(POEM_SAAS_ROLE)
                .where(POEM_SAAS_ROLE.SAAS_ROLE_CODE.eq(code)
                ).or(POEM_SAAS_ROLE.SAAS_ROLE_NAME.eq(name)).limit(1)
        );
    }

    /**
     * 根据角色名称或角色码查询角色信息
     *
     * @param saasRoleId 商户角色id
     * @param saasName   商户角色名称
     * @param saasCode   商户角色码
     * @return 符合条件的角色集合
     */
    default PoemSaasRole selectByNameOrCode(Long saasRoleId, String saasName, String saasCode) {
        return this.selectOneByQuery(QueryWrapper.create().where(
                POEM_SAAS_ROLE.SAAS_ROLE_ID.ne(saasRoleId, If::notNull)
        ).and(POEM_SAAS_ROLE.SAAS_ROLE_CODE.eq(saasCode).or(POEM_SAAS_ROLE.SAAS_ROLE_NAME.eq(saasName))).limit(1));
    }
}
