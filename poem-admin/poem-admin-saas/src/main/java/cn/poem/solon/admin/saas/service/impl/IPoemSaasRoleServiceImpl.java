package cn.poem.solon.admin.saas.service.impl;

import cn.poem.solon.admin.common.enums.DataScope;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.domin.PoemRoleDept;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasRoleConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasRoleFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRole;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasRoleMenu;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasRoleVo;
import cn.poem.solon.admin.saas.mapper.PoemSaasMenuMapper;
import cn.poem.solon.admin.saas.mapper.PoemSaasRoleMapper;
import cn.poem.solon.admin.saas.mapper.PoemSaasRoleMenuMapper;
import cn.poem.solon.admin.saas.service.IPoemSaasRoleService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * 商户角色service
 *
 * @author hans
 */
@Component
public class IPoemSaasRoleServiceImpl extends ServiceImpl<PoemSaasRoleMapper, PoemSaasRole> implements IPoemSaasRoleService {

    @Inject
    private PoemSaasMenuMapper poemSaasMenuMapper;
    @Inject
    private PoemSaasRoleMenuMapper poemSaasRoleMenuMapper;

    /**
     * 修改商户角色以及商户角色菜单关系
     *
     * @param saasRoleId 商户角色id
     * @return 商户角色视图对象，包含菜单信息
     */
    @Override
    public PoemSaasRoleVo info(Long saasRoleId) {
        PoemSaasRole poemSaasRole = mapper.selectOneById(saasRoleId);
        if (poemSaasRole == null) {
            return null;
        }
        PoemSaasRoleVo vo = PoemSaasRoleConvert.INSTANCES.toVo(poemSaasRole);
        List<Long> saasMenuIds = PoemSaasRoleMenu.create()
                .where(PoemSaasRoleMenu::getSaasRoleId)
                .eq(saasRoleId)
                .list()
                .stream()
                .map(PoemSaasRoleMenu::getSaasMenuId).toList();
        vo.setMenuIds(saasMenuIds);
        return vo;
    }

    /**
     * 保存商户角色以及商户角色菜单关系
     *
     * @param poemSaasRoleFromDTO 商户角色表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean save(PoemSaasRoleFromDTO poemSaasRoleFromDTO) {
        PoemSaasRole entity = poemSaasRoleFromDTO.toEntity();
        //防止角色code重复
        PoemSaasRole poemRole = mapper.selectByRoleNameAndRoleCode(entity.getSaasRoleName(), entity.getSaasRoleCode());
        Optional.ofNullable(poemRole).map(item -> {
            if (item.getSaasRoleCode().equals(entity.getSaasRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (item.getSaasRoleName().equals(entity.getSaasRoleName())) {
                throw new ServiceException("角色名已存在");
            }
            return null;
        });
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            return false;
        }
        //如果有配置菜单则添加菜单信息
        if (Utils.isNotEmpty(poemSaasRoleFromDTO.getSaasMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemSaasMenuMapper.selectByListByIds(poemSaasRoleFromDTO.getSaasMenuIds())
                    .stream()
                    .map(PoemSaasMenu::getParentSaasMenuId)
                    .toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemSaasRoleFromDTO.getSaasMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<PoemSaasRoleMenu> poemSaasRoleMenuList = new ArrayList<>();
            for (Long saasMenuId : saasMenuIds) {
                poemSaasRoleMenuList.add(PoemSaasRoleMenu.create()
                        .setSaasRoleId(entity.getSaasRoleId())
                        .setSaasMenuId(saasMenuId)
                );
            }
            int i = poemSaasRoleMenuMapper.insertBatch(poemSaasRoleMenuList);
            if (i != saasMenuIds.size()) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }

    /**
     * 修改商户角色以及商户角色菜单关系
     *
     * @param poemSaasRoleFromDTO 商户角色表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean update(PoemSaasRoleFromDTO poemSaasRoleFromDTO) {
        PoemSaasRole entity = poemSaasRoleFromDTO.toEntity();
        poemSaasRoleFromDTO.setSaasRoleCode(null);
        //判断角色名称与角色码是否重复
        PoemSaasRole poemRole = mapper.selectByNameOrCode(entity.getSaasRoleId()
                , entity.getSaasRoleName(), entity.getSaasRoleCode());
        Optional.ofNullable(poemRole).map(item -> {
            if (poemRole.getSaasRoleCode().equals(entity.getSaasRoleCode())) {
                throw new ServiceException("角色码已存在");
            }
            if (poemRole.getSaasRoleName().equals(entity.getSaasRoleName())) {
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
        PoemSaasRoleMenu.create()
                .where(PoemSaasRoleMenu::getSaasRoleId)
                .eq(poemSaasRoleFromDTO.getSaasRoleId())
                .remove();
        if (Utils.isNotEmpty(poemSaasRoleFromDTO.getSaasMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemSaasMenuMapper.selectByListByIds(poemSaasRoleFromDTO.getSaasMenuIds())
                    .stream().map(PoemSaasMenu::getParentSaasMenuId).toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemSaasRoleFromDTO.getSaasMenuIds());
            List<PoemSaasRoleMenu> poemRoleMenuList = new ArrayList<>();
            saasMenuIds.addAll(parentMenuIds);
            for (Long saasMenuId : saasMenuIds) {
                poemRoleMenuList.add(PoemSaasRoleMenu.create()
                        .setSaasRoleId(entity.getSaasRoleId())
                        .setSaasMenuId(saasMenuId)
                );
            }
            int i = poemSaasRoleMenuMapper.insertBatch(poemRoleMenuList);
            if (i <= 0) {
                throw new ServiceException("修改失败");
            }
        }
        //删除角色部门关系数据,准备重载
        PoemRoleDept.create().where(PoemRoleDept::getRoleId).eq(poemSaasRoleFromDTO.getSaasRoleId()).remove();
        return true;
    }
}
