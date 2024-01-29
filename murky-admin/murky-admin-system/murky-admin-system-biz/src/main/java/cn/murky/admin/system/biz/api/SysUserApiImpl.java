package cn.murky.admin.system.biz.api;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.murky.admin.system.api.domian.bo.SysUserBO;
import cn.murky.admin.system.biz.contant.SystemContant;
import cn.murky.admin.system.biz.convert.SysUserConvert;
import cn.murky.admin.system.biz.domain.entity.table.SysUserTableDef;
import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.admin.system.api.SysUserApi;
import cn.murky.admin.system.biz.domain.dto.ResetPasswordDto;
import cn.murky.admin.system.biz.domain.entity.*;
import cn.murky.admin.system.biz.domain.entity.SysDeptAncestors;
import cn.murky.admin.system.api.domian.UserProfile;
import cn.murky.admin.system.api.domian.dto.ProfileFromDTO;
import cn.murky.admin.system.api.enums.MenuType;
import cn.murky.admin.system.biz.mapper.SysDeptAncestorsMapper;
import cn.murky.admin.system.biz.mapper.SysMenuMapper;
import cn.murky.admin.system.biz.mapper.SysRoleMapper;
import cn.murky.admin.system.biz.service.ISysDeptService;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.admin.system.biz.service.ISysUserService;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import cn.murky.admin.core.utils.SecurityUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * UserApi
 */
@Component
public class SysUserApiImpl implements SysUserApi {

    @Inject
    private ISysUserService iSysUserService;
    @Inject
    private SysRoleMapper sysRoleMapper;
    @Inject
    private SysMenuMapper SysMenuMapper;
    @Inject
    private ISysDictDataService iSysDictDataService;
    @Inject
    private SysDeptAncestorsMapper sysDeptAncestorsMapper;
    @Inject
    private ISysDeptService iSysDeptService;

    /**
     * 根据账号查询用户
     *
     * @param account 账号
     * @return 用户
     */
    @Override
    public SysUserBO getOneByAccount(String account) {
        return SysUserConvert.INSTANCES.toBO(iSysUserService.getOne(QueryWrapper.create().where(
                SysUserTableDef.SYS_USER.ACCOUNT.eq(account)
        )));
    }

    /**
     * 获取用户详情事件
     *
     * @return 用户详情信息
     */
    @Override
    public SecurityUserInfo userInfo() {
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
        SecurityUserInfo userInfoCache = SecurityUtils.getUserInfo();

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            SysUser sysUser = iSysUserService.getById(loginId);
            SecurityUserInfo userInfo = new SecurityUserInfo().setUserId(loginId)
                    .setEmail(sysUser.getEmail())
                    .setUserName(sysUser.getUserName())
                    .setLanguage(sysUser.getLanguage())
                    .setDeptId(sysUser.getFkDeptId())
                    .setFkRoleId(sysUser.getFkRoleId())
                    .setToken(tokenInfo.getTokenValue());
            //查询角色code列表
            SysRole sysRole = sysRoleMapper.selectOneById(sysUser.getFkRoleId());
            userInfo.setAdmin(SystemContant.ADMIN_ROLE_CODE.equals(sysRole.getRoleCode()));
            userInfo.setRoleCode(sysRole.getRoleCode());
            //查询数据权限信息
            userInfo.setDataScope(sysRole.getDataScope());
            //查询权限列表
            List<String> permissions = SysMenuMapper.selectByMenuType(
                    Arrays.asList(MenuType.BUTTON, MenuType.MENU, MenuType.DIRECTORY)
                    , userInfo.getAdmin() ? null : sysRole.getId())
                    .stream().map(SysMenu::getAuth).toList();
            userInfo.setPermissions(permissions);
            SecurityUtils.setUserInfo(userInfo);
            return userInfo;
        });
    }

    @Override
    public UserProfile getProfile(Long userId) {
        SysUser sysUser = iSysUserService.getById(userId);
        SysRole sysRole = sysRoleMapper.selectOneById(sysUser.getFkRoleId());
        List<Long> deptIds = new java.util.ArrayList<>(sysDeptAncestorsMapper.getListByDeptId(sysUser.getFkDeptId()).stream().map(SysDeptAncestors::getAncestors).toList());
        deptIds.add(sysUser.getFkDeptId());
        List<String> deptNameList = iSysDeptService.listByIds(deptIds).stream().map(SysDept::getDeptName).toList();
        return new UserProfile()
                .setUserName(sysUser.getUserName())
                .setSex(sysUser.getSex())
                .setEmail(sysUser.getEmail())
                .setCreateTime(sysUser.getCreateTime())
                .setRoleName(sysRole.getRoleName())
                .setDeptNameList(deptNameList)
                ;
    }

    @Override
    public boolean setProfile(ProfileFromDTO profileFromDTO) {
        SysUser sysUser = iSysUserService.getById(SecurityUtils.getUserId());
        return sysUser.setUserName(profileFromDTO.getUserName())
                .setEmail(profileFromDTO.getEmail())
                .setSex(profileFromDTO.getSex()).updateById();
    }

    /**
     * 修改密码
     *  @param oldPassword 旧密码
     *  @param password 新密码
     *  @param surePassword 确定新密码
     * @return 修改状态
     */
    @Override
    public boolean setPassword(String oldPassword, String password, String surePassword) {
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = iSysUserService.getById(userId);
        String encryPassword = EncryptionUtil.userEncryption(new PasswordRecord(sysUser.getSalt(), oldPassword));
        if(!sysUser.getPassword().equals(encryPassword)){
            throw new ServiceException("旧密码不正确");
        }
        return iSysUserService.resetPassword(new ResetPasswordDto()
                .setPassword(password)
                .setConfirmPassword(surePassword)
                .setId(userId));
    }

    /**
     * 设置用户语言偏好
     *
     * @return true:设置成功  false:设置失败
     */
    @Override
    public boolean setLanguage(String language) {
        List<SysDictData> list = iSysDictDataService.getI18nDict().stream().filter(item -> item.getDictValue().equals(language)).toList();
        if (Utils.isEmpty(list)) {
            throw new ServiceException("系统暂不支持该语言");
        }
        SysUser sysUser = new SysUser().setId(SecurityUtils.getUserId()).setLanguage(language);
        boolean b = iSysUserService.updateById(sysUser);
        if (b) {
            SecurityUserInfo userInfo = SecurityUtils.getUserInfo();
            userInfo.setLanguage(language);
            SecurityUtils.setUserInfo(userInfo);
            return true;
        }
        return false;
    }
}
