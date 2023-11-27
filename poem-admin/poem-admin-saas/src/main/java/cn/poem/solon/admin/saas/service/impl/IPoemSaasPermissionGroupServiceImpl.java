package cn.poem.solon.admin.saas.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.saas.domain.convert.PoemSaasPermissionGroupConvert;
import cn.poem.solon.admin.saas.domain.dto.PoemSaasPermissionGroupFromDTO;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasGroupMenu;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasMenu;
import cn.poem.solon.admin.saas.domain.entity.PoemSaasPermissionGroup;
import cn.poem.solon.admin.saas.domain.vo.PoemSaasPermissionGroupVo;
import cn.poem.solon.admin.saas.mapper.PoemSaasMenuMapper;
import cn.poem.solon.admin.saas.mapper.PoemSaasPermissionGroupMapper;
import cn.poem.solon.admin.saas.mapper.PoemSaasGroupMenuMapper;
import cn.poem.solon.admin.saas.service.IPoemSaasPermissionGroupService;
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
 * 商户权限组service
 *
 * @author hans
 */
@Component
public class IPoemSaasPermissionGroupServiceImpl extends ServiceImpl<PoemSaasPermissionGroupMapper, PoemSaasPermissionGroup> implements IPoemSaasPermissionGroupService {

    @Inject
    private PoemSaasMenuMapper poemSaasMenuMapper;
    @Inject
    private PoemSaasGroupMenuMapper poemSaasGroupMenuMapper;

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    @Override
    public PoemSaasPermissionGroupVo info(Long groupId) {
        PoemSaasPermissionGroup poemSaasPermissionGroup = mapper.selectOneById(groupId);
        if (poemSaasPermissionGroup == null) {
            return null;
        }
        PoemSaasPermissionGroupVo vo = PoemSaasPermissionGroupConvert.INSTANCES.toVo(poemSaasPermissionGroup);
        List<PoemSaasMenu> poemSaasMenus = poemSaasMenuMapper.selectByGroupId(groupId);
        List<Long> saasMenuIds=new ArrayList<>();
        for (PoemSaasMenu poemSaasMenu : poemSaasMenus) {
            Long saasMenuId = poemSaasMenu.getSaasMenuId();
            boolean notHasChild=true;
            for (PoemSaasMenu saasMenu : poemSaasMenus) {
                if(saasMenuId.equals(saasMenu.getParentSaasMenuId())){
                    notHasChild=false;
                    break;
                }
            }
            if(notHasChild){
                saasMenuIds.add(saasMenuId);
            }
        }
        vo.setSaasMenuIds(saasMenuIds);
        return vo;
    }

    /**
     * 保存商户权限组以及商户权限组菜单关系
     *
     * @param poemSaasPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean save(PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO) {
        PoemSaasPermissionGroup entity = poemSaasPermissionGroupFromDTO.toEntity();
        //防止权限组code重复
        PoemSaasPermissionGroup poemRole = mapper.selectByRoleNameAndRoleCode(entity.getGroupName());
        Optional.ofNullable(poemRole).map(item -> {
            if (item.getGroupName().equals(entity.getGroupName())) {
                throw new ServiceException("权限组名已存在");
            }
            return null;
        });
        int insert = mapper.insert(entity);
        if (insert <= 0) {
            return false;
        }
        //如果有配置菜单则添加菜单信息
        if (Utils.isNotEmpty(poemSaasPermissionGroupFromDTO.getSaasMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemSaasMenuMapper.selectByListByIds(poemSaasPermissionGroupFromDTO.getSaasMenuIds())
                    .stream()
                    .map(PoemSaasMenu::getParentSaasMenuId)
                    .toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemSaasPermissionGroupFromDTO.getSaasMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<PoemSaasGroupMenu> poemGroupMenuList = new ArrayList<>();
            for (Long saasMenuId : saasMenuIds) {
                poemGroupMenuList.add(PoemSaasGroupMenu.create()
                        .setGroupId(entity.getGroupId())
                        .setSaasMenuId(saasMenuId)
                );
            }
            int i = poemSaasGroupMenuMapper.insertBatch(poemGroupMenuList);
            if (i != saasMenuIds.size()) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param poemSaasPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean update(PoemSaasPermissionGroupFromDTO poemSaasPermissionGroupFromDTO) {
        PoemSaasPermissionGroup entity = poemSaasPermissionGroupFromDTO.toEntity();
        //判断权限组名称与权限组码是否重复
        PoemSaasPermissionGroup poemRole = mapper.selectByNameOrCode(entity.getGroupId()
                , entity.getGroupName());
        Optional.ofNullable(poemRole).map(item -> {
            if (poemRole.getGroupName().equals(entity.getGroupName())) {
                throw new ServiceException("权限组名已存在");
            }
            return null;
        });

        //修改权限组对象
        int insert = mapper.update(entity);
        if (insert <= 0) {
            return false;
        }

        //先删除在新增，覆盖原本的权限
        PoemSaasGroupMenu.create()
                .where(PoemSaasGroupMenu::getGroupId)
                .eq(poemSaasPermissionGroupFromDTO.getGroupId())
                .remove();
        if (Utils.isNotEmpty(poemSaasPermissionGroupFromDTO.getSaasMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemSaasMenuMapper.selectByListByIds(poemSaasPermissionGroupFromDTO.getSaasMenuIds())
                    .stream().map(PoemSaasMenu::getParentSaasMenuId).toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemSaasPermissionGroupFromDTO.getSaasMenuIds());
            List<PoemSaasGroupMenu> poemRoleMenuList = new ArrayList<>();
            saasMenuIds.addAll(parentMenuIds);
            for (Long saasMenuId : saasMenuIds) {
                poemRoleMenuList.add(PoemSaasGroupMenu.create()
                        .setGroupId(entity.getGroupId())
                        .setSaasMenuId(saasMenuId)
                );
            }
            int i = poemSaasGroupMenuMapper.insertBatch(poemRoleMenuList);
            if (i <= 0) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }

}
