package cn.murky.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class SecurityUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract List<String> getPermissions();

    public abstract Boolean getAdmin();

    public abstract String getRoleCode();
}
