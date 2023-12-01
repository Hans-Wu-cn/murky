package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.tenant.domain.convert.PoemTenantPermissionGroupConvert;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantGroupMenu;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantMenu;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantPermissionGroupVo;
import cn.poem.solon.admin.tenant.mapper.PoemTenantGroupMenuMapper;
import cn.poem.solon.admin.tenant.mapper.PoemTenantMenuMapper;
import cn.poem.solon.admin.tenant.mapper.PoemTenantPermissionGroupMapper;
import cn.poem.solon.admin.tenant.service.IPoemTenantPermissionGroupService;
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
public class IPoemTenantPermissionGroupServiceImpl extends ServiceImpl<PoemTenantPermissionGroupMapper, PoemTenantPermissionGroup> implements IPoemTenantPermissionGroupService {

    @Inject
    private PoemTenantMenuMapper poemTenantMenuMapper;
    @Inject
    private PoemTenantGroupMenuMapper poemTenantGroupMenuMapper;

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    @Override
    public PoemTenantPermissionGroupVo info(Long groupId) {
        PoemTenantPermissionGroup poemSaasPermissionGroup = mapper.selectOneById(groupId);
        if (poemSaasPermissionGroup == null) {
            return null;
        }
        PoemTenantPermissionGroupVo vo = PoemTenantPermissionGroupConvert.INSTANCES.toVo(poemSaasPermissionGroup);
        List<PoemTenantMenu> poemTenantMenus = poemTenantMenuMapper.selectByGroupId(groupId);
        List<Long> tenantMenuIds=new ArrayList<>();
        for (PoemTenantMenu poemTenantMenu : poemTenantMenus) {
            Long saasMenuId = poemTenantMenu.getTenantMenuId();
            boolean notHasChild=true;
            for (PoemTenantMenu tenantMenu : poemTenantMenus) {
                if(saasMenuId.equals(tenantMenu.getParentTenantMenuId())){
                    notHasChild=false;
                    break;
                }
            }
            if(notHasChild){
                tenantMenuIds.add(saasMenuId);
            }
        }
        vo.setTenantMenuIds(tenantMenuIds);
        return vo;
    }

    /**
     * 保存商户权限组以及商户权限组菜单关系
     *
     * @param poemTenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean save(PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO) {
        PoemTenantPermissionGroup entity = poemTenantPermissionGroupFromDTO.toEntity();
        //防止权限组code重复
        PoemTenantPermissionGroup poemTenantPermissionGroup = mapper.selectByRoleNameAndRoleCode(entity.getGroupName());
        Optional.ofNullable(poemTenantPermissionGroup).map(item -> {
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
        if (Utils.isNotEmpty(poemTenantPermissionGroupFromDTO.getTenantMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemTenantMenuMapper.selectByListByIds(poemTenantPermissionGroupFromDTO.getTenantMenuIds())
                    .stream()
                    .map(PoemTenantMenu::getParentTenantMenuId)
                    .toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemTenantPermissionGroupFromDTO.getTenantMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<PoemTenantGroupMenu> poemGroupMenuList = new ArrayList<>();
            for (Long saasMenuId : saasMenuIds) {
                poemGroupMenuList.add(PoemTenantGroupMenu.create()
                        .setGroupId(entity.getGroupId())
                        .setTenantMenuId(saasMenuId)
                );
            }
            int i = poemTenantGroupMenuMapper.insertBatch(poemGroupMenuList);
            if (i != saasMenuIds.size()) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param poemTenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean update(PoemTenantPermissionGroupFromDTO poemTenantPermissionGroupFromDTO) {
        PoemTenantPermissionGroup entity = poemTenantPermissionGroupFromDTO.toEntity();
        //判断权限组名称与权限组码是否重复
        PoemTenantPermissionGroup poemRole = mapper.selectByNameOrCode(entity.getGroupId()
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
        PoemTenantGroupMenu.create()
                .where(PoemTenantGroupMenu::getGroupId)
                .eq(poemTenantPermissionGroupFromDTO.getGroupId())
                .remove();
        if (Utils.isNotEmpty(poemTenantPermissionGroupFromDTO.getTenantMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = poemTenantMenuMapper.selectByListByIds(poemTenantPermissionGroupFromDTO.getTenantMenuIds())
                    .stream().map(PoemTenantMenu::getParentTenantMenuId).toList();
            HashSet<Long> saasMenuIds = new HashSet<>(poemTenantPermissionGroupFromDTO.getTenantMenuIds());
            List<PoemTenantGroupMenu> poemRoleMenuList = new ArrayList<>();
            saasMenuIds.addAll(parentMenuIds);
            for (Long saasMenuId : saasMenuIds) {
                poemRoleMenuList.add(PoemTenantGroupMenu.create()
                        .setGroupId(entity.getGroupId())
                        .setTenantMenuId(saasMenuId)
                );
            }
            int i = poemTenantGroupMenuMapper.insertBatch(poemRoleMenuList);
            if (i <= 0) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }

}
