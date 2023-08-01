package cn.poem.core.filter;

import cn.poem.core.exception.ServiceException;
import cn.poem.core.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
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

            if(!ctx.getHandled()){
                ctx.status(404);
            }
        }catch (ServiceException ex){
            log.error("业务异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.CODE,ex.getMessage()));
        }catch (ValidatorException ex){
            log.error("表单验证异常:{}",ex.getMessage());
            ctx.render(ApiResult.fail(ex.getCode(),ex.getMessage()));
        }catch (RuntimeException ex){
            ex.printStackTrace();
            ctx.render(ApiResult.fail(500,ex.getMessage()));
        }

    }
}
