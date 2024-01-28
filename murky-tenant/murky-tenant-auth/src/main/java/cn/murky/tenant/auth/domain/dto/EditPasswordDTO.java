package cn.murky.tenant.auth.domain.dto;

import lombok.Data;
import org.noear.solon.validation.annotation.NotEmpty;

/**
 *      *  @param oldPassword 旧密码
 *      *  @param password 新密码
 *      *  @param surePassword 确定新密码
 */
@Data
public class EditPasswordDTO {

    /**
     * 旧密码
     */
    @NotEmpty
    private String oldPassword;

    /**
     * 新密码
     */
    @NotEmpty
    private String password;

    /**
     * 确定新密码
     */
    @NotEmpty
    private String surePassword;
}
