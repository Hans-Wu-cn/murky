package cn.murky.admin.core;


import cn.murky.core.record.PasswordRecord;
import cn.murky.core.utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UserEncryptionTest {
    @Test
    public void EncryptionUtilTest() {
        String password="admin";
        PasswordRecord passwordRecord0 = EncryptionUtil.userEncryption(password);
        PasswordRecord passwordRecord1 = EncryptionUtil.userEncryption(password);
        log.debug("passwordRecord0:{}",passwordRecord0);
        log.debug("passwordRecord1:{}",passwordRecord1);
        assert passwordRecord0!=passwordRecord1;
    }

    @Test
    public void EncryptionUtil1Test() {
        String password="admin";
        PasswordRecord passwordRecord0 = EncryptionUtil.userEncryption(password);
        String ps = EncryptionUtil.userEncryption(new PasswordRecord(passwordRecord0.salt(),password));
        log.debug("passwordRecord0:{}",passwordRecord0);
        log.debug("ps:{}",ps);
        assert ps.equals(passwordRecord0.password());
    }
}
