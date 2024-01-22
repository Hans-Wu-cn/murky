package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.biz.domain.bo.SysDictBo;
import cn.murky.admin.system.biz.contant.DictContant;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import cn.murky.admin.system.biz.mapper.SysDictTypeMapper;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.admin.system.biz.service.ISysDictTypeService;
import cn.murky.core.exception.ServiceException;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典类型service
 *
 * @Author hans
 */
@Component
@Slf4j
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Inject
    private RedisClient redisClient;
    @Inject
    private ISysDictTypeService iSysDictTypeService;
    @Inject
    private ISysDictDataService iSysDictDataService;

    /**
     * 刷新缓存
     */
    @Override
    public void refreshDict() {
        List<SysDictBo> sysDictBos = mapper.selectSysDict();
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        redisHash.clear();
        for (SysDictBo sysDictBo : sysDictBos) {
            redisHash.putAndSerialize(sysDictBo.getDictType(), sysDictBo.getSysDictDataList());
        }
        log.info("初始化字典缓存");
    }


    /**
     * 重写修改方法
     */
    @Tran
    @Override
    public boolean edit(SysDictType sysDictType) {
        SysDictType dictType = iSysDictTypeService.getById(sysDictType.getId());
        boolean b = iSysDictTypeService.updateById(sysDictType);
        if (b) {
            // 修改对应的data
            List<SysDictData> dictList = iSysDictDataService.getDict(dictType.getDictType());
            for (SysDictData sysDictData : dictList) {
                sysDictData.setDictType(sysDictType.getDictType());
            }
            return iSysDictDataService.updateBatch(dictList);
        }
        throw new ServiceException("修改失败");
    }

    /**
     * 启动服务时初始化字典换粗
     */
    @Init
    public void initDict() {
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        // 如果已经被初始化过则不需要在初始化
        if(!redisHash.isEmpty()){
            return;
        }
        List<SysDictBo> sysDictBos = mapper.selectSysDict();
        Map<String,String> hashMap = new HashMap<>();
        for (SysDictBo sysDictBo : sysDictBos) {
            hashMap.put(sysDictBo.getDictType(), ONode.serialize(sysDictBo.getSysDictDataList()));
        }
        redisHash.putAll(hashMap);
        log.info("初始化字典缓存");
    }
}
