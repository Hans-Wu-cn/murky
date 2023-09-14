package cn.poem.solon.system.service.impl;

import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.expand.PoemServiceImpl;
import cn.poem.solon.expand.SecurityUserInfo;
import cn.poem.solon.system.domain.convert.PoemUserConvert;
import cn.poem.solon.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.PoemUserRole;
import cn.poem.solon.system.domain.entity.table.PoemDeptAncestorsTableDef;
import cn.poem.solon.system.domain.entity.table.PoemRoleDeptTableDef;
import cn.poem.solon.system.domain.entity.table.PoemRoleTableDef;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.system.domain.vo.PoemUserVo;
import cn.poem.solon.system.enums.DataScope;
import cn.poem.solon.system.mapper.PoemUserMapper;
import cn.poem.solon.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.system.service.IPoemUserService;
import cn.poem.solon.utils.SecurityUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.mapstruct.ap.internal.util.Strings;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ProxyComponent;
import org.noear.solon.data.annotation.Tran;

import java.text.MessageFormat;
import java.util.*;

@Component
public class IPoemUserServiceImpl extends PoemServiceImpl<PoemUserMapper, PoemUser> implements IPoemUserService {
    @Inject
    PoemUserRoleMapper poemUserRoleMapper;

    /**
     * 根据用户id查询用户详细信息，包含角色信息
     *
     * @param userId 用户id
     * @return 用户视图对象
     */
    @Override
    public PoemUserVo info(Long userId) {
        PoemUser poemUser = mapper.selectOneById(userId);
        PoemUserVo vo = PoemUserConvert.INSTANCES.toVo(poemUser);
        List<Long> roleIds = poemUserRoleMapper.selectByUserId(userId)
                .stream().map(PoemUserRole::getRoleId).toList();
        vo.setRoleIds(roleIds);
        return vo;
    }

    /**
     * 添加用户并绑定对应的角色关系
     *
     * @param poemUserFromDTO
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean save(PoemUserFromDTO poemUserFromDTO) {
        PoemUser entity = poemUserFromDTO.toEntity();
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            throw new ServiceException("添加失败");
        }
        if (!poemUserFromDTO.getRoleIds().isEmpty()) {
            List<PoemUserRole> poemUserRoles = new ArrayList<>();
            for (Long roleId : poemUserFromDTO.getRoleIds()) {
                poemUserRoles.add(new PoemUserRole()
                        .setRoleId(roleId)
                        .setUserId(entity.getUserId())
                );
            }
            int i = poemUserRoleMapper.insertBatch(poemUserRoles);
            if (i != poemUserFromDTO.getRoleIds().size()) {
                throw new ServiceException("添加失败");
            }
        }
        return true;
    }

    /**
     * 修改用户并绑定对应的角色关系
     *
     * @param poemUserFromDTO
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean update(PoemUserFromDTO poemUserFromDTO) {
        poemUserFromDTO.setPassword(null);
        PoemUser entity = poemUserFromDTO.toEntity();
        int update = mapper.update(entity);
        if (update <= 0) {
            throw new ServiceException("添加失败");
        }
        if (!poemUserFromDTO.getRoleIds().isEmpty()) {
            poemUserRoleMapper.deleteByUserId(poemUserFromDTO.getUserId());
            List<PoemUserRole> poemUserRoles = new ArrayList<>();
            for (Long roleId : poemUserFromDTO.getRoleIds()) {
                poemUserRoles.add(new PoemUserRole()
                        .setRoleId(roleId)
                        .setUserId(entity.getUserId())
                );
            }
            int i = poemUserRoleMapper.insertBatch(poemUserRoles);
            if (i != poemUserFromDTO.getRoleIds().size()) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }

    @Override
    public boolean resetPassword(Long userId, String password) {
        int count = mapper.resetPassword(userId, password);
        return count > 0;
    }

    /**
     * <p>根据查询条件分页查询数据。
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页对象
     */
    @Override
    public Page<PoemUser> page(Page<PoemUser> page, QueryWrapper query) {
        SecurityUserInfo userInfo = SecurityUtils.getUserInfo();
        Set<DataScope> dataScopes = Optional.ofNullable(userInfo.getDataScope()).orElseGet(HashSet::new);
        PoemUserTableDef POEM_USER = PoemUserTableDef.POEM_USER;
        PoemRoleDeptTableDef POEM_ROLE_DEPT = PoemRoleDeptTableDef.POEM_ROLE_DEPT;
        for (DataScope dataScope : dataScopes) {
            if (DataScope.CUSTOMIZE.equals(dataScope)) {
                query.or(POEM_USER.DEPT_ID.in(QueryWrapper.create().from(POEM_ROLE_DEPT).where(POEM_ROLE_DEPT.ROLE_ID.in(userInfo.getRoleIds()))));
            }
            if (DataScope.DEPARTMENT_BELOW.equals(dataScope)) {
                PoemDeptAncestorsTableDef POEM_DEPT_ANCESTORS = PoemDeptAncestorsTableDef.POEM_DEPT_ANCESTORS;
                QueryWrapper chrendQuery = QueryWrapper.create().from(POEM_DEPT_ANCESTORS).where(POEM_DEPT_ANCESTORS.ANCESTORS.eq(userInfo.getDeptId()));
                query.or(POEM_USER.DEPT_ID.in(chrendQuery).or(POEM_USER.DEPT_ID.eq(userInfo.getDeptId())));
            }
            if (DataScope.DEPARTMENT.equals(dataScope)) {
                query.or(POEM_ROLE_DEPT.DEPT_ID.eq(userInfo.getDeptId()));
            }
            if (DataScope.ONESELF.equals(dataScope)) {
                query.or(POEM_USER.CREATE_USER.eq(userInfo.getUserId()));
            }
        }
        return super.page(page,query);
    }
}
