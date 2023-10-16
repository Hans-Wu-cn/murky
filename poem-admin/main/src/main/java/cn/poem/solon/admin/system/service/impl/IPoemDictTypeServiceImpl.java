package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.mapper.PoemDictTypeMapper;
import cn.poem.solon.admin.system.service.IPoemDictTypeService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

/**
 * 字典类型service
 *
 * @Author hans
 */
@Component
public class IPoemDictTypeServiceImpl extends ServiceImpl<PoemDictTypeMapper, PoemDictType> implements IPoemDictTypeService {
}
