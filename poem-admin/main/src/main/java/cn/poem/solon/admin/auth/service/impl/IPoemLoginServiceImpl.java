package cn.poem.solon.admin.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.domin.PoemUser;
import cn.poem.solon.admin.domin.tab.PoemUserTableDef;
import cn.poem.solon.admin.common.entity.SecurityUserInfo;
import cn.poem.solon.admin.common.enums.DataScope;
import cn.poem.solon.admin.system.contant.AdminContant;
import cn.poem.solon.admin.system.domain.dto.LoginDto;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.domain.entity.PoemUserRole;
import cn.poem.solon.admin.system.enums.MenuType;
import cn.poem.solon.admin.system.mapper.PoemDeptMapper;
import cn.poem.solon.admin.system.mapper.PoemMenuMapper;
import cn.poem.solon.admin.system.mapper.PoemRoleMapper;
import cn.poem.solon.admin.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.admin.system.service.IPoemUserService;
import cn.poem.solon.admin.auth.service.IPoemLoginService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class IPoemLoginServiceImpl implements IPoemLoginService {

    @Inject
    private IPoemUserService iPoemUserService;

    @Inject
    private PoemMenuMapper poemMenuMapper;

    @Inject
    private PoemUserRoleMapper poemUserRoleMapper;

    @Inject
    private PoemRoleMapper poemRoleMapper;

    @Inject
    private PoemDeptMapper poemDeptMapper;

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
     *
     * @return 用户信息对象
     */
    @Override
    public SecurityUserInfo userInfo() {
        //判断缓存中是否有，如果有则从缓存中取数据，如果没有则从数据库查询
        SecurityUserInfo userInfoCache = SecurityUtils.getUserInfo();

        return Optional.ofNullable(userInfoCache).orElseGet(() -> {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            Long loginId = StpUtil.getLoginIdAsLong();
            PoemUser poemUser = iPoemUserService.getById(loginId);
            SecurityUserInfo userInfo = (SecurityUserInfo) new SecurityUserInfo().setUserId(loginId)
                    .setUserName(poemUser.getUserName())
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
                if (AdminContant.ADMIN_ROLE_CODE.equals(item.getRoleCode())) {
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

    /**
     * 根据数据权限查询所有所属部门
     *
     * @param dataScopes 数据权限
     * @param deptId     当前用户所属部门id
     */
    private Set<Long> getDeptIdByDataScope(Collection<DataScope> dataScopes, Long deptId) {
        Set<Long> deptIds = new HashSet<>();
        for (DataScope dataScope : dataScopes) {
            switch (dataScope) {
                case CUSTOMIZE -> {
                    //todo 需要查询角色配置的部门权限
                }
                case DEPARTMENT_BELOW -> {
                    //todo 需要查询部门以及下级部门

                }
                case DEPARTMENT -> {
                    deptIds.add(deptId);
                }
            }
        }
        return deptIds;
    }

}
