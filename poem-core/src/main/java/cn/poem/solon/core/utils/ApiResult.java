package cn.poem.solon.core.utils;

import cn.poem.solon.core.enums.ApiResultEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ApiResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*响应码*/
    private int code;
    /*消息内容*/
    private String message;
    /*响应数据*/
    private T result;

    public static <T> ApiResult<T> ok() {
        return restResult(ApiResultEnum.SUCCESS, null);
    }

    public static <T> ApiResult<T> fail() {
        return restResult(ApiResultEnum.FAil, null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return restResult(ApiResultEnum.SUCCESS, data);
    }

    private static <T> ApiResult<T> restResult(int code, String msg, T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setResult(data);
        apiResult.setMessage(msg);
        return apiResult;
    }

    private static <T> ApiResult<T> restResult(ApiResultEnum apiResultEnum, T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(apiResultEnum.getCode());
        apiResult.setResult(data);
        apiResult.setMessage(apiResultEnum.getMessage());
        return apiResult;
    }
}
