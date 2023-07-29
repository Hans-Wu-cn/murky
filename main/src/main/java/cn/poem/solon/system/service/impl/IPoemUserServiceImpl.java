package cn.poem.solon.system.service.impl;

import cn.poem.solon.system.domain.entity.PoemUser;
import cn.poem.solon.system.mapper.PoemUserMapper;
import cn.poem.solon.system.service.IPoemUserService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.ProxyComponent;

@ProxyComponent
public class IPoemUserServiceImpl extends ServiceImpl<PoemUserMapper, PoemUser> implements IPoemUserService {
}
