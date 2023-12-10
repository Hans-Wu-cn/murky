package cn.poem.solon.admin.core;


import cn.poem.solon.record.PasswordRecord;
import cn.poem.solon.utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UserEncryptionTest {
    @Test
    public void EncryptionUtilTest() {
        String password="admin";
        PasswordRecord poemPassword0 = EncryptionUtil.userEncryption(password);
        PasswordRecord poemPassword1 = EncryptionUtil.userEncryption(password);
        log.debug("poemPassword0:{}",poemPassword0);
        log.debug("poemPassword1:{}",poemPassword1);
        assert poemPassword0!=poemPassword1;
    }

    @Test
    public void EncryptionUtil1Test() {
        String password="admin";
        PasswordRecord poemPassword0 = EncryptionUtil.userEncryption(password);
        String ps = EncryptionUtil.userEncryption(new PasswordRecord(poemPassword0.salt(),password));
        log.debug("poemPassword0:{}",poemPassword0);
        log.debug("ps:{}",ps);
        assert ps.equals(poemPassword0.password());
    }
}
