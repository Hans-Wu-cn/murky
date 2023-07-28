package cn.poem.solon.system.service.impl;

import cn.poem.solon.system.domain.entity.PoemRole;
import cn.poem.solon.system.mapper.PoemRoleMapper;
import cn.poem.solon.system.service.IPoemRoleService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.ProxyComponent;

/**
 * 角色service
 *
 * @author hans
 */

@ProxyComponent
public class IPoemRoleServiceImpl extends ServiceImpl<PoemRoleMapper, PoemRole> implements IPoemRoleService {
}
