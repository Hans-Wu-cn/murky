package cn.poem.solon.mybatisflex;

import com.mybatisflex.core.query.QueryWrapper;

/**
 * 数据权限接口
 * @author hans
 */
public interface DataScope {
    /**
     * 开始注入数据权限条件
     * @param queryWrapper 需要注入的wrapper
     * @return 注入后的wrapper
     */
    QueryWrapper to(QueryWrapper queryWrapper);
}
