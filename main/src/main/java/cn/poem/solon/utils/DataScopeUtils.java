package cn.poem.solon.utils;

import cn.poem.solon.entity.UserInfo;
import cn.poem.solon.mybatisflex.DataScope;
import com.mybatisflex.core.query.QueryWrapper;

public class DataScopeUtils implements DataScope {

    /**
     * 开始注入数据权限条件
     * @param queryWrapper 需要注入的wrapper
     * @return 注入后的wrapper
     */
    @Override
    public QueryWrapper to(QueryWrapper queryWrapper) {
        //超级管理员不需要做数据权限
        if(!SecurityUtil.admin()){
            UserInfo userInfo = SecurityUtil.getUserInfo();
        }
        return queryWrapper;
    }
}
