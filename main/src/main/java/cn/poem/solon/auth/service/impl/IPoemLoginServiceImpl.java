package cn.poem.solon.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.expand.SecurityCache;
import cn.poem.solon.auth.service.IPoemLoginService;
import cn.poem.solon.system.contant.AdminContant;
import cn.poem.solon.system.domain.dto.LoginDto;
import cn.poem.solon.system.domain.entity.PoemMenu;
import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.PoemUserRole;
import cn.poem.solon.system.enums.MenuType;
import cn.poem.solon.system.mapper.PoemMenuMapper;
import cn.poem.solon.system.mapper.PoemRoleMapper;
import cn.poem.solon.system.mapper.PoemRoleMenuMapper;
import cn.poem.solon.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.system.service.IPoemMenuService;
import cn.poem.solon.system.service.IPoemRoleService;
import cn.poem.solon.system.service.IPoemUserService;
import cn.poem.solon.system.domain.entity.table.PoemUserTableDef;
import cn.poem.solon.utils.SecurityUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ProxyComponent;

import java.util.*;
import java.util.stream.Collectors;

@ProxyComponent
public class IPoemLoginServiceImpl implements IPoemLoginService {

    @Inject
    IPoemUserService iPoemUserService;

    @Inject
    PoemMenuMapper poemMenuMapper;

    @Inject
    PoemUserRoleMapper poemUserRoleMapper;

    @Inject
    PoemRoleMapper poemRoleMapper;

    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        PoemUser user = iPoemUserService.getOne(QueryWrapper.create().where(
                PoemUserTableDef.POEM_USER.ACCOUNT.eq(loginDto.getAccount())
        ).and(PoemUserTableDef.POEM_USER.PASSWORD.eq(loginDto.getPassword())));

        //如果为空抛出异常
        Optional.ofNullable(user).orElseThrow(() -> new ServiceException("账号或密码错误"));

        // 第1步，先登录上
        StpUtil.login(user.getUserId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return tokenInfo;
    }

    /**
     * 获取用户登录信息
     * @return 用户信息对象
     */
    @Override
    public UserInfo userInfo() {
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
        UserInfo userInfoCache = SecurityUtil.getUserInfo();

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            PoemUser poemUser = iPoemUserService.getById(loginId);
            UserInfo userInfo = new UserInfo().setUserId(loginId)
                    .setUserName(poemUser.getUserName())

                    .setToken(tokenInfo.getTokenValue());
            //查询角色id列表
            Set<Long> roleIds = poemUserRoleMapper.selectByUserId(poemUser.getUserId())
                    .stream().map(PoemUserRole::getRoleId)
                    .collect(Collectors.toSet());
            userInfo.setRoleIds(roleIds);
            //查询角色code列表
            List<String> roleCodes = poemRoleMapper.selectListByIds(roleIds).stream().map(item -> {
                if(AdminContant.ADMIN_ROLE_CODE.equals(item.getRoleCode())){
                    userInfo.setIsAdmin(true);
                }
                return item.getRoleCode();
            }).collect(Collectors.toList());
            userInfo.setRoleCodes(roleCodes);
            SecurityUtil.setUserInfo(userInfo);
            //查询权限列表
            List<String> permissions = poemMenuMapper.selectByMenuType(
                    Arrays.asList(MenuType.BUTTON, MenuType.MENU, MenuType.DIRECTORY)
                    , SecurityUtil.isAdmin()?null:roleIds).stream().map(PoemMenu::getAuth).collect(Collectors.toList());
            userInfo.setPermissions(permissions);
            SecurityUtil.setUserInfo(userInfo);
            return userInfo;
        });
    }

}
