package cn.poem.solon.admin.system.event;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.common.enums.DataScope;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.domin.tab.PoemUserTableDef;
import cn.poem.solon.admin.system.domain.entity.PoemUserRole;
import cn.poem.solon.admin.event.system.enums.MenuType;
import cn.poem.solon.admin.system.contant.SystemContant;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.mapper.PoemMenuMapper;
import cn.poem.solon.admin.system.mapper.PoemRoleMapper;
import cn.poem.solon.admin.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.admin.system.service.IPoemUserService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.dami.solon.annotation.DamiTopic;
import org.noear.solon.annotation.Inject;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserEvent 事件实现
 */
@DamiTopic("event.user")
public class PoemUserEvent {

    @Inject
    private IPoemUserService iPoemUserService;
    @Inject
    private PoemUserRoleMapper poemUserRoleMapper;
    @Inject
    private PoemRoleMapper poemRoleMapper;
    @Inject
    private PoemMenuMapper poemMenuMapper;

    public PoemUser getOneByAccount(String account){
       return iPoemUserService.getOne(QueryWrapper.create().where(
                PoemUserTableDef.POEM_USER.ACCOUNT.eq(account)
        ));
    }

    public SecurityUserInfo userInfo() throws LoginException {
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
        SecurityUserInfo userInfoCache = SecurityUtils.getUserInfo();

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            PoemUser poemUser = iPoemUserService.getById(loginId);
            SecurityUserInfo userInfo = new SecurityUserInfo().setUserId(loginId)
                    .setUserName(poemUser.getUserName())
                    .setLanguage(poemUser.getLanguage())
                    .setToken(tokenInfo.getTokenValue());
            //查询角色id列表
            Set<Long> roleIds = poemUserRoleMapper.selectByUserId(poemUser.getUserId())
                    .stream().map(PoemUserRole::getRoleId)
                    .collect(Collectors.toSet());
            userInfo.setRoleIds(roleIds);
            //设置部门信息
            userInfo.setDeptId(poemUser.getDeptId());
            //查询角色code列表
            List<PoemRole> poemRoles = poemRoleMapper.selectListByIds(roleIds);
            List<String> roleCodes = poemRoles.stream().map(item -> {
                if (SystemContant.ADMIN_ROLE_CODE.equals(item.getRoleCode())) {
                    userInfo.setAdmin(true);
                }
                return item.getRoleCode();
            }).collect(Collectors.toList());
            userInfo.setRoleCodes(roleCodes);
            //查询数据权限信息
            Set<DataScope> dataScopes = poemRoles.stream().map(PoemRole::getDataScope).collect(Collectors.toSet());
            dataScopes.forEach(userInfo::addDataScope);
//            userInfo.setDeptIds(getDeptIdByDataScope(dataScopes, userInfo.getDeptId()));
            //查询权限列表
            List<String> permissions = poemMenuMapper.selectByMenuType(
                    Arrays.asList(MenuType.BUTTON, MenuType.MENU, MenuType.DIRECTORY)
                    , userInfo.getAdmin() ? null : roleIds).stream().map(PoemMenu::getAuth).collect(Collectors.toList());
            userInfo.setPermissions(permissions);
            SecurityUtils.setUserInfo(userInfo);
            return userInfo;
        });
    }

}
