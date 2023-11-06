package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.system.domain.bo.PoemDictBo;
import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.domain.entity.table.PoemDictDataTableDef;
import cn.poem.solon.admin.system.domain.entity.table.PoemDictTypeTableDef;
import cn.poem.solon.admin.system.enums.DictStatus;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;

import java.util.List;

public interface PoemDictTypeMapper extends BaseMapper<PoemDictType> {
    PoemDictTypeTableDef POEM_DICT_TYPE_TABLE = PoemDictTypeTableDef.POEM_DICT_TYPE;
    PoemDictDataTableDef POEM_DICT_DATA_TABLE = PoemDictDataTableDef.POEM_DICT_DATA;


    /**
     * 查询所有正常状态字典数据以及字典分类
     * @return PoemDictBo
     */
    default List<PoemDictBo> selectPoemDict() {
        return QueryChain.of(this).select().from(POEM_DICT_TYPE_TABLE)
                .leftJoin(POEM_DICT_DATA_TABLE).on(POEM_DICT_TYPE_TABLE.DICT_TYPE.eq(POEM_DICT_DATA_TABLE.DICT_TYPE))
                .where(POEM_DICT_TYPE_TABLE.STATUS.eq(DictStatus.NORMAL))
                .and(POEM_DICT_DATA_TABLE.STATUS.eq(DictStatus.NORMAL))
                .orderBy(POEM_DICT_DATA_TABLE.DICT_SORT.asc()).listAs(PoemDictBo.class);
    }

    /**
     * 字典类型查询正常状态字典数据以及字典分类
     * @return PoemDictBo
     */
    default PoemDictBo selectPoemDict(String dictType) {
        return QueryChain.of(this).select().from(POEM_DICT_TYPE_TABLE)
                .leftJoin(POEM_DICT_DATA_TABLE).on(POEM_DICT_TYPE_TABLE.DICT_TYPE.eq(POEM_DICT_DATA_TABLE.DICT_TYPE))
                .where(POEM_DICT_TYPE_TABLE.STATUS.eq(DictStatus.NORMAL))
                .and(POEM_DICT_DATA_TABLE.STATUS.eq(DictStatus.NORMAL))
                .and(POEM_DICT_TYPE_TABLE.DICT_TYPE.eq(dictType))
                .orderBy(POEM_DICT_DATA_TABLE.DICT_SORT.asc()).oneAs(PoemDictBo.class);
    }

}
