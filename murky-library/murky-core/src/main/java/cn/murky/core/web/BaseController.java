package cn.murky.core.web;

import com.mybatisflex.core.service.IService;
import org.noear.solon.annotation.Inject;

/**
 * 公用Controller
 *
 * @author hans
 */
public class BaseController<T extends IService<?>> {

    @Inject(required = false)
    protected T baseService;
    protected ApiResult<?> toResult(Boolean b) {
        return b ? ApiResult.ok() : ApiResult.fail();
    }

}
