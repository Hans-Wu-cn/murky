package cn.murky.core.constant;

import cn.murky.core.record.ErrorRecord;

public class ErrorConstant {
    public static final ErrorRecord ADD_ERROR = new ErrorRecord(1000, "添加失败");
    public static final ErrorRecord DELETE_ERROR = new ErrorRecord(1001, "删除失败");
    public static final ErrorRecord EDIT_ERROR = new ErrorRecord(1002, "修改失败");

}
