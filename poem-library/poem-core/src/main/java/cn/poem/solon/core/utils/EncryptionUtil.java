package cn.poem.solon.core.utils;

import cn.poem.solon.core.record.PasswordRecord;
import org.noear.solon.Utils;

public class EncryptionUtil {


    public static PasswordRecord userEncryption(String password){
        String salt = Utils.guid();
        String ps = Utils.md5(Utils.md5(password) + salt);
        return new PasswordRecord(salt,ps);
    }

    public static String userEncryption(PasswordRecord passwordRecord){
        return Utils.md5(Utils.md5(passwordRecord.password()) + passwordRecord.salt());
    }
}
