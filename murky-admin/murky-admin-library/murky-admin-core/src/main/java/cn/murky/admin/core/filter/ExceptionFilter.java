package cn.murky.admin.core.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.murky.common.enums.ApiResultEnum;
import cn.murky.common.utils.ApiResult;
import cn.murky.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.noear.dami.exception.DamiException;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.validation.ValidatorException;

/**
 * 系统异常统一处理拦截
 */
@Component(index = 0)
@Slf4j
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(ctx);
        }catch (DamiException ex){
            // 解包damiBus异常
            throw ex.getCause();
        }catch (NotLoginException ex){
            // 未登录异常处理
            log.debug("登录状态过期:{},{}",ex.getCode(),ex.getMessage());
            ctx.render(ApiResult.fail(ApiResultEnum.NOT_LOGIN,ex.getMessage()));
        }catch (NotPermissionException ex){
            // 没有权限异常处理
            ex.printStackTrace();
            ctx.render(ApiResult.fail(ApiResultEnum.NOT_PREMISSION));
        }catch (ServiceException ex){
            // 业务异常处理
            ctx.render(ApiResult.fail(ex.CODE,ex.getMessage()));
        }catch (ValidatorException ex){
            // 表单验证异常处理
            log.error("表单验证异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.getCode(), "参数错误"));
        }
        catch (RuntimeException ex){
            // 其他异常
            ex.printStackTrace();
            ctx.render(ApiResult.fail(500,ex.getMessage()));
        }

    }
}
