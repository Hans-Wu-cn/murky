package cn.poem.core.enums;

import lombok.Getter;

/**
 * 响应体枚举,方便申明
 *
 * @author hans
 */
@Getter
public enum ApiResultEnum {
    SUCCESS(200,"成功"),
    FAil(500,"失败"),

    ;
    /*响应码*/
    private final int code;
    /*响应消息*/
    private final String message;

    ApiResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
