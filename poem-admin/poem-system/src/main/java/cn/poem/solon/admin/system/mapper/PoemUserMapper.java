package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.domin.table.PoemUserTableDef;
import cn.poem.solon.admin.system.domain.dto.PoemUserPageDTO;
import cn.poem.solon.admin.system.domain.entity.table.PoemDeptTableDef;
import cn.poem.solon.admin.system.domain.vo.PoemUserPageVo;
import cn.poem.solon.admin.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.Set;

public interface PoemUserMapper extends BaseMapper<PoemUser> {
    PoemUserTableDef POEM_USER = PoemUserTableDef.POEM_USER;
    PoemDeptTableDef POEM_DEPT = PoemDeptTableDef.POEM_DEPT;

    /**
     * 根据部门id查询用户数量
     *
     * @param deptId
     * @return
     */
    default Long getCountByDeptId(Long deptId) {
        return selectCountByQuery(QueryWrapper.create()
                .from(POEM_USER)
                .where(POEM_USER.DEPT_ID.eq(deptId))
        );
    }

    /**
     * 根据用户id,修改密码
     *
     * @param userId   用户id
     * @param password 密码
     * @return 受影响行数
     */
    default int resetPassword(Long userId, String password) {
        return this.updateByQuery(new PoemUser().setPassword(password)
                , true, QueryWrapper.create().where(
                        POEM_USER.USER_ID.eq(userId)
                ));
    }

    /**
     * 查询账号数量
     *
     * @param account 账号
     * @return 数量
     */
    default Long getCountByAccount(String account) {
        return selectCountByQuery(QueryWrapper.create()
                .from(POEM_USER)
                .where(POEM_USER.ACCOUNT.eq(account))
        );
    }

    /**
     * 用户分页
     *
     * @param poemUserPageDTO 参数
     * @return 用户分页视图对象
     */
    default Page<PoemUserPageVo> page(PoemUserPageDTO poemUserPageDTO, Set<Long> deptIds) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .leftJoin(POEM_DEPT).on(POEM_DEPT.DEPT_ID.eq(POEM_USER.DEPT_ID))
                .and(POEM_USER.DEPT_ID.in(deptIds, If::notNull))
                .and(POEM_USER.EMAIL.like(poemUserPageDTO.getEmail(), If::hasText))
                .and(POEM_USER.SEX.eq(poemUserPageDTO.getSex(), If::notNull))
                .and(POEM_USER.USER_NAME.like(poemUserPageDTO.getUserName(), If::hasText))
                .and(POEM_USER.DEPT_ID.in(deptIds, If::notNull))
                .orderBy(POEM_USER.CREATE_TIME.asc());
        return paginateAs(poemUserPageDTO.getPageNumber(), poemUserPageDTO.getPageSize(), DataScopeUtils.dataScope(queryWrapper), PoemUserPageVo.class);
    }
}
