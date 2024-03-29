package cn.murky.admin.tenant.service.impl;

import cn.murky.common.enums.CommonStatus;
import cn.murky.admin.tenant.enums.Sex;
import cn.murky.admin.tenant.domain.convert.TenantConvert;
import cn.murky.admin.tenant.domain.dto.TenantFromDTO;
import cn.murky.admin.tenant.domain.dto.TenantPageDTO;
import cn.murky.admin.tenant.domain.entity.Tenant;
import cn.murky.admin.tenant.domain.entity.TenantUser;
import cn.murky.admin.tenant.domain.vo.TenantVo;
import cn.murky.admin.tenant.mapper.TenantMapper;
import cn.murky.admin.tenant.mapper.TenantUserMapper;
import cn.murky.admin.tenant.service.ITenantDDLService;
import cn.murky.admin.tenant.service.ITenantService;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import static cn.murky.admin.tenant.api.constant.ErrorConstants.*;
import static cn.murky.core.constant.ErrorConstant.ADD_ERROR;

/**
 * TenantService
 *
 * @Author hans
 */
@Component
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {
    @Inject("${murky.tenant.schemaPrefix}")
    private String tenantSchemaNamePrefix;
    @Inject
    private TenantUserMapper tenantUserMapper;

    @Inject
    private ITenantDDLService iTenantDDLService;
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
            throw new ServiceException(REPEATED_PASSWORD_ERROR);
        }
        // 检测租户名称是否已存在
        long tenantNameCount = mapper.selectCountByTenantName(tenantFromDTO.getTenantName());
        if (tenantNameCount > 0) {
            throw new ServiceException(TENANT_NAME_ALREADY);
        }
        // 校验账号是否重复
        long accountCount = tenantUserMapper.selectCountByAccount(tenantFromDTO.getAccount());
        if (accountCount > 0) {
            throw new ServiceException(ACCOUNT_ALREADY);
        }
        // 添加租户
        Tenant tenantEntity = TenantConvert.INSTANCES.toEntity(tenantFromDTO)
                .setStatus(CommonStatus.NORMAL);
        boolean tenantSaveB = tenantEntity.save();
        if (!tenantSaveB) {
            throw new ServiceException(ADD_ERROR);
        }
        // 密码加密
        PasswordRecord passwordRecord = EncryptionUtil.userEncryption(tenantFromDTO.getPassword());
        // 添加租户管理员
        TenantUser tenantUser = new TenantUser().setAccount(tenantFromDTO.getAccount())
                .setUserName(tenantFromDTO.getTenantName())
                .setPassword(passwordRecord.password())
                .setSalt(passwordRecord.salt())
                .setSex(Sex.OTHER)
                .setAdmin(true)
                .setFkTenantId(tenantEntity.getId());
        boolean userSaveB = tenantUser.save();
        if (!userSaveB) {
            throw new ServiceException(ADD_ERROR);
        }
        // 绑定租户与租户管理员
        tenantEntity.setAdminUser(tenantUser.getId());
        int count = mapper.update(tenantEntity);
        if (count <= 0) {
            throw new ServiceException(ADD_ERROR);
        }
        // 生成schema名称
        String schemaName = generateSchemaName(tenantEntity.getId());
        //创建schame
        iTenantDDLService.createSchema(schemaName);
        //执行ddl
        iTenantDDLService.createTable(schemaName);
        //插入基础数据
        iTenantDDLService.initData(schemaName);
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
            throw new ServiceException(TENANT_NAME_ALREADY);
        }
        int i = mapper.updateNameAndExpiresAndGroupByTenantId(tenantFromDTO.getId()
                , tenantFromDTO.getTenantName()
                , tenantFromDTO.getExpires()
                , tenantFromDTO.getFkGroupId());
        return i > 0;
    }

    public String generateSchemaName(Long tenantId){
        return STR."\{tenantSchemaNamePrefix}_\{tenantId}";
    }
}
