package cn.poem.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户信息类
 *
 * @author hans
 */
@Data
@Accessors(chain = true)
public abstract class SecurityUser implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录token
     */
    private String token;

}
