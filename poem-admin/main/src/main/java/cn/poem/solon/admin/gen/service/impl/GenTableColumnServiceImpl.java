package cn.poem.solon.admin.gen.service.impl;

import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.mapper.GenTableColumnMapper;
import cn.poem.solon.admin.gen.service.IGenTableColumnService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.solon.annotation.Component;

import java.util.List;

@Component
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {
    @Override
    public List<GenTableColumn> getByTableId(Long tableId) {
        return mapper.selectByTableId(tableId);
    }
}
