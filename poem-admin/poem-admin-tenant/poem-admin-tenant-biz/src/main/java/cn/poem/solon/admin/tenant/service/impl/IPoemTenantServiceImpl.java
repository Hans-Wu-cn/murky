package cn.poem.solon.admin.tenant.service.impl;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.enums.Sex;
import cn.poem.solon.admin.tenant.domain.convert.PoemTenantConvert;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantFromDTO;
import cn.poem.solon.admin.tenant.domain.dto.PoemTenantPageDTO;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenant;
import cn.poem.solon.admin.tenant.domain.entity.PoemTenantUser;
import cn.poem.solon.admin.tenant.domain.vo.PoemTenantVo;
import cn.poem.solon.admin.tenant.mapper.PoemTenantMapper;
import cn.poem.solon.admin.tenant.mapper.PoemTenantUserMapper;
import cn.poem.solon.admin.tenant.service.IPoemTenantService;
import cn.poem.solon.exception.ServiceException;
import cn.poem.solon.record.PasswordRecord;
import cn.poem.solon.utils.EncryptionUtil;
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
public class IPoemTenantServiceImpl extends ServiceImpl<PoemTenantMapper, PoemTenant> implements IPoemTenantService {

    @Inject
    private PoemTenantUserMapper poemTenantUserMapper;

    /**
     * 分页接口
     *
     * @param poemTenantPageDTO 分页DTO对象
     */
    @Override
    public Page<PoemTenantVo> page(PoemTenantPageDTO poemTenantPageDTO) {
        return mapper.page(poemTenantPageDTO);
    }

    /**
     * 详情接口
     *
     * @param tenantId 租户id
     */
    @Override
    public PoemTenantVo info(Long tenantId) {
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
     * @param poemTenantFromDTO 租户表单对象
     */
    @Tran
    @Override
    public Boolean add(PoemTenantFromDTO poemTenantFromDTO) {
        // 检测密码是否一致
        if (!poemTenantFromDTO.getPassword().equals(poemTenantFromDTO.getConfirmPassword())) {
            throw new ServiceException("两次输入密码不一致");
        }
        // 检测租户名称是否已存在
        long tenantNameCount = mapper.selectCountByTenantName(poemTenantFromDTO.getTenantName());
        if (tenantNameCount > 0) {
            throw new ServiceException("该租户名称已存在");
        }
        // 校验账号是否重复
        long accountCount = poemTenantUserMapper.selectCountByAccount(poemTenantFromDTO.getAccount());
        if (accountCount > 0) {
            throw new ServiceException("该账号已存在");
        }
        // 添加租户
        PoemTenant poemTenantEntity = PoemTenantConvert.INSTANCES.toEntity(poemTenantFromDTO)
                .setStatus(CommonStatus.NORMAL);
        int tenantInsertCount = mapper.insert(poemTenantEntity);
        if (tenantInsertCount <= 0) {
            throw new ServiceException("添加租户失败");
        }
        // 密码加密
        PasswordRecord passwordRecord = EncryptionUtil.userEncryption(poemTenantFromDTO.getPassword());
        // 添加租户管理员
        PoemTenantUser poemTenantUser = new PoemTenantUser().setAccount(poemTenantFromDTO.getAccount())
                .setUserName(poemTenantFromDTO.getTenantName())
                .setPassword(passwordRecord.password())
                .setSalt(passwordRecord.salt())
                .setSex(Sex.OTHER)
                .setTenantId(poemTenantEntity.getTenantId());
        int userInsertCount = poemTenantUserMapper.insert(poemTenantUser);
        if (userInsertCount <= 0) {
            throw new ServiceException("添加租户失败");
        }
        // 绑定租户与租户管理员
        poemTenantEntity.setAdminUser(poemTenantUser.getTenantUserId());
        int count = mapper.update(poemTenantEntity);
        if (count <= 0) {
            throw new ServiceException("添加租户失败");
        }
        return true;
    }

    /**
     * 修改租户
     *
     * @param poemTenantFromDTO 租户表单对象
     */
    @Tran
    @Override
    public Boolean edit(PoemTenantFromDTO poemTenantFromDTO) {
        // 检测租户名称是否已存在
        long tenantNameCount = mapper.selectCountByTenantName(poemTenantFromDTO.getTenantName());
        if (tenantNameCount > 0) {
            throw new ServiceException("该租户名称已存在");
        }
        int i = mapper.updateNameAndExpiresAndGroupByTenantId(poemTenantFromDTO.getTenantId()
                , poemTenantFromDTO.getTenantName()
                , poemTenantFromDTO.getExpires()
                , poemTenantFromDTO.getGroupId());
        return i > 0;
    }
}
