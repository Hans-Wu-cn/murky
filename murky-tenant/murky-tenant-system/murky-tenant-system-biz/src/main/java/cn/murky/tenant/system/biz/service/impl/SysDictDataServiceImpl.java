package cn.murky.tenant.system.biz.service.impl;

import cn.murky.tenant.core.utils.RedisUtils;
import cn.murky.tenant.system.api.constant.DictContant;
import cn.murky.tenant.system.api.domain.bo.SysDictDataBO;
import cn.murky.tenant.system.biz.domian.entity.SysDictData;
import cn.murky.tenant.system.biz.mapper.SysDictDataMapper;
import cn.murky.tenant.system.biz.mapper.SysDictTypeMapper;
import cn.murky.tenant.system.biz.service.ISysDictDataService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.ArrayList;
import java.util.List;


@Component
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
    @Inject
    private SysDictTypeMapper sysDictTypeMapper;
    @Override
    public List<SysDictDataBO> getDict(String dictType) {
        RedisHash redisHash = RedisUtils.get().getHash(DictContant.DICT_CACHE_KEY);
        if(redisHash!=null){
            return redisHash.getAndDeserialize(dictType, (new ArrayList<SysDictDataBO>() {
            }).getClass());
        }
        return sysDictTypeMapper.selectSysDict(dictType).getSysDictDataList();
    }
}
