package cn.poem.solon.core.extension;

import cn.poem.solon.core.utils.ApiResult;
import com.mybatisflex.core.service.IService;
import org.noear.solon.annotation.Inject;

public class BaseController<T extends IService<?>> {

    @Inject
    protected T baseService;
    protected ApiResult<?> toResult(boolean b) {
        return b ? ApiResult.ok() : ApiResult.fail();
    }
}
