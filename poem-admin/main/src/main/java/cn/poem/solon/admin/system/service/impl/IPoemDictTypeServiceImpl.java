package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.core.exception.ServiceException;
import cn.poem.solon.admin.system.contant.DictContant;
import cn.poem.solon.admin.system.domain.bo.PoemDictBo;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.mapper.PoemDictTypeMapper;
import cn.poem.solon.admin.system.service.IPoemDictDataService;
import cn.poem.solon.admin.system.service.IPoemDictTypeService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.util.List;

/**
 * 字典类型service
 *
 * @Author hans
 */
@Component
@Slf4j
public class IPoemDictTypeServiceImpl extends ServiceImpl<PoemDictTypeMapper, PoemDictType> implements IPoemDictTypeService {

    @Inject
    private RedisClient redisClient;
    @Inject
    private IPoemDictTypeService iPoemDictTypeService;
    @Inject
    private IPoemDictDataService iPoemDictDataService;
    /**
     * 刷新缓存
     */
    @Init
    @Override
    public void refreshDict() {
        List<PoemDictBo> poemDictBos = mapper.selectPoemDict();
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE);
        for (PoemDictBo poemDictBo : poemDictBos) {
            redisHash.putAndSerialize(poemDictBo.getDictType(),poemDictBo.getPoemDictDatas());
        }
        log.info("初始化字典缓存");
    }

    @Tran
    @Override
    public boolean edit(PoemDictType poemDictType) {
        PoemDictType dictType = iPoemDictTypeService.getById(poemDictType.getDictTypeId());
        boolean b = iPoemDictTypeService.updateById(poemDictType);
        if(b){
            List<PoemDictData> dictList = iPoemDictDataService.getDict(dictType.getDictType());
            for (PoemDictData poemDictData : dictList) {
                poemDictData.setDictType(poemDictType.getDictType());
            }
            return iPoemDictDataService.updateBatch(dictList);
        }
        throw new ServiceException("修改失败");
    }
}