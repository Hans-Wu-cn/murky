package cn.murky.admin.system.biz.mapper;

import cn.murky.admin.core.enums.CommonStatus;
import cn.murky.admin.system.biz.domain.bo.SysDictBo;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import cn.murky.admin.system.biz.domain.entity.table.SysDictDataTableDef;
import cn.murky.admin.system.biz.domain.entity.table.SysDictTypeTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;

import java.util.List;

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    /**
     * 查询所有正常状态字典数据以及字典分类
     * @return SysDictBo
     */
    default List<SysDictBo> selectSysDict() {
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
     * @return SysDictBo
     */
    default SysDictBo selectSysDict(String dictType) {
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
