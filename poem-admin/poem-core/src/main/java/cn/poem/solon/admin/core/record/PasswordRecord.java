package cn.poem.solon.admin.core.record;


public record PasswordRecord(String salt, String password) {
    public PasswordRecord(String salt, String password) {
        this.salt=salt;
        this.password=password;
    }


}
