package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.domin.SysDeptAncestors;
import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.admin.system.domain.convert.SysUserConvert;
import cn.poem.solon.admin.system.domain.dto.SysUserFromDTO;
import cn.poem.solon.admin.system.domain.dto.SysUserPageDTO;
import cn.poem.solon.admin.system.domain.entity.SysUserRole;
import cn.poem.solon.admin.system.domain.vo.SysUserPageVo;
import cn.poem.solon.admin.system.domain.vo.SysUserVo;
import cn.poem.solon.admin.system.mapper.SysDeptAncestorsMapper;
import cn.poem.solon.admin.system.mapper.SysUserMapper;
import cn.poem.solon.admin.system.mapper.SysUserRoleMapper;
import cn.poem.solon.admin.system.service.ISysUserService;
import cn.poem.solon.admin.system.service.ISystemParameterService;
import cn.poem.solon.exception.ServiceException;
import cn.poem.solon.record.PasswordRecord;
import cn.poem.solon.utils.EncryptionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * userService
 * @author hans
 */
@Component
public class ISysUserServiceImpl extends PoemServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Inject
    private SysUserRoleMapper sysUserRoleMapper;

    @Inject
    private SysDeptAncestorsMapper sysDeptAncestorsMapper;

    @Inject
    private ISystemParameterService iSystemParameterService;

    /**
     * 根据用户id查询用户详细信息，包含角色信息
     *
     * @param userId 用户id
     * @return 用户视图对象
     */
    @Override
    public SysUserVo info(Long userId) {
        SysUser poemUser = mapper.selectOneById(userId);
        SysUserVo vo = SysUserConvert.INSTANCES.toVo(poemUser);
        List<Long> roleIds = sysUserRoleMapper.selectByUserId(userId)
                .stream().map(SysUserRole::getRoleId).toList();
        vo.setRoleIds(roleIds);
        return vo;
    }

    /**
     * 添加用户并绑定对应的角色关系
     *
     * @param sysUserFromDTO
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean save(SysUserFromDTO sysUserFromDTO) {
        SysUser entity = sysUserFromDTO.toEntity();
        Long countByAccount = mapper.getCountByAccount(entity.getAccount());
        if (countByAccount > 0) {
            throw new ServiceException("添加失败:账号已存在");
        }
        // 获取系统默认密码
        String defaultUserPassword = iSystemParameterService.getDefaultUserPassword();
        // 加密
        PasswordRecord poemPassword = EncryptionUtil.userEncryption(defaultUserPassword);
        entity.setSalt(poemPassword.salt())
                .setPassword(poemPassword.password());

        int insert = mapper.insert(entity);
        if (insert <= 0) {
            throw new ServiceException("添加失败");
        }
        if (!sysUserFromDTO.getRoleIds().isEmpty()) {
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            for (Long roleId : sysUserFromDTO.getRoleIds()) {
                sysUserRoles.add(new SysUserRole()
                        .setRoleId(roleId)
                        .setUserId(entity.getUserId())
                );
            }
            int i = sysUserRoleMapper.insertBatch(sysUserRoles);
            if (i != sysUserFromDTO.getRoleIds().size()) {
                throw new ServiceException("添加失败");
            }
        }
        return true;
    }

    /**
     * 修改用户并绑定对应的角色关系
     *
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean update(SysUserFromDTO poemUserFromDTO) {
        SysUser entity = poemUserFromDTO.toEntity();
        int update = mapper.update(entity);
        if (update <= 0) {
            throw new ServiceException("添加失败");
        }
        if (!poemUserFromDTO.getRoleIds().isEmpty()) {
            sysUserRoleMapper.deleteByUserId(poemUserFromDTO.getUserId());
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            for (Long roleId : poemUserFromDTO.getRoleIds()) {
                sysUserRoles.add(new SysUserRole()
                        .setRoleId(roleId)
                        .setUserId(entity.getUserId())
                );
            }
            int i = sysUserRoleMapper.insertBatch(sysUserRoles);
            if (i != poemUserFromDTO.getRoleIds().size()) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }

    /**
     * 重置用户密码
     * @param userId 用户id
     * @param password 密码
     * @return 重置成功状态
     */
    @Override
    public boolean resetPassword(Long userId, String password) {
        SysUser poemUser = mapper.selectOneById(userId);
        // 校验账号是否正确
        Optional.ofNullable(poemUser).orElseThrow(() -> new ServiceException("该用户不存在"));
        // 加密获取新的密码和盐值
        PasswordRecord passwordRecord = EncryptionUtil.userEncryption(password);
        int count = mapper.resetPassword(userId, passwordRecord.password(),passwordRecord.salt());
        return count > 0;
    }

    /**
     * 重写分页方法
     */
    @Override
    public Page<SysUserPageVo> page(SysUserPageDTO sysUserPageDTO) {
        Set<Long> deptIds = sysDeptAncestorsMapper.getListByAncestors(sysUserPageDTO.getDeptId()).stream().map(SysDeptAncestors::getDeptId).collect(Collectors.toSet());
        deptIds.add(sysUserPageDTO.getDeptId());
        return mapper.page(sysUserPageDTO, deptIds);
    }

    /**
     * <p>根据查询条件分页查询数据。
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页对象
     */
    @Override
    public Page<SysUser> page(Page<SysUser> page, QueryWrapper query) {
        QueryWrapper queryWrapper = super.dataScope(query, SecurityUtils.getUserInfo());
        return super.page(page, queryWrapper);
    }
}
