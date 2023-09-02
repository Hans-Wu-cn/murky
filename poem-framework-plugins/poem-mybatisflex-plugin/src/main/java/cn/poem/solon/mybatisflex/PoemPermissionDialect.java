package cn.poem.solon.mybatisflex;

import cn.poem.solon.mybatisflex.enums.DataScope;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.QueryWrapper;

public class PoemPermissionDialect extends CommonsDialectImpl {
    @Override
    public String forSelectByQuery(QueryWrapper queryWrapper) {
//        queryWrapper.
//        switch (DataScope){
//            case DataScope.ALL ->{}
//            case DataScope.ALL ->{}
//        }
        //获取当前用户信息，为 queryWrapper 添加额外的条件
//        queryWrapper.and("...");

        return super.buildSelectSql(queryWrapper);
    }
}
