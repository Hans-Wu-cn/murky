package cn.murky.admin.tenant.api.constant;

import cn.murky.core.record.ErrorRecord;

public class ErrorConstants {
    public static final ErrorRecord TENANT_NAME_ALREADY = new ErrorRecord(10000, "租户名称已存在");
    public static final ErrorRecord REPEATED_PASSWORD_ERROR = new ErrorRecord(10001, "两次输入密码不一致");
    public static final ErrorRecord ACCOUNT_ALREADY = new ErrorRecord(10002, "账号已存在");
    public static final ErrorRecord PERMISSION_GROUP_NAME_ALREADY = new ErrorRecord(10004, "权限组名已存在");
}
