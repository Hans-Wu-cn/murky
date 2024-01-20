package cn.murky.admin.system.biz.service.impl;

import cn.murky.common.enums.DataScope;
import cn.murky.admin.core.MurkyServiceImpl;
import cn.murky.admin.system.biz.domain.dto.SysRoleFromDTO;
import cn.murky.admin.system.biz.domain.vo.SysRoleVo;
import cn.murky.admin.system.biz.service.ISysRoleService;
import cn.murky.admin.system.biz.domain.entity.SysRoleDept;
import cn.murky.security.utils.SecurityUtils;
import cn.murky.admin.system.biz.domain.convert.SysRoleConvert;
import cn.murky.admin.system.biz.domain.entity.SysMenu;
import cn.murky.admin.system.biz.domain.entity.SysRole;
import cn.murky.admin.system.biz.domain.entity.SysRoleMenu;
import cn.murky.admin.system.biz.mapper.SysMenuMapper;
import cn.murky.admin.system.biz.mapper.SysRoleDeptMapper;
import cn.murky.admin.system.biz.mapper.SysRoleMapper;
import cn.murky.admin.system.biz.mapper.SysRoleMenuMapper;
import cn.murky.core.exception.ServiceException;
import org.noear.solon.Utils;
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
public class ISysRoleServiceImpl extends MurkyServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Inject
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Inject
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Inject
    private SysMenuMapper SysMenuMapper;

    /**
     * 查询角色以及角色菜单关系
     *
     * @param roleId 角色id
     * @return 角色视图对象，包含菜单信息
     */
    @Override
    public SysRoleVo info(Long roleId) {
        SysRole sysRole = mapper.selectOneById(roleId);
        if (sysRole == null) {
            return null;
        }
        SysRoleVo vo = SysRoleConvert.INSTANCES.toVo(sysRole);
        List<SysMenu> sysRoleMenus = SysMenuMapper.selectByRoleId(roleId);
        List<Long> menuIds=new ArrayList<>();
        for (SysMenu SysMenu : sysRoleMenus) {
            Long menuId = SysMenu.getId();
            boolean notHasChild=true;
            for (SysMenu saasMenu : sysRoleMenus) {
                if(menuId.equals(saasMenu.getParentId())){
                    notHasChild=false;
                    break;
                }
            }
            if(notHasChild){
                menuIds.add(menuId);
            }
        }
        vo.setFkMenuIds(menuIds);
        if (DataScope.CUSTOMIZE == sysRole.getDataScope()) {
            List<Long> deptIds = SysRoleDept.create().where(SysRoleDept::getFkRoleId).eq(roleId).list().stream().map(SysRoleDept::getFkDeptId).toList();
            vo.setFkDeptIds(deptIds);
        }

        return vo;
    }

    /**
     * 保存角色以及角色菜单关系
     *
     * @param sysRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean save(SysRoleFromDTO sysRoleFromDTO) {
        SysRole entity = sysRoleFromDTO.toEntity();
        //防止角色code重复
        SysRole sysRole = mapper.selectByRoleNameAndRoleCode(entity.getRoleName(), entity.getRoleCode());
        Optional.ofNullable(sysRole).map(item -> {
            if (item.getRoleCode().equals(entity.getRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (item.getRoleName().equals(entity.getRoleName())) {
                throw new ServiceException("角色名已存在");
            }
            return null;
        });
        entity.setDataScope(DataScope.ONESELF);
        entity.setFkDeptId(SecurityUtils.getUserInfo().getDeptId());
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            return false;
        }
        //如果有配置菜单则添加菜单信息
        if (Utils.isNotEmpty(sysRoleFromDTO.getFkMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = SysMenuMapper.selectByListByIds(sysRoleFromDTO.getFkMenuIds()).stream().map(SysMenu::getParentId).toList();
            HashSet<Long> menuIds = new HashSet<>(sysRoleFromDTO.getFkMenuIds());
            menuIds.addAll(parentMenuIds);
            List<SysRoleMenu> sysRoleMenuList = menuIds.stream().map(menuId -> new SysRoleMenu()
                    .setFkRoleId(entity.getId())
                    .setFkMenuId(menuId)).toList();
            int i = sysRoleMenuMapper.insertBatch(sysRoleMenuList);
            if (i <= 0) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }


    /**
     * 修改角色以及角色菜单关系
     *
     * @param sysRoleFromDTO 角色表单对象
     * @return 保存成功状态
     */
    @Override
    @Tran
    public boolean update(SysRoleFromDTO sysRoleFromDTO) {
        SysRole entity = sysRoleFromDTO.toEntity();
        sysRoleFromDTO.setRoleCode(null);
        //判断角色名称与角色码是否重复
        SysRole sysRole = mapper.selectByNameOrCode(entity.getId(), entity.getRoleName(), entity.getRoleCode());
        Optional.ofNullable(sysRole).map(item -> {
            if (item.getRoleCode().equals(entity.getRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (item.getRoleName().equals(entity.getRoleName())) {
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
        sysRoleMenuMapper.deleteByRoleId(sysRoleFromDTO.getId());
        if (Utils.isNotEmpty(sysRoleFromDTO.getFkMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = SysMenuMapper.selectByListByIds(sysRoleFromDTO.getFkMenuIds()).stream().map(SysMenu::getParentId).toList();
            HashSet<Long> menuIds = new HashSet<>(sysRoleFromDTO.getFkMenuIds());
            menuIds.addAll(parentMenuIds);
            List<SysRoleMenu> sysRoleMenuList = menuIds.stream().map(menuId -> new SysRoleMenu()
                    .setFkRoleId(entity.getId())
                    .setFkMenuId(menuId)).toList();
            int i = sysRoleMenuMapper.insertBatch(sysRoleMenuList);
            if (i <= 0) {
                throw new ServiceException("修改失败");
            }
        }
        //删除角色部门关系数据,准备重载
        SysRoleDept.create().where(SysRoleDept::getFkRoleId).eq(sysRoleFromDTO.getId()).remove();
        // 如果是自定义部门权限则需要保存对应部门角色关系数据
        if (DataScope.CUSTOMIZE == sysRoleFromDTO.getDataScope()) {
            List<SysRoleDept> sysRoleDepts = sysRoleFromDTO.getFkDeptIds().stream().map(item -> {
                return SysRoleDept.create().setFkDeptId(item).setFkRoleId(sysRoleFromDTO.getId());
            }).toList();
            int i = sysRoleDeptMapper.insertBatch(sysRoleDepts);
            if (i != sysRoleFromDTO.getFkDeptIds().size()) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }
}
