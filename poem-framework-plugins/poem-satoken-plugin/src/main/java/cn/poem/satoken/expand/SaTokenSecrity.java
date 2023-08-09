package cn.poem.satoken.expand;

import cn.dev33.satoken.stp.StpUtil;
import cn.poem.expand.Security;
import org.noear.solon.annotation.Component;

@Component
public class SaTokenSecrity implements Security {
    @Override
    public Long getUserId() {
        return StpUtil.getLoginIdAsLong();
    }
}
