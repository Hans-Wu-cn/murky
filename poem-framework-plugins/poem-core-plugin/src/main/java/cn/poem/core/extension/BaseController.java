package cn.poem.core.extension;

import cn.poem.core.utils.ApiResult;
import org.noear.solon.annotation.Inject;

/**
 * 公用Controller
 *
 * @author hans
 */
public class BaseController<T> {

    @Inject
    protected T baseService;
    protected ApiResult<?> toResult(boolean b) {
        return b ? ApiResult.ok() : ApiResult.fail();
    }
}
