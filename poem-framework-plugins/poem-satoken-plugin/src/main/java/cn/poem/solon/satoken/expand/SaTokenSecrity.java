package cn.poem.solon.satoken.expand;

import cn.dev33.satoken.stp.StpUtil;
import cn.poem.solon.expand.Security;
import org.noear.solon.annotation.Component;

@Component
public class SaTokenSecrity implements Security {
    @Override
    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }
}
