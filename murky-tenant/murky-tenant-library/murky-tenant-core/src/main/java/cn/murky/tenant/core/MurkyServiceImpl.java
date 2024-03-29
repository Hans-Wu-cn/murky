package cn.murky.tenant.core;


import cn.murky.security.entity.SecurityUserInfo;
import cn.murky.tenant.core.utils.DataScopeUtils;
import cn.murky.tenant.core.utils.SecurityUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.core.bean.InitializingBean;

import java.util.List;

public abstract class MurkyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IService<T>, InitializingBean {

    protected QueryWrapper dataScope(QueryWrapper query, SecurityTenantUserInfo userInfo){

        DataScopeUtils.dataScope(query,userInfo,null);
        return query;
    }

    protected QueryWrapper dataScope(QueryWrapper query){
        return DataScopeUtils.dataScope(query);
    }

    public List<T> list(QueryWrapper query,SecurityTenantUserInfo userInfo) {
        return getMapper().selectListByQuery(dataScope(query,userInfo));
    }

    public List<T> list(QueryWrapper query) {
        return getMapper().selectListByQuery(dataScope(query));
    }

    public Page<T> page(Page<T> page, QueryWrapper query,SecurityTenantUserInfo userInfo) {
        QueryWrapper queryWrapper = dataScope(query, userInfo);
        return super.page(page,queryWrapper);
    }

    public Page<T> page(Page<T> page, QueryWrapper query) {
        QueryWrapper queryWrapper = dataScope(query, SecurityUtils.getUserInfo());
        return super.page(page,queryWrapper);
    }
}
