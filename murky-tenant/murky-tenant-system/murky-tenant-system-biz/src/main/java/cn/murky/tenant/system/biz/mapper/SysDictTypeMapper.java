package cn.murky.tenant.system.biz.mapper;

import cn.murky.tenant.system.api.domain.bo.SysDictBO;
import cn.murky.tenant.system.api.enums.CommonStatus;
import cn.murky.tenant.system.biz.domian.entity.SysDictType;
import cn.murky.tenant.system.biz.domian.entity.table.SysDictDataTableDef;
import cn.murky.tenant.system.biz.domian.entity.table.SysDictTypeTableDef;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;

import java.util.List;

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {


    /**
     * 字典类型查询正常状态字典数据以及字典分类
     * @return SysDictBo
     */
    default SysDictBO selectSysDict(String dictType) {
        SysDictDataTableDef SYS_DICT_DATA = SysDictDataTableDef.SYS_DICT_DATA;
        SysDictTypeTableDef SYS_DICT_TYPE = SysDictTypeTableDef.SYS_DICT_TYPE;
        return QueryChain.of(this).select().from(SYS_DICT_TYPE)
                .leftJoin(SYS_DICT_DATA).on(SYS_DICT_TYPE.DICT_TYPE.eq(SYS_DICT_DATA.DICT_TYPE))
                .where(SYS_DICT_TYPE.STATUS.eq(CommonStatus.NORMAL))
                .and(SYS_DICT_DATA.STATUS.eq(CommonStatus.NORMAL))
                .and(SYS_DICT_TYPE.DICT_TYPE.eq(dictType))
                .orderBy(SYS_DICT_DATA.DICT_SORT.asc()).oneAs(SysDictBO.class);
    }

}
