package cn.poem.solon.admin.gen.service;

import cn.poem.solon.admin.gen.domain.dto.GenTableDTO;
import cn.poem.solon.admin.gen.domain.vo.GenTableVo;
import cn.poem.solon.admin.gen.domain.dto.GenTablePageDTO;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

public interface IGenTableService extends IService<GenTable> {

    /**
     * 查询业务信息
     *
     * @param tableId 业务ID
     * @return 业务信息
     */
    GenTableVo selectGenTableById(Long tableId);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GenTableVo> selectGenTableAll();

    /**
     * 查询据库列表
     *
     * @param genTablePageDTO 业务信息
     * @return 数据库表集合
     */
    Page<GenTable> selectDbTableList(GenTablePageDTO genTablePageDTO);

    /**
     * 查询据库列表
     *
     * @param tables 表格字符串
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableList(String tables);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    void importGenTable(List<GenTable> tableList);

    /**
     * 修改业务
     *
     * @param genTableDTO 业务信息
     */
    void updateGenTable(GenTableDTO genTableDTO);

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据ID
     */
    void deleteGenTableByIds(Long[] tableIds);

    /**
     * 修改保存参数校验
     *
     * @param genTableDTO 业务信息
     */
    void validateEdit(GenTableDTO genTableDTO);
}
