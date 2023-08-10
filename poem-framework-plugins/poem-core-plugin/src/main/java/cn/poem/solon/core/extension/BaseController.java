package cn.poem.solon.core.extension;

import cn.poem.solon.core.utils.ApiResult;
import org.noear.solon.annotation.Inject;

/**
 * 公用Controller
 *
 * @author hans
 */
public class BaseController<T> {

    @Inject
    protected T baseService;
    protected ApiResult<?> toResult(Boolean b) {
        return b ? ApiResult.ok() : ApiResult.fail();
    }
}
