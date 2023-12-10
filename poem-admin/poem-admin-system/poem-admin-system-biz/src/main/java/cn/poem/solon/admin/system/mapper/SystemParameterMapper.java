package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.entity.SystemParameter;
import cn.poem.solon.admin.system.domain.entity.table.SystemParameterTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

public interface SystemParameterMapper extends BaseMapper<SystemParameter> {
    final static SystemParameterTableDef SYSTEM_PARAMETER=SystemParameterTableDef.SYSTEM_PARAMETER;


    default SystemParameter selectByKey(String key){
        return this.selectOneByQuery(QueryWrapper.create().where(SYSTEM_PARAMETER.KEY.eq(key)));
    }
}
