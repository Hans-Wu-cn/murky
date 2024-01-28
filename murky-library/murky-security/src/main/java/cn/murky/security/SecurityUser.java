package cn.murky.security;

import java.util.List;

public abstract class SecurityUser {

    public abstract List<String> getPermissions();

    public abstract Boolean getAdmin();

    public abstract String getRoleCode();
}
