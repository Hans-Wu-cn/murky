package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.core.record.PasswordRecord;
import cn.poem.solon.admin.core.utils.EncryptionUtil;
import cn.poem.solon.admin.domin.PoemDeptAncestors;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.system.domain.convert.PoemUserConvert;
import cn.poem.solon.admin.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.PoemUserPageDTO;
import cn.poem.solon.admin.event.system.entity.PoemUserRole;
import cn.poem.solon.admin.system.domain.vo.PoemUserPageVo;
import cn.poem.solon.admin.system.domain.vo.PoemUserVo;
import cn.poem.solon.admin.system.mapper.PoemDeptAncestorsMapper;
import cn.poem.solon.admin.system.mapper.PoemUserMapper;
import cn.poem.solon.admin.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.admin.system.service.IPoemUserService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class IPoemUserServiceImpl extends PoemServiceImpl<PoemUserMapper, PoemUser> implements IPoemUserService {
    @Inject
    private PoemUserRoleMapper poemUserRoleMapper;

    @Inject
    private PoemDeptAncestorsMapper poemDeptAncestorsMapper;

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
        Long countByAccount = mapper.getCountByAccount(entity.getAccount());
        if (countByAccount > 0) {
            throw new ServiceException("添加失败:账号已存在");
        }
        PasswordRecord poemPassword = EncryptionUtil.userEncryption(poemUserFromDTO.getPassword());
        entity.setSalt(poemPassword.salt())
                .setPassword(poemPassword.password());

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

    @Override
    public Page<PoemUserPageVo> page(PoemUserPageDTO poemUserPageDTO) {
        Set<Long> deptIds = poemDeptAncestorsMapper.getListByAncestors(poemUserPageDTO.getDeptId()).stream().map(PoemDeptAncestors::getDeptId).collect(Collectors.toSet());
        deptIds.add(poemUserPageDTO.getDeptId());
        return mapper.page(poemUserPageDTO, deptIds);
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
        QueryWrapper queryWrapper = super.dataScope(query, SecurityUtils.getUserInfo());
        return super.page(page, queryWrapper);
    }
}
