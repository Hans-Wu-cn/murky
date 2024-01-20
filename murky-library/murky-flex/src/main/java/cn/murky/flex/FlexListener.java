package cn.murky.flex;

import com.mybatisflex.annotation.Listener;
import com.mybatisflex.core.FlexGlobalConfig;

/**
 * FlexListener监听注册器
 */
public interface FlexListener {

    void registerListener(FlexGlobalConfig globalConfig);
}
