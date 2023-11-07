package cn.poem.solon.admin.core.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.core.enums.ApiResultEnum;
import cn.poem.solon.admin.core.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.core.handle.Result;
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
            ctx.render(ApiResult.fail(ApiResultEnum.NOT_LOGIN,ex.getMessage()));
        }catch (NotPermissionException ex){
            ex.printStackTrace();
            ctx.render(ApiResult.fail(ApiResultEnum.NOT_PREMISSION));
        }catch (ServiceException ex){
            ctx.render(ApiResult.fail(ex.CODE,ex.getMessage()));
        }catch (ValidatorException ex){
            log.error("表单验证异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.getCode(), "参数错误"));
        }catch (RuntimeException ex){
            // 其他异常
            ex.printStackTrace();
            ctx.render(ApiResult.fail(500,ex.getMessage()));
        }

    }
}
