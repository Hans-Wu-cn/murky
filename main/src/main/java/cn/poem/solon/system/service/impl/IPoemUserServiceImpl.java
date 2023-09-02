package cn.poem.solon.system.service.impl;

import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.system.domain.convert.PoemUserConvert;
import cn.poem.solon.system.domain.dto.PoemUserFromDTO;
import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.domain.entity.PoemUserRole;
import cn.poem.solon.system.domain.vo.PoemUserVo;
import cn.poem.solon.system.mapper.PoemUserMapper;
import cn.poem.solon.system.mapper.PoemUserRoleMapper;
import cn.poem.solon.system.service.IPoemUserService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ProxyComponent;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;

@Component
public class IPoemUserServiceImpl extends ServiceImpl<PoemUserMapper, PoemUser> implements IPoemUserService {
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
}
