package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.enums.Sex;
import cn.poem.solon.admin.tenant.domain.convert.TenantConvert;
import cn.poem.solon.admin.tenant.domain.dto.TenantFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.TenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.Tenant;
import cn.poem.solon.admin.tenant.domain.entity.TenantUser;
import cn.poem.solon.admin.tenant.domain.vo.TenantVo;
import cn.poem.solon.admin.tenant.mapper.TenantMapper;
import cn.poem.solon.admin.tenant.mapper.TenantUserMapper;
import cn.poem.solon.admin.tenant.service.ITenantService;
import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.core.record.PasswordRecord;
import cn.poem.solon.core.utils.EncryptionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

/**
 * PoemTenant Service
 *
 * @Author hans
 */
@Component
public class ITenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Inject
    private TenantUserMapper tenantUserMapper;

    /**
     * 分页接口
     *
     * @param tenantPageDTO 分页DTO对象
     */
    @Override
    public Page<TenantVo> page(TenantPageDTO tenantPageDTO) {
        return mapper.page(tenantPageDTO);
    }

    /**
     * 详情接口
     *
     * @param tenantId 租户id
     */
    @Override
    public TenantVo info(Long tenantId) {
        return mapper.info(tenantId);
    }

    /**
     * 添加租户
     * 1.检测密码是否一致
     * 2.检测租户名称是否已存在
     * 3.校验账号是否重复
     * 4.添加租户
     * 5.密码加密
     * 6.添加租户管理员
     * 7.绑定租户与租户管理员
     *
     * @param tenantFromDTO 租户表单对象
     */
    @Tran
    @Override
    public Boolean add(TenantFromDTO tenantFromDTO) {
        // 检测密码是否一致
        if (!tenantFromDTO.getPassword().equals(tenantFromDTO.getConfirmPassword())) {
            throw new ServiceException("两次输入密码不一致");
        }
        // 检测租户名称是否已存在
        long tenantNameCount = mapper.selectCountByTenantName(tenantFromDTO.getTenantName());
        if (tenantNameCount > 0) {
            throw new ServiceException("该租户名称已存在");
        }
        // 校验账号是否重复
        long accountCount = tenantUserMapper.selectCountByAccount(tenantFromDTO.getAccount());
        if (accountCount > 0) {
            throw new ServiceException("该账号已存在");
        }
        // 添加租户
        Tenant tenantEntity = TenantConvert.INSTANCES.toEntity(tenantFromDTO)
                .setStatus(CommonStatus.NORMAL);
        int tenantInsertCount = mapper.insert(tenantEntity);
        if (tenantInsertCount <= 0) {
            throw new ServiceException("添加租户失败");
        }
        // 密码加密
        PasswordRecord passwordRecord = EncryptionUtil.userEncryption(tenantFromDTO.getPassword());
        // 添加租户管理员
        TenantUser tenantUser = new TenantUser().setAccount(tenantFromDTO.getAccount())
                .setUserName(tenantFromDTO.getTenantName())
                .setPassword(passwordRecord.password())
                .setSalt(passwordRecord.salt())
                .setSex(Sex.OTHER)
                .setTenantId(tenantEntity.getTenantId());
        int userInsertCount = tenantUserMapper.insert(tenantUser);
        if (userInsertCount <= 0) {
            throw new ServiceException("添加租户失败");
        }
        // 绑定租户与租户管理员
        tenantEntity.setAdminUser(tenantUser.getTenantUserId());
        int count = mapper.update(tenantEntity);
        if (count <= 0) {
            throw new ServiceException("添加租户失败");
        }
        return true;
    }

    /**
     * 修改租户
     *
     * @param tenantFromDTO 租户表单对象
     */
    @Tran
    @Override
    public Boolean edit(TenantFromDTO tenantFromDTO) {
        // 检测租户名称是否已存在
        long tenantNameCount = mapper.selectCountByTenantName(tenantFromDTO.getTenantName());
        if (tenantNameCount > 0) {
            throw new ServiceException("该租户名称已存在");
        }
        int i = mapper.updateNameAndExpiresAndGroupByTenantId(tenantFromDTO.getTenantId()
                , tenantFromDTO.getTenantName()
                , tenantFromDTO.getExpires()
                , tenantFromDTO.getGroupId());
        return i > 0;
    }
}
