package cn.poem.solon.admin;


import cn.poem.solon.admin.constant.BusTopicConstant;
import cn.poem.solon.admin.entity.SecurityUserInfo;
import cn.poem.solon.admin.utils.DataScopeUtils;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.dami.Dami;
import org.noear.solon.core.bean.InitializingBean;
import java.util.List;

public abstract class PoemServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IService<T>, InitializingBean {

    protected QueryWrapper dataScope(QueryWrapper query, SecurityUserInfo userInfo){

        return DataScopeUtils.dataScope(query,userInfo);
    }

    protected QueryWrapper dataScope(QueryWrapper query){
        return DataScopeUtils.dataScope(query);
    }

    public List<T> list(QueryWrapper query,SecurityUserInfo userInfo) {
        return getMapper().selectListByQuery(dataScope(query,userInfo));
    }

    public List<T> list(QueryWrapper query) {
        return getMapper().selectListByQuery(dataScope(query));
    }

    public Page<T> page(Page<T> page, QueryWrapper query,SecurityUserInfo userInfo) {
        QueryWrapper queryWrapper = dataScope(query, userInfo);
        return super.page(page,queryWrapper);
    }

    public Page<T> page(Page<T> page, QueryWrapper query) {
        QueryWrapper queryWrapper = dataScope(query, getUserInfo());
        return super.page(page,queryWrapper);
    }

    private SecurityUserInfo getUserInfo(){
        return Dami.<String, SecurityUserInfo>bus().sendAndResponse(BusTopicConstant.USER_INFO_TOPIC,null);
    }


}
