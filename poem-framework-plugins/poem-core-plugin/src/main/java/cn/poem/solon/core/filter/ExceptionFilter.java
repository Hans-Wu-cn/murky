package cn.poem.solon.core.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.poem.solon.core.enums.ApiResultEnum;
import cn.poem.solon.core.exception.ServiceException;
import cn.poem.solon.core.utils.ApiResult;
import cn.poem.solon.expand.SecurityCache;
import cn.poem.solon.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.validation.ValidatorException;


@Component(index = 0)
@Slf4j
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(ctx);
        }catch (NotLoginException ex){
            log.debug("登录状态过期:{},{}",ex.getCode(),ex.getMessage());
            //推送未登录事件
            SecurityUtil.publishAsyncEvent();
            ctx.render(ApiResult.fail(ApiResultEnum.NOT_LOGIN,ex.getMessage()));
        }catch (ServiceException ex){
            log.error("业务异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.CODE,ex.getMessage()));
        }catch (ValidatorException ex){
            log.error("表单验证异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.getCode(),ex.getMessage()));
        }catch (RuntimeException ex){
            //其他异常
            ctx.render(ApiResult.fail(500,ex.getMessage()));
            ex.printStackTrace();
        }

    }
}
