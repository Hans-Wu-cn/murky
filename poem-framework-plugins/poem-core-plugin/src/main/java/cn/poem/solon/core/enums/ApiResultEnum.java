package cn.poem.solon.core.enums;

import lombok.Getter;

/**
 * 响应体枚举,方便申明
 *
 * @author hans
 */
@Getter
public enum ApiResultEnum {
    SUCCESS(200,"成功","统一成功状态码"),
    FAil(500,"失败","请求错误,根据具体的错误响应不同的状态码"),
    NOT_LOGIN(401,"登录状态过期，请重新登录","token过期，未登录等错误"),

    ;
    /*响应码*/
    private final int code;
    /*响应消息*/
    private final String message;
    /*描述*/
    private final String describe;

    ApiResultEnum(int code, String message, String describe) {
        this.code = code;
        this.message = message;
        this.describe = describe;
    }
}
