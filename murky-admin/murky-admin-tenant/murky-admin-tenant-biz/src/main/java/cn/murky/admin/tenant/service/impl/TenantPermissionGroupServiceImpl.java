package cn.murky.admin.tenant.service.impl;

import cn.murky.admin.tenant.domain.convert.TenantPermissionGroupConvert;
import cn.murky.admin.tenant.mapper.TenantGroupMenuMapper;
import cn.murky.admin.tenant.mapper.TenantMenuMapper;
import cn.murky.admin.tenant.mapper.TenantPermissionGroupMapper;
import cn.murky.admin.tenant.domain.dto.TenantPermissionGroupFromDTO;
import cn.murky.admin.tenant.domain.entity.TenantGroupMenu;
import cn.murky.admin.tenant.domain.entity.TenantPermissionGroup;
import cn.murky.admin.tenant.domain.entity.TenantMenu;
import cn.murky.admin.tenant.domain.vo.TenantPermissionGroupVo;
import cn.murky.admin.tenant.service.ITenantPermissionGroupService;
import cn.murky.core.exception.ServiceException;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static cn.murky.admin.tenant.api.constant.ErrorConstants.PERMISSION_GROUP_NAME_ALREADY;
import static cn.murky.core.constant.ErrorConstant.ADD_ERROR;
import static cn.murky.core.constant.ErrorConstant.EDIT_ERROR;

/**
 * 商户权限组service
 *
 * @author hans
 */
@Component
public class TenantPermissionGroupServiceImpl extends ServiceImpl<TenantPermissionGroupMapper, TenantPermissionGroup> implements ITenantPermissionGroupService {

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
        TenantPermissionGroup tenantPermissionGroup = mapper.selectOneById(groupId);
        if (tenantPermissionGroup == null) {
            return null;
        }
        TenantPermissionGroupVo vo = TenantPermissionGroupConvert.INSTANCES.toVo(tenantPermissionGroup);
        List<TenantMenu> tenantMenus = tenantMenuMapper.selectByGroupId(groupId);
        List<Long> tenantMenuIds = tenantMenus.stream().filter(item -> {
            Long menuId = item.getId();
            boolean notHasChild = true;
            for (TenantMenu tenantMenu : tenantMenus) {
                if (menuId.equals(tenantMenu.getParentId())) {
                    notHasChild = false;
                    break;
                }
            }
            return notHasChild;
        }).map(TenantMenu::getId).toList();
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
                throw new ServiceException(PERMISSION_GROUP_NAME_ALREADY);
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
                    .map(TenantMenu::getParentId)
                    .toList();
            HashSet<Long> saasMenuIds = new HashSet<>(tenantPermissionGroupFromDTO.getTenantMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<TenantGroupMenu> groupMenuList = new ArrayList<>();
            for (Long saasMenuId : saasMenuIds) {
                groupMenuList.add(TenantGroupMenu.create()
                        .setFkGroupId(entity.getId())
                        .setFkMenuId(saasMenuId)
                );
            }
            int i = tenantGroupMenuMapper.insertBatch(groupMenuList);
            if (i != saasMenuIds.size()) {
                throw new ServiceException(ADD_ERROR);
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
        TenantPermissionGroup tenantRole = mapper.selectByNameOrCode(entity.getId()
                , entity.getGroupName());
        Optional.ofNullable(tenantRole).map(item -> {
            if (item.getGroupName().equals(entity.getGroupName())) {
                throw new ServiceException(PERMISSION_GROUP_NAME_ALREADY);
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
                .where(TenantGroupMenu::getFkGroupId)
                .eq(tenantPermissionGroupFromDTO.getId())
                .remove();
        if (Utils.isNotEmpty(tenantPermissionGroupFromDTO.getTenantMenuIds())) {
            //补充不完全一定存在的父级元素
            List<Long> parentMenuIds = tenantMenuMapper.selectByListByIds(tenantPermissionGroupFromDTO.getTenantMenuIds())
                    .stream().map(TenantMenu::getParentId).toList();
            HashSet<Long> saasMenuIds = new HashSet<>(tenantPermissionGroupFromDTO.getTenantMenuIds());
            saasMenuIds.addAll(parentMenuIds);
            List<TenantGroupMenu> tenantGroupMenus = saasMenuIds.stream()
                    .map(item -> TenantGroupMenu.create().setFkGroupId(entity.getId())
                    .setFkMenuId(item)).toList();
            int i = tenantGroupMenuMapper.insertBatch(tenantGroupMenus);
            if (i <= 0) {
                throw new ServiceException(EDIT_ERROR);
            }
        }
        return true;
    }

}
