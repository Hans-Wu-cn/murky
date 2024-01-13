package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.flex.domin.SysUser;
import cn.murky.admin.flex.domin.table.SysUserTableDef;
import cn.murky.admin.security.utils.SecurityUtils;
import cn.murky.admin.system.biz.domain.dto.SysUserPageDTO;
import cn.murky.admin.system.biz.domain.entity.table.SysDeptTableDef;
import cn.murky.admin.system.biz.domain.vo.SysUserPageVo;
import cn.murky.admin.system.biz.contant.SystemContant;
import cn.murky.admin.flex.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.Set;

public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据部门id查询用户数量
     *
     * @param deptId
     * @return
     */
    default Long getCountByDeptId(Long deptId) {
        SysUserTableDef SYS_USER = SysUserTableDef.SYS_USER;
        return selectCountByQuery(QueryWrapper.create()
                .from(SYS_USER)
                .where(SYS_USER.FK_DEPT_ID.eq(deptId))
        );
    }

    /**
     * 根据用户id,修改密码
     *
     * @param userId   用户id
     * @param password 密码
     * @return 受影响行数
     */
    default int resetPassword(Long userId, String password, String salt) {
        SysUserTableDef SYS_USER = SysUserTableDef.SYS_USER;
        return this.updateByQuery(new SysUser().setPassword(password).setSalt(salt)
                , true, QueryWrapper.create().where(
                        SYS_USER.ID.eq(userId)
                ));
    }

    /**
     * 查询账号数量
     *
     * @param account 账号
     * @return 数量
     */
    default Long getCountByAccount(String account) {
        SysUserTableDef SYS_USER = SysUserTableDef.SYS_USER;
        return selectCountByQuery(QueryWrapper.create()
                .from(SYS_USER)
                .where(SYS_USER.ACCOUNT.eq(account))
        );
    }

    /**
     * 用户分页
     *
     * @param sysUserPageDTO 参数
     * @return 用户分页视图对象
     */
    default Page<SysUserPageVo> page(SysUserPageDTO sysUserPageDTO, Set<Long> deptIds) {
        SysDeptTableDef SYS_DEPT = SysDeptTableDef.SYS_DEPT;
        SysUserTableDef SYS_USER = SysUserTableDef.SYS_USER;
        QueryWrapper queryWrapper = QueryWrapper.create()
                .leftJoin(SYS_DEPT).on(SYS_DEPT.ID.eq(SYS_USER.FK_DEPT_ID))
                .and(SYS_USER.FK_DEPT_ID.in(deptIds, If::notNull))
                .and(SYS_USER.EMAIL.like(sysUserPageDTO.getEmail(), If::hasText))
                .and(SYS_USER.SEX.eq(sysUserPageDTO.getSex(), If::notNull))
                .and(SYS_USER.USER_NAME.like(sysUserPageDTO.getUserName(), If::hasText))
                .and(SYS_USER.FK_DEPT_ID.in(deptIds, If::notNull))
                .and(SYS_USER.ID.ne(SecurityUtils.getUserId()))
                .and(SYS_USER.ID.ne(SystemContant.ADMIN_USER_ID))
                .orderBy(SYS_USER.CREATE_TIME.asc());
        return paginateAs(sysUserPageDTO.getPageNumber(), sysUserPageDTO.getPageSize(), DataScopeUtils.dataScope(queryWrapper,SYS_USER.getTableName()), SysUserPageVo.class);
    }
}
