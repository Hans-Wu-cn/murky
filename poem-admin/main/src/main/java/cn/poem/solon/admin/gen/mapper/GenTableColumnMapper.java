package cn.poem.solon.admin.gen.mapper;

import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.domain.entity.table.GenTableColumnTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowUtil;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StringTemplate.STR;

public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {
    GenTableColumnTableDef GEN_TABLE_COLUMN = GenTableColumnTableDef.GEN_TABLE_COLUMN;

    /**
     * 根据tableId查询列数据
     * @param tableId 表格id
     */
    default List<GenTableColumn> selectByTableId(Long tableId) {
        return selectListByQuery(
                QueryWrapper.create().where(GEN_TABLE_COLUMN.TABLE_ID.eq(tableId))
                        .orderBy(GEN_TABLE_COLUMN.SORT.asc()));
    }

    default List<GenTableColumn> selectDbTableColumnsByName(String tableName){
        String sql = STR."""
        SELECT
        column_name,
                (case when is_nullable='NO' then 1 else 0 end) as is_required,
        CASE WHEN column_name IN (
                SELECT column_name
        FROM information_schema.table_constraints tc
        JOIN information_schema.key_column_usage kcu
        ON tc.constraint_name = kcu.constraint_name
        WHERE tc.constraint_type = 'PRIMARY KEY' AND tc.table_schema = col.table_schema AND tc.table_name =  col.table_name 
    ) THEN '1' ELSE '0' END AS is_pk,
                ordinal_position AS sort,
        (
                SELECT description
        FROM pg_description
        WHERE objoid = (
                SELECT oid
        FROM pg_class
        WHERE relname = '?' AND relnamespace = (
                SELECT oid
        FROM pg_namespace
        WHERE nspname =col.table_schema
            )
        ) AND objsubid = ordinal_position
    ) AS column_comment,
        CASE WHEN column_default LIKE 'nextval%' THEN '1' ELSE '0' END AS is_increment,
                data_type AS column_type
        FROM
        information_schema.columns as col
                WHERE
        table_schema = current_schema() AND table_name = '?'
        ORDER BY
        ordinal_position
        """;
        List<Row> rows = Db.selectListBySql(sql, tableName,tableName);

        return RowUtil.toObjectList(rows, GenTableColumn.class);
    }
}
