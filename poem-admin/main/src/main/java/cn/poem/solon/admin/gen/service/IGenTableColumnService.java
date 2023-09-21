package cn.poem.solon.admin.gen.service;

import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import com.mybatisflex.core.service.IService;

import java.util.List;

public interface IGenTableColumnService extends IService<GenTableColumn> {

    /**
     * 根据tableId查询
     */
    List<GenTableColumn> getByTableId(Long tableId);
}
