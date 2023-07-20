package cn.poem.solon.core.extension;

/**
 * 用于通用处理枚举序列化的接口
 *
 * @author hans
 */
public interface Enumerator {
    /*枚举值*/
    Integer code();
    /*描述*/
    String des();
}
