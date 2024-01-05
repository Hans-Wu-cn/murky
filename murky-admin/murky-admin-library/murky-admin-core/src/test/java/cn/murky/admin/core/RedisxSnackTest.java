package cn.murky.admin.core;

import cn.murky.admin.common.entity.SecurityUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.noear.snack.ONode;
@Slf4j
public class RedisxSnackTest {
    @Test
    public void testSerializerStr() {
        SecurityUserInfo securityUserInfo = new SecurityUserInfo();
        securityUserInfo.setUserId(1L);
        securityUserInfo.setUserName("hans");
        String json = ONode.stringify(securityUserInfo);
        log.debug(json);
        SecurityUserInfo deserialize = ONode.deserialize(json, SecurityUserInfo.class);
        assert securityUserInfo.getUserId().equals(deserialize.getUserId());
    }

}
