package cn.murky.admin.system.api.constant;

import cn.murky.core.record.ErrorRecord;

public class ErrorConstant {
    public static final ErrorRecord OLD_PASSWORD_ERROR = new ErrorRecord(5000, "旧密码错误");
    public static final ErrorRecord LANGUAGE_NOT_SUPPORT = new ErrorRecord(5001, "暂不支持该语言");
    public static final ErrorRecord DEPT_HAS_CHILD = new ErrorRecord(5002, "该部门下仍有子部门无法删除");
    public static final ErrorRecord DEPT_IS_USED = new ErrorRecord(5003, "该部门正在被使用,无法删除");
    public static final ErrorRecord I18N_KEY_ALREADY = new ErrorRecord(5004, "当前i18nKey已存在");
    public static final ErrorRecord ILLEGAL_LANGUAGE = new ErrorRecord(5005, "非法的i18n语言");
    public static final ErrorRecord DEFAULT_LANGUAGE_NOT_SET = new ErrorRecord(5006, "默认语言未设置");
    public static final ErrorRecord MENU_HAS_CHILD = new ErrorRecord(5007, "该菜单下仍有子菜单无法删除");
    public static final ErrorRecord MENU_IS_USED = new ErrorRecord(5008, "该菜单正在被使用,无法删除");
    public static final ErrorRecord ROLE_CODE_ALREADY = new ErrorRecord(5009, "当前角色码已存在");
    public static final ErrorRecord ROLE_NAME_ALREADY = new ErrorRecord(5010, "当前角色名已存在");
    public static final ErrorRecord ACCOUNT_ALREADY = new ErrorRecord(5011, "账号已存在");
    public static final ErrorRecord CONFIRM_PASSWORD_ERROR = new ErrorRecord(5012, "确认密码不一致");
    public static final ErrorRecord USER_NOT_EXIST = new ErrorRecord(5013, "该用户不存在");
}
