package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.tenant.domain.convert.TenantPermissionGroupConvert;
import cn.poem.solon.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.poem.solon.admin.tenant.domain.entity.TenantGroupMenu;
import cn.poem.solon.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.poem.solon.admin.tenant.domain.entity.TenantMenu;
import cn.poem.solon.admin.tenant.domain.vo.TenantPermissionGroupVo;
import cn.poem.solon.admin.tenant.mapper.TenantGroupMenuMapper;
import cn.poem.solon.admin.tenant.mapper.TenantMenuMapper;
import cn.poem.solon.admin.tenant.mapper.TenantPermissionGroupMapper;
import cn.poem.solon.admin.tenant.service.ITenantPermissionGroupService;
import cn.poem.solon.exception.ServiceException;
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
public class ITenantPermissionGroupServiceImpl extends ServiceImpl<TenantPermissionGroupMapper, TenantPermissionGroup> implements ITenantPermissionGroupService {

    @Inject
    private TenantMenuMapper tenantMenuMapper;
    @Inject
    private TenantGroupMenuMapper tenantGroupMenuMapper;

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param groupId 商户权限组id
     * @return 商户权限组视图对象，包含菜单信息
     */
    @Override
    public TenantPermissionGroupVo info(Long groupId) {
        TenantPermissionGroup poemSaasPermissionGroup = mapper.selectOneById(groupId);
        if (poemSaasPermissionGroup == null) {
            return null;
        }
        TenantPermissionGroupVo vo = TenantPermissionGroupConvert.INSTANCES.toVo(poemSaasPermissionGroup);
        List<TenantMenu> tenantMenus = tenantMenuMapper.selectByGroupId(groupId);
        List<Long> tenantMenuIds = tenantMenus.stream().filter(item -> {
            Long saasMenuId = item.getTenantMenuId();
            boolean notHasChild = true;
            for (TenantMenu tenantMenu : tenantMenus) {
                if (saasMenuId.equals(tenantMenu.getParentTenantMenuId())) {
                    notHasChild = false;
                    break;
                }
            }
            return notHasChild;
        }).map(TenantMenu::getTenantMenuId).toList();
        vo.setTenantMenuIds(tenantMenuIds);
        return vo;
    }

    /**
     * 保存商户权限组以及商户权限组菜单关系
     *
     * @param tenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean save(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO) {
        TenantPermissionGroup entity = tenantPermissionGroupFromDTO.toEntity();
        //防止权限组code重复
        TenantPermissionGroup tenantPermissionGroup = mapper.selectByRoleNameAndRoleCode(entity.getGroupName());
        Optional.ofNullable(tenantPermissionGroup).map(item -> {
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
        if (Utils.isNotEmpty(tenantPermissionGroupFromDTO.getTenantMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = tenantMenuMapper.selectByListByIds(tenantPermissionGroupFromDTO.getTenantMenuIds())
                    .stream()
                    .map(TenantMenu::getParentTenantMenuId)
                    .toList();
            HashSet<Long> saasMenuIds = new HashSet<>(tenantPermissionGroupFromDTO.getTenantMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<TenantGroupMenu> groupMenuList = new ArrayList<>();
            for (Long saasMenuId : saasMenuIds) {
                groupMenuList.add(TenantGroupMenu.create()
                        .setGroupId(entity.getGroupId())
                        .setTenantMenuId(saasMenuId)
                );
            }
            int i = tenantGroupMenuMapper.insertBatch(groupMenuList);
            if (i != saasMenuIds.size()) {
                throw new ServiceException("添加失败");
            }

        }
        return true;
    }

    /**
     * 修改商户权限组以及商户权限组菜单关系
     *
     * @param tenantPermissionGroupFromDTO 商户权限组表单对象
     * @return 保存成功状态
     */
    @Tran
    @Override
    public boolean update(TenantPermissionGroupFromDTO tenantPermissionGroupFromDTO) {
        TenantPermissionGroup entity = tenantPermissionGroupFromDTO.toEntity();
        //判断权限组名称与权限组码是否重复
        TenantPermissionGroup tenantRole = mapper.selectByNameOrCode(entity.getGroupId()
                , entity.getGroupName());
        Optional.ofNullable(tenantRole).map(item -> {
            if (item.getGroupName().equals(entity.getGroupName())) {
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
        TenantGroupMenu.create()
                .where(TenantGroupMenu::getGroupId)
                .eq(tenantPermissionGroupFromDTO.getGroupId())
                .remove();
        if (Utils.isNotEmpty(tenantPermissionGroupFromDTO.getTenantMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = tenantMenuMapper.selectByListByIds(tenantPermissionGroupFromDTO.getTenantMenuIds())
                    .stream().map(TenantMenu::getParentTenantMenuId).toList();
            HashSet<Long> saasMenuIds = new HashSet<>(tenantPermissionGroupFromDTO.getTenantMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<TenantGroupMenu> tenantGroupMenus = saasMenuIds.stream()
                    .map(item -> TenantGroupMenu.create().setGroupId(entity.getGroupId())
                    .setTenantMenuId(item)).toList();
            int i = tenantGroupMenuMapper.insertBatch(tenantGroupMenus);
            if (i <= 0) {
                throw new ServiceException("修改失败");
            }
        }
        return true;
    }

}
