package cn.poem.solon.admin.system.mapper;

import cn.poem.solon.admin.core.enums.CommonStatus;
import cn.poem.solon.admin.system.domain.bo.SysDictBo;
import cn.poem.solon.admin.system.domain.entity.SysDictType;
import cn.poem.solon.admin.system.domain.entity.table.SysDictDataTableDef;
import cn.poem.solon.admin.system.domain.entity.table.SysDictTypeTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;

import java.util.List;

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    /**
     * 查询所有正常状态字典数据以及字典分类
     * @return PoemDictBo
     */
    default List<SysDictBo> selectPoemDict() {
        SysDictDataTableDef SYS_DICT_DATA = SysDictDataTableDef.SYS_DICT_DATA;
        SysDictTypeTableDef SYS_DICT_TYPE = SysDictTypeTableDef.SYS_DICT_TYPE;
        return QueryChain.of(this).select().from(SYS_DICT_TYPE)
                .leftJoin(SYS_DICT_DATA).on(SYS_DICT_TYPE.DICT_TYPE.eq(SYS_DICT_DATA.DICT_TYPE))
                .where(SYS_DICT_TYPE.STATUS.eq(CommonStatus.NORMAL))
                .and(SYS_DICT_DATA.STATUS.eq(CommonStatus.NORMAL))
                .orderBy(SYS_DICT_DATA.DICT_SORT.asc()).listAs(SysDictBo.class);
    }

    /**
     * 字典类型查询正常状态字典数据以及字典分类
     * @return PoemDictBo
     */
    default SysDictBo selectPoemDict(String dictType) {
        SysDictDataTableDef SYS_DICT_DATA = SysDictDataTableDef.SYS_DICT_DATA;
        SysDictTypeTableDef SYS_DICT_TYPE = SysDictTypeTableDef.SYS_DICT_TYPE;
        return QueryChain.of(this).select().from(SYS_DICT_TYPE)
                .leftJoin(SYS_DICT_DATA).on(SYS_DICT_TYPE.DICT_TYPE.eq(SYS_DICT_DATA.DICT_TYPE))
                .where(SYS_DICT_TYPE.STATUS.eq(CommonStatus.NORMAL))
                .and(SYS_DICT_DATA.STATUS.eq(CommonStatus.NORMAL))
                .and(SYS_DICT_TYPE.DICT_TYPE.eq(dictType))
                .orderBy(SYS_DICT_DATA.DICT_SORT.asc()).oneAs(SysDictBo.class);
    }

}
