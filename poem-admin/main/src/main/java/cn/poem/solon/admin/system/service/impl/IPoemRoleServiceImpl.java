package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.domin.PoemRoleDept;
import cn.poem.solon.admin.enums.DataScope;
import cn.poem.solon.admin.system.domain.convert.PoemRoleConvert;
import cn.poem.solon.admin.system.domain.entity.PoemMenu;
import cn.poem.solon.admin.system.domain.entity.PoemRole;
import cn.poem.solon.admin.system.mapper.PoemMenuMapper;
import cn.poem.solon.admin.system.mapper.PoemRoleDeptMapper;
import cn.poem.solon.admin.core.utils.CollectionUtils;
import cn.poem.solon.admin.system.domain.dto.PoemRoleFromDTO;
import cn.poem.solon.admin.system.domain.entity.PoemRoleMenu;
import cn.poem.solon.admin.system.domain.vo.PoemRoleVo;
import cn.poem.solon.admin.system.mapper.PoemRoleMapper;
import cn.poem.solon.admin.system.mapper.PoemRoleMenuMapper;
import cn.poem.solon.admin.system.service.IPoemRoleService;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * 角色service
 *
 * @author hans
 */
@Component
public class IPoemRoleServiceImpl extends PoemServiceImpl<PoemRoleMapper, PoemRole> implements IPoemRoleService {

    @Inject
    private PoemRoleMenuMapper poemRoleMenuMapper;

    @Inject
    private PoemRoleDeptMapper poemRoleDeptMapper;

    @Inject
    private PoemMenuMapper poemMenuMapper;

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
        if (DataScope.CUSTOMIZE == poemRole.getDataScope()) {
            List<Long> deptIds = PoemRoleDept.create().where(PoemRoleDept::getRoleId).eq(roleId).list().stream().map(PoemRoleDept::getDeptId).toList();
            vo.setDeptIds(deptIds);
        }

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
        entity.setDataScope(DataScope.ONESELF);
        entity.setDeptId(SecurityUtils.getUserInfo().getDeptId());
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            return false;
        }
        //如果有配置菜单则添加菜单信息
        if (CollectionUtils.isNotEmpty(poemRoleFromDTO.getMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemMenuMapper.selectByListByIds(poemRoleFromDTO.getMenuIds()).stream().map(PoemMenu::getParentMenuId).toList();
            HashSet<Long> menuIds = new HashSet<>(poemRoleFromDTO.getMenuIds());
            menuIds.addAll(parentMenuIds);
            List<PoemRoleMenu> poemRoleMenuList = new ArrayList<>();
            for (Long menuId : menuIds) {
                poemRoleMenuList.add(new PoemRoleMenu()
                        .setRoleId(entity.getRoleId())
                        .setMenuId(menuId)
                );
            }
            int i = poemRoleMenuMapper.insertBatch(poemRoleMenuList);
            if (i != menuIds.size()) {
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
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemMenuMapper.selectByListByIds(poemRoleFromDTO.getMenuIds()).stream().map(PoemMenu::getParentMenuId).toList();
            HashSet<Long> menuIds = new HashSet<>(poemRoleFromDTO.getMenuIds());
            List<PoemRoleMenu> poemRoleMenuList = new ArrayList<>();
            menuIds.addAll(parentMenuIds);
            for (Long menuId : menuIds) {
                poemRoleMenuList.add(new PoemRoleMenu()
                        .setRoleId(entity.getRoleId())
                        .setMenuId(menuId)
                );
            }
            int i = poemRoleMenuMapper.insertBatch(poemRoleMenuList);
            if (i != menuIds.size()) {
                throw new ServiceException("修改失败");
            }
        }
        //删除角色部门关系数据,准备重载
        PoemRoleDept.create().where(PoemRoleDept::getRoleId).eq(poemRoleFromDTO.getRoleId()).remove();
        // 如果是自定义部门权限则需要保存对应部门角色关系数据
        if (DataScope.CUSTOMIZE == poemRoleFromDTO.getDataScope()) {
            List<PoemRoleDept> poemRoleDepts = poemRoleFromDTO.getDeptIds().stream().map(item -> {
                return PoemRoleDept.create().setDeptId(item).setRoleId(poemRoleFromDTO.getRoleId());
            }).toList();
            int i = poemRoleDeptMapper.insertBatch(poemRoleDepts);
            if (i != poemRoleFromDTO.getDeptIds().size()) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }
}
