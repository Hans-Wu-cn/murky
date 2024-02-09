package cn.murky.tenant.core.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.murky.common.enums.ApiResultEnum;
import cn.murky.common.utils.StringUtils;
import cn.murky.core.exception.ServiceException;
import cn.murky.core.web.ApiResult;
import cn.murky.tenant.core.SecurityTenantUserInfo;
import cn.murky.tenant.core.utils.SecurityUtils;
import cn.murky.tenant.system.api.TenantUserApi;
import cn.murky.tenant.system.api.domain.bo.TenantUserBO;
import lombok.extern.slf4j.Slf4j;
import org.noear.dami.exception.DamiException;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.validation.ValidatorException;
import org.noear.solon.core.handle.Filter;

import java.util.List;
import java.util.Optional;

import static cn.murky.tenant.core.constant.Constants.TENANT_ID_HEADER;

@Component(index = 1)
@Slf4j
public class TenantFilter implements Filter {
    @Inject("${security.path-ignore}")
    private List<String> ignoreList;
    @Inject
    private TenantUserApi tenantUserApi;

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        if (!ignoreList.contains(ctx.uri().getPath())) {
            String tenantId = ctx.header(TENANT_ID_HEADER);
            if (StringUtils.isEmpty(tenantId)) {
                log.info("[TenantFilter] -> 此次请求未携带租户id ip:{},method:{},uri:{}", ctx.realIp(), ctx.method(), ctx.uri());
                ctx.render(ApiResult.fail(ApiResultEnum.NOT_LOGIN));
                return;
            }
            SecurityUtils.setTenantId(Long.parseLong(tenantId));
            SecurityTenantUserInfo userInfo = SecurityUtils.getUserInfo();
            if (userInfo != null) {
                if (!userInfo.getTenantId().toString().equals(tenantId)) {
                    SecurityUtils.deleteTenantId();
                    log.info("[TenantFilter] -> 无该租户访问权限id:{} ip:{},method:{},uri:{}", tenantId, ctx.realIp(), ctx.method(), ctx.uri());
                    ctx.render(ApiResult.fail(ApiResultEnum.NOT_TENANT_PREMISSION));
                    return;
                }
            }else{
                TenantUserBO tenantUserBO = tenantUserApi.getById(SecurityUtils.getUserId());
                if (!tenantUserBO.getFkTenantId().toString().equals(tenantId)) {
                    SecurityUtils.deleteTenantId();
                    log.info("[TenantFilter] -> 无该租户访问权限id:{} ip:{},method:{},uri:{}", tenantId, ctx.realIp(), ctx.method(), ctx.uri());
                    ctx.render(ApiResult.fail(ApiResultEnum.NOT_TENANT_PREMISSION));
                    return;
                }
            }
        }
        chain.doFilter(ctx);
        SecurityUtils.deleteTenantId();
    }
}
