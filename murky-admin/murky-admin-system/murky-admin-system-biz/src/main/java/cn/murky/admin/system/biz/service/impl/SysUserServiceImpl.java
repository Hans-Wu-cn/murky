package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.core.MurkyServiceImpl;
import cn.murky.admin.system.biz.domain.dto.ResetPasswordDto;
import cn.murky.admin.system.biz.domain.dto.SysUserFromDTO;
import cn.murky.admin.system.biz.domain.dto.SysUserPageDTO;
import cn.murky.admin.system.biz.domain.entity.SysUser;
import cn.murky.admin.system.biz.domain.vo.SysUserPageVo;
import cn.murky.admin.system.biz.service.ISysUserService;
import cn.murky.admin.system.biz.domain.entity.SysDeptAncestors;
import cn.murky.admin.core.utils.SecurityUtils;
import cn.murky.admin.system.biz.mapper.SysDeptAncestorsMapper;
import cn.murky.admin.system.biz.mapper.SysUserMapper;
import cn.murky.admin.system.biz.service.ISystemParameterService;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * userService
 * @author hans
 */
@Component
public class SysUserServiceImpl extends MurkyServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
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
    public SysUser info(Long userId) {
        return mapper.selectOneById(userId);
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
        PasswordRecord sysPassword = EncryptionUtil.userEncryption(defaultUserPassword);
        entity.setSalt(sysPassword.salt())
                .setPassword(sysPassword.password());

        int insert = mapper.insert(entity);
        if (insert <= 0) {
            throw new ServiceException("添加失败");
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
    public boolean update(SysUserFromDTO sysUserFromDTO) {
        SysUser entity = sysUserFromDTO.toEntity();
        int update = mapper.update(entity);
        if (update <= 0) {
            throw new ServiceException("添加失败");
        }
        return true;
    }

    /**
     * 重置用户密码
     *
     * @param resetPasswordDto@return 重置成功状态
     */
    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new ServiceException("确认密码不一致");
        }
        Long userId = resetPasswordDto.getId();
        SysUser sysUser = mapper.selectOneById(userId);
        // 校验账号是否正确
        Optional.ofNullable(sysUser).orElseThrow(() -> new ServiceException("该用户不存在"));
        // 加密获取新的密码和盐值
        PasswordRecord passwordRecord = EncryptionUtil.userEncryption(resetPasswordDto.getPassword());
        int count = mapper.resetPassword(userId, passwordRecord.password(),passwordRecord.salt());
        return count > 0;
    }

    /**
     * 重写分页方法
     */
    @Override
    public Page<SysUserPageVo> page(SysUserPageDTO sysUserPageDTO) {
        Set<Long> deptIds = sysDeptAncestorsMapper.getListByAncestors(sysUserPageDTO.getFkDeptId()).stream().map(SysDeptAncestors::getFkDeptId).collect(Collectors.toSet());
        deptIds.add(sysUserPageDTO.getFkDeptId());
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
