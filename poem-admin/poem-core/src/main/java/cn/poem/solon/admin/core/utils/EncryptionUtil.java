package cn.poem.solon.admin.core.utils;

import cn.poem.solon.admin.core.record.PasswordRecord;
import org.noear.solon.Utils;

public class EncryptionUtil {


    public static PasswordRecord userEncryption(String password){
        String salt = Utils.guid();
        String ps = Utils.md5(Utils.md5(password) + salt);
        return new PasswordRecord(salt,ps);
    }

    public static String userEncryption(PasswordRecord poemPassword){
        return Utils.md5(Utils.md5(poemPassword.password()) + poemPassword.salt());
    }
}
