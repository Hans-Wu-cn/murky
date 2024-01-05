package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.system.biz.domain.entity.SystemParameter;
import cn.murky.admin.system.biz.domain.entity.table.SystemParameterTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

public interface SystemParameterMapper extends BaseMapper<SystemParameter> {


    default SystemParameter selectByKey(String key){
        SystemParameterTableDef SYSTEM_PARAMETER=SystemParameterTableDef.SYSTEM_PARAMETER;
        return this.selectOneByQuery(QueryWrapper.create().where(SYSTEM_PARAMETER.KEY.eq(key)));
    }
}
