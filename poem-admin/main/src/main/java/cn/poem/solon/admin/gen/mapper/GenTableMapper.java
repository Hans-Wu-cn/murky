package cn.poem.solon.admin.gen.mapper;

import cn.poem.solon.admin.gen.domain.vo.GenTableVo;
import cn.poem.solon.admin.gen.domain.dto.GenTablePageDTO;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.table.GenTableColumnTableDef;
import cn.poem.solon.admin.gen.domain.entity.table.GenTableTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowUtil;

import java.text.MessageFormat;
import java.util.List;

public interface GenTableMapper extends BaseMapper<GenTable> {
    GenTableTableDef GEN_TABLE=GenTableTableDef.GEN_TABLE;
    GenTableColumnTableDef GEN_TABLE_COLUMN = GenTableColumnTableDef.GEN_TABLE_COLUMN;

    /**
     * 根据id查询业务表格 业务列关系视图对象
     * @param tableId 表格id
     */
    default GenTableVo selctTableLeftColunmByTableId(Long tableId){
        QueryWrapper queryWrapper = QueryWrapper.create().from(GEN_TABLE).leftJoin(GEN_TABLE_COLUMN).on(GEN_TABLE.TABLE_ID.eq(GEN_TABLE_COLUMN.TABLE_ID));
        queryWrapper.where(GEN_TABLE.TABLE_ID.eq(tableId)).orderBy(GEN_TABLE_COLUMN.SORT.asc());
        return selectOneByQueryAs(queryWrapper,GenTableVo.class);
    }

    /**
     * 查询业务表格 业务列关系视图对象
     * @return
     */
    default List<GenTableVo> selctTableLeftColunm(){
        QueryWrapper queryWrapper = QueryWrapper.create().from(GEN_TABLE).leftJoin(GEN_TABLE_COLUMN).on(GEN_TABLE.TABLE_ID.eq(GEN_TABLE_COLUMN.TABLE_ID));
        queryWrapper.orderBy(GEN_TABLE_COLUMN.SORT.asc());
        return selectListByQueryAs(queryWrapper,GenTableVo.class);
    }

    /**
     * 查询数据库表格信息
     * @param genTablePageDTO
     * @return 返回表格分页信息
     */
    default Page<GenTable> selectDbTablePage(GenTablePageDTO genTablePageDTO){
        QueryWrapper queryWrapper = QueryWrapper.create().from("information_schema.tables");
        queryWrapper.select("table_name").where("table_schema = current_schema()").and("table_name not in ('gen_table')");
        queryWrapper.and(MessageFormat.format("table_name like %{0}%",genTablePageDTO.getTableName()),!genTablePageDTO.getTableName().isEmpty());
        return paginate(genTablePageDTO,queryWrapper);
    }

    /**
     * 查询数据库表格信息
     * @param tables
     * @return 返回表格列表信息
     */
    default List<GenTable> selectDbTableList(String tables){
        String sql=STR."""
                SELECT
                    t.table_name,
                    d.description AS table_comment
                FROM
                    information_schema.tables t
                LEFT JOIN
                    pg_class c
                    ON c.relname = t.table_name
                LEFT JOIN
                    pg_description d
                    ON d.objoid = c.oid
                WHERE
                    t.table_name NOT LIKE 'qrtz\\_%'
                    AND t.table_name NOT LIKE 'gen\\_%'
                    AND t.table_schema = current_schema()
                    table_name in (?)
                    ;
                """;
        List<Row> rows = Db.selectListBySql(sql,tables);
        return RowUtil.toObjectList(rows,GenTable.class);
    }
}
