package cn.poem.solon.admin.gen.service.impl;

import cn.poem.solon.admin.PoemServiceImpl;
import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.gen.constant.GenConstants;
import cn.poem.solon.admin.gen.domain.convert.GenTableConvert;
import cn.poem.solon.admin.gen.domain.dto.GenTableDTO;
import cn.poem.solon.admin.gen.domain.vo.GenTableVo;
import cn.poem.solon.admin.gen.domain.dto.GenTablePageDTO;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.enums.TplCategory;
import cn.poem.solon.admin.gen.mapper.GenTableColumnMapper;
import cn.poem.solon.admin.gen.mapper.GenTableMapper;
import cn.poem.solon.admin.gen.service.IGenTableService;
import cn.poem.solon.admin.gen.utils.GenUtils;
import cn.poem.solon.admin.utils.SecurityUtils;
import com.mybatisflex.core.paginate.Page;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.Arrays;
import java.util.List;

@Component
public class GenTableServiceImpl extends PoemServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    /**
     * 树编码字段
     */
    public static final String TREE_CODE = "treeCode";

    /**
     * 树父编码字段
     */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /**
     * 树名称字段
     */
    public static final String TREE_NAME = "treeName";

    /**
     * 上级菜单ID字段
     */
    public static final String PARENT_MENU_ID = "parentMenuId";

    /**
     * 上级菜单名称字段
     */
    public static final String PARENT_MENU_NAME = "parentMenuName";
    @Inject
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查询业务信息
     *
     * @param tableId 业务ID
     * @return 业务信息
     */
    @Override
    public GenTableVo selectGenTableById(Long tableId) {
        GenTableVo genTableVo = mapper.selctTableLeftColunmByTableId(tableId);
        ONode paramsObj = ONode.loadStr(genTableVo.getOptions());
        if (!paramsObj.isNull()) {
            String treeCode = paramsObj.attrGet(TREE_CODE);
            String treeParentCode = paramsObj.attrGet(TREE_PARENT_CODE);
            String treeName = paramsObj.attrGet(TREE_NAME);
            String parentMenuId = paramsObj.attrGet(PARENT_MENU_ID);
            String parentMenuName = paramsObj.attrGet(PARENT_MENU_NAME);

            genTableVo.setTreeCode(treeCode);
            genTableVo.setTreeParentCode(treeParentCode);
            genTableVo.setTreeName(treeName);
            genTableVo.setParentMenuId(parentMenuId);
            genTableVo.setParentMenuName(parentMenuName);
        }
        return genTableVo;
    }

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    @Override
    public List<GenTableVo> selectGenTableAll() {
        return mapper.selctTableLeftColunm();
    }

    /**
     * 查询据库列表
     *
     * @param genTablePageDTO 业务信息
     * @return 数据库表集合
     */
    @Override
    public Page<GenTable> selectDbTableList(GenTablePageDTO genTablePageDTO) {
        return mapper.selectDbTablePage(genTablePageDTO);
    }

    /**
     * 查询据库列表
     *
     * @param tables 表格字符串
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(String tables) {
        return mapper.selectDbTableList(tables);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Override
    @Tran
    public void importGenTable(List<GenTable> tableList) {
        Long userId = SecurityUtils.getUserId();
        try {
            for (GenTable table : tableList) {
                String tableName = table.getTableName();
                GenUtils.initTable(table, userId);
                int row = mapper.insert(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insert(column);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException("导入失败：" + e.getMessage());
        }
    }

    /**
     * 修改业务
     *
     * @param genTableDTO 业务信息
     */
    @Override
    @Tran
    public void updateGenTable(GenTableDTO genTableDTO) {
        GenTable genTable = GenTableConvert.INSTANCES.toEntity(genTableDTO);
        int row = mapper.update(genTable);
        if (row > 0)
        {
            for (GenTableColumn cenTableColumn : genTableDTO.getColumns())
            {
                genTableColumnMapper.update(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据ID
     */
    @Override
    @Tran
    public void deleteGenTableByIds(Long[] tableIds) {
        List<Long> tableIdList = Arrays.asList(tableIds);
        mapper.deleteBatchByIds(tableIdList);
        genTableColumnMapper.deleteBatchByIds(tableIdList);
    }

    @Override
    public void validateEdit(GenTableDTO genTableDTO) {
        if (TplCategory.TREE == genTableDTO.getTplCategory()) {
            ONode paramsObj = ONode.loadStr(genTableDTO.getOptions());
            if (paramsObj.get(GenConstants.TREE_CODE).isNull()) {
                throw new ServiceException("树编码字段不能为空");
            } else if (paramsObj.get(GenConstants.TREE_PARENT_CODE).isNull()) {
                throw new ServiceException("树父编码字段不能为空");
            } else if (paramsObj.get(GenConstants.TREE_NAME).isNull()) {
                throw new ServiceException("树名称字段不能为空");
            } else if (TplCategory.SUB == genTableDTO.getTplCategory()) {
                if (genTableDTO.getSubTableName()==null || genTableDTO.getSubTableName().isEmpty()) {
                    throw new ServiceException("关联子表的表名不能为空");
                } else if (genTableDTO.getSubTableFkName()==null || genTableDTO.getSubTableFkName().isEmpty()) {
                    throw new ServiceException("子表关联的外键名不能为空");
                }
            }
        }
    }

}
