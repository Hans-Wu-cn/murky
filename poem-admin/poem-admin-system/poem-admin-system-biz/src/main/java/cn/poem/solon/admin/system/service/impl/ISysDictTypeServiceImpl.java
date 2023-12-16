package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.DictContant;
import cn.poem.solon.admin.system.domain.bo.SysDictBo;
import cn.poem.solon.admin.system.domain.entity.SysDictData;
import cn.poem.solon.admin.system.domain.entity.SysDictType;
import cn.poem.solon.admin.system.mapper.SysDictTypeMapper;
import cn.poem.solon.admin.system.service.ISysDictDataService;
import cn.poem.solon.admin.system.service.ISysDictTypeService;
import cn.poem.solon.exception.ServiceException;
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
public class ISysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Inject
    private RedisClient redisClient;
    @Inject
    private ISysDictTypeService iPoemDictTypeService;
    @Inject
    private ISysDictDataService iPoemDictDataService;

    /**
     * 刷新缓存
     */
    @Override
    public void refreshDict() {
        List<SysDictBo> poemDictBos = mapper.selectPoemDict();
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        redisHash.clear();
        for (SysDictBo poemDictBo : poemDictBos) {
            redisHash.putAndSerialize(poemDictBo.getDictType(), poemDictBo.getSysDictDataList());
        }
        log.info("初始化字典缓存");
    }


    /**
     * 重写修改方法
     */
    @Tran
    @Override
    public boolean edit(SysDictType sysDictType) {
        SysDictType dictType = iPoemDictTypeService.getById(sysDictType.getDictTypeId());
        boolean b = iPoemDictTypeService.updateById(sysDictType);
        if (b) {
            // 修改对应的data
            List<SysDictData> dictList = iPoemDictDataService.getDict(dictType.getDictType());
            for (SysDictData poemDictData : dictList) {
                poemDictData.setDictType(sysDictType.getDictType());
            }
            return iPoemDictDataService.updateBatch(dictList);
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
        List<SysDictBo> poemDictBos = mapper.selectPoemDict();
        Map<String,String> hashMap = new HashMap<>();
        for (SysDictBo poemDictBo : poemDictBos) {
            hashMap.put(poemDictBo.getDictType(), ONode.serialize(poemDictBo.getSysDictDataList()));
        }
        redisHash.putAll(hashMap);
        log.info("初始化字典缓存");
    }
}
