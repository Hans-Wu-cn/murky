package cn.poem.solon.system.service.impl;

import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.core.utils.CollectionUtils;
import cn.poem.solon.system.domain.convert.PoemRoleConvert;
import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.system.domain.entity.PoemRoleMenu;
import cn.poem.solon.system.domain.vo.PoemRoleVo;
import cn.poem.solon.system.mapper.PoemRoleMapper;
import cn.poem.solon.system.mapper.PoemRoleMenuMapper;
import cn.poem.solon.system.service.IPoemRoleService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ProxyComponent;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 角色service
 *
 * @author hans
 */
@Component
public class IPoemRoleServiceImpl extends ServiceImpl<PoemRoleMapper, PoemRole> implements IPoemRoleService {

    @Inject
    PoemRoleMenuMapper poemRoleMenuMapper;

    /**
     * 修改角色以及角色菜单关系
     *
     * @param roleId 角色id
     * @return 角色视图对象，包含菜单信息
     */
    @Override
    public PoemRoleVo info(Long roleId) {
        PoemRole poemRole = mapper.selectOneById(roleId);
        if (poemRole == null) {
            return null;
        }
        PoemRoleVo vo = PoemRoleConvert.INSTANCES.toVo(poemRole);
        List<Long> menuIds = poemRoleMenuMapper.selectByRoleId(roleId).stream().map(PoemRoleMenu::getMenuId).toList();
        vo.setMenuIds(menuIds);
        return vo;
    }

    /**
     * 保存角色以及角色菜单关系
     *
     * @param poemRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean save(PoemRoleFromDTO poemRoleFromDTO) {
        PoemRole entity = poemRoleFromDTO.toEntity();
        //防止角色code重复
        PoemRole poemRole = mapper.selectByRoleNameAndRoleCode(entity.getRoleName(), entity.getRoleCode());
        Optional.ofNullable(poemRole).map(item -> {
            if (item.getRoleCode().equals(entity.getRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (item.getRoleName().equals(entity.getRoleName())) {
                throw new ServiceException("角色名已存在");
            }
            return null;
        });
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            return false;
        }
        //如果有配置菜单则添加菜单信息

        if (CollectionUtils.isNotEmpty(poemRoleFromDTO.getMenuIds())) {
            List<PoemRoleMenu> poemRoleMenuList = new ArrayList<>();
            for (Long menuId : poemRoleFromDTO.getMenuIds()) {
                poemRoleMenuList.add(new PoemRoleMenu()
                        .setRoleId(entity.getRoleId())
                        .setMenuId(menuId)
                );
            }
            int i = poemRoleMenuMapper.insertBatch(poemRoleMenuList);
            if (i != poemRoleFromDTO.getMenuIds().size()) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }


    /**
     * 修改角色以及角色菜单关系
     *
     * @param poemRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean update(PoemRoleFromDTO poemRoleFromDTO) {
        PoemRole entity = poemRoleFromDTO.toEntity();
        poemRoleFromDTO.setRoleCode(null);
        //判断角色名称与角色码是否重复
        PoemRole poemRole = mapper.selectByNameOrCode(entity.getRoleId(), entity.getRoleName(), entity.getRoleCode());
        Optional.ofNullable(poemRole).map(item -> {
            if (poemRole.getRoleCode().equals(entity.getRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (poemRole.getRoleName().equals(entity.getRoleName())) {
                throw new ServiceException("角色名已存在");
            }
            return null;
        });

        //修改角色对象
        int insert = mapper.update(entity);
        if (insert <= 0) {
            return false;
        }
        //先删除在新增，覆盖原本的权限
        poemRoleMenuMapper.deleteByRoleId(poemRoleFromDTO.getRoleId());
        if (CollectionUtils.isNotEmpty(poemRoleFromDTO.getMenuIds())) {
            List<PoemRoleMenu> poemRoleMenuList = new ArrayList<>();
            for (Long menuId : poemRoleFromDTO.getMenuIds()) {
                poemRoleMenuList.add(new PoemRoleMenu()
                        .setRoleId(entity.getRoleId())
                        .setMenuId(menuId)
                );
            }
            int i = poemRoleMenuMapper.insertBatch(poemRoleMenuList);
            if (i != poemRoleFromDTO.getMenuIds().size()) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }
}
