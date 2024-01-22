package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.biz.domain.entity.SysPost;
import cn.murky.admin.system.biz.mapper.SysPostMapper;
import cn.murky.admin.system.biz.service.ISysPostService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

/**
 * SysPost service
 */
@Component
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper,SysPost> implements ISysPostService {
}
