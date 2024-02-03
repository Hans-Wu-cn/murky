package cn.murky.redismq;

import org.noear.snack.ONode;

/**
 * 使用redis实现mq消息的模板接口
 */
public interface RedisMqTemplate {

    /**
     * 发布消息
     * @param oNode 参数为发布消息的数据,使用snack3转为ONode类型
     */
    void publish(ONode oNode);

    /**
     * 监听消息,不需要调用,只需要实现即可
     */
    void subscribe();

    /**
     * 获取消息主题
     */
    String getTopic();
}
