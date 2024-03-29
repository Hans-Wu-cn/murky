package cn.murky.core.web;

import cn.murky.common.enums.ApiResultEnum;
import cn.murky.core.exception.ServiceException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应体数据结构
 *
 * @param <T> 任意类型
 * @author hans
 */
@Accessors(chain = true)
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
        return restResult(ApiResultEnum.SUCCESS, "成功");
    }

    public static <T> ApiResult<T> fail(int code, String msg) {
        return restResult(code, msg, null);
    }
    public static <T> ApiResult<T> fail(ServiceException ex) {
        return restResult(ex.getCode(), ex.getMessage(), null);
    }
    public static <T> ApiResult<T> fail(ApiResultEnum apiResult, String msg) {
        return restResult(apiResult, msg);
    }

    public static <T> ApiResult<T> fail() {
        return restResult(ApiResultEnum.FAil);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return restResult(ApiResultEnum.FAil, msg);
    }

    public static <T> ApiResult<T> fail(ApiResultEnum resultEnum) {
        return restResult(resultEnum);
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

    private static <T> ApiResult<T> restResult(ApiResultEnum apiResultEnum, String msg) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(apiResultEnum.getCode());
        apiResult.setMessage(msg);
        return apiResult;
    }

    private static <T> ApiResult<T> restResult(ApiResultEnum apiResultEnum) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(apiResultEnum.getCode());
        apiResult.setMessage(apiResultEnum.getMessage());
        return apiResult;
    }
}
