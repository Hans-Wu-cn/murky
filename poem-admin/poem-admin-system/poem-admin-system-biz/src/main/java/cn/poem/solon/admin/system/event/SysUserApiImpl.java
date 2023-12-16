package cn.poem.solon.admin.system.event;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.common.enums.DataScope;
import cn.poem.solon.admin.domin.SysUser;
import cn.poem.solon.admin.domin.tab.SysUserTableDef;
import cn.poem.solon.admin.security.utils.SecurityUtils;
import cn.poem.solon.admin.system.api.SysUserApi;
import cn.poem.solon.admin.system.api.enums.MenuType;
import cn.poem.solon.admin.system.contant.SystemContant;
import cn.poem.solon.admin.system.domain.entity.SysDictData;
import cn.poem.solon.admin.system.domain.entity.SysMenu;
import cn.poem.solon.admin.system.domain.entity.SysRole;
import cn.poem.solon.admin.system.domain.entity.SysUserRole;
import cn.poem.solon.admin.system.mapper.SysMenuMapper;
import cn.poem.solon.admin.system.mapper.SysRoleMapper;
import cn.poem.solon.admin.system.mapper.SysUserRoleMapper;
import cn.poem.solon.admin.system.service.ISysDictDataService;
import cn.poem.solon.admin.system.service.ISysUserService;
import cn.poem.solon.exception.ServiceException;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserApi
 */
@Component
public class SysUserApiImpl implements SysUserApi {

    @Inject
    private ISysUserService iSysUserService;
    @Inject
    private SysUserRoleMapper sysUserRoleMapper;
    @Inject
    private SysRoleMapper sysRoleMapper;
    @Inject
    private SysMenuMapper SysMenuMapper;
    @Inject
    private ISysDictDataService iSysDictDataService;

    /**
     * 根据账号查询用户
     * @param account 账号
     * @return 用户
     */
    @Override
    public SysUser getOneByAccount(String account){
       return iSysUserService.getOne(QueryWrapper.create().where(
                SysUserTableDef.SYS_USER.ACCOUNT.eq(account)
        ));
    }

    /**
     * 获取用户详情事件
     * @return 用户详情信息
     */
    @Override
    public SecurityUserInfo userInfo(){
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
        SecurityUserInfo userInfoCache = SecurityUtils.getUserInfo();

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            SysUser poemUser = iSysUserService.getById(loginId);
            SecurityUserInfo userInfo = new SecurityUserInfo().setUserId(loginId)
                    .setUserName(poemUser.getUserName())
                    .setLanguage(poemUser.getLanguage())
                    .setDeptId(poemUser.getDeptId())
                    .setToken(tokenInfo.getTokenValue());
            //查询角色id列表
            Set<Long> roleIds = sysUserRoleMapper.selectByUserId(poemUser.getUserId())
                    .stream().map(SysUserRole::getRoleId)
                    .collect(Collectors.toSet());
            userInfo.setRoleIds(roleIds);
            //查询角色code列表
            List<SysRole> poemRoles = sysRoleMapper.selectListByIds(roleIds);
            List<String> roleCodes = poemRoles.stream().map(item -> {
                if (SystemContant.ADMIN_ROLE_CODE.equals(item.getRoleCode())) {
                    userInfo.setAdmin(true);
                }
                return item.getRoleCode();
            }).collect(Collectors.toList());
            userInfo.setRoleCodes(roleCodes);
            //查询数据权限信息
            Set<DataScope> dataScopes = poemRoles.stream().map(SysRole::getDataScope).collect(Collectors.toSet());
            dataScopes.forEach(userInfo::addDataScope);
            //查询权限列表
            List<String> permissions = SysMenuMapper.selectByMenuType(
                    Arrays.asList(MenuType.BUTTON, MenuType.MENU, MenuType.DIRECTORY)
                    , userInfo.getAdmin() ? null : roleIds).stream().map(SysMenu::getAuth).collect(Collectors.toList());
            userInfo.setPermissions(permissions);
            SecurityUtils.setUserInfo(userInfo);
            return userInfo;
        });
    }

    /**
     * 设置用户语言偏好
     * @return true:设置成功  false:设置失败
     */
    @Override
    public boolean setLanguage(String language){
        List<SysDictData> list = iSysDictDataService.getI18nDict().stream().filter(item -> item.getDictValue().equals(language)).toList();
        if(Utils.isEmpty(list)){
            throw new ServiceException("系统暂不支持该语言");
        }
        SysUser poemUser = new SysUser().setUserId(SecurityUtils.getUserId()).setLanguage(language);
        boolean b = iSysUserService.updateById(poemUser);
        if(b){
            SecurityUserInfo userInfo = SecurityUtils.getUserInfo();
            userInfo.setLanguage(language);
            SecurityUtils.setUserInfo(userInfo);
            return true;
        }
        return false;
    }
}
