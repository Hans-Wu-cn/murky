package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.DictContant;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.mapper.PoemDictDataMapper;
import cn.poem.solon.admin.system.mapper.PoemDictTypeMapper;
import cn.poem.solon.admin.system.service.IPoemDictDataService;
import com.mybatisflex.core.util.SqlUtil;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典数据Service
 *
 * @Author hans
 */
@Component
public class IPoemDictDataServiceImpl extends ServiceImpl<PoemDictDataMapper, PoemDictData> implements IPoemDictDataService {
    @Inject
    private RedisClient redisClient;

    @Inject
    private PoemDictTypeMapper poemDictTypeMapper;

    /**
     * 重写save方法加入缓存机制
     *
     * @param poemDictData
     * @return
     */
    @Tran
    @Override
    public boolean save(PoemDictData poemDictData) {
        boolean bool = SqlUtil.toBool(this.getMapper().insert(poemDictData, true));

        if (bool) {
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(poemDictData.getDictType(), (new ArrayList<PoemDictData>(){}).getClass());
            dictList = Optional.ofNullable(dictList).orElseGet(ArrayList::new);
            dictList.add(poemDictData);
            dictList.sort(Comparator.comparing(PoemDictData::getDictSort));
            redisHash.putAndSerialize(poemDictData.getDictType(),dictList);
        }
        return bool;
    }

    /**
     * 重写修改方法加入缓存机制
     *
     * @param poemDictData
     * @return
     */
    @Tran
    @Override
    public boolean updateById(PoemDictData poemDictData) {
        String dictType = poemDictData.getDictType();
        poemDictData.setDictType(null);
        boolean bool = this.updateById(poemDictData, true);
        if (bool) {
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(dictType, (new ArrayList<PoemDictData>(){}).getClass());
            dictList.forEach(item->{
                if (item.getDictCode().equals(poemDictData.getDictCode())){
                    item=poemDictData;
                }
            });
            dictList.sort(Comparator.comparing(PoemDictData::getDictSort));
            redisHash.putAndSerialize(dictType,dictList);
        }
        return bool;
    }


    @Tran
    @Override
    public boolean removeById(Serializable id) {
        PoemDictData poemDictData = mapper.selectOneById(id);
        boolean bool = SqlUtil.toBool(this.getMapper().deleteById(id));
        if(bool){
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(poemDictData.getDictType(),  (new ArrayList<PoemDictData>(){}).getClass());
            dictList = dictList.stream().filter(item -> !item.getDictCode().equals(id)).collect(Collectors.toList());
            redisHash.putAndSerialize(poemDictData.getDictType(),dictList);

        }
        return bool;
    }

    @Override
    public List<PoemDictData> getI18nDict() {
        return getDict(DictContant.I18N_LANGUAGE_DICT_KEY);
    }

    @Override
    public List<PoemDictData> getDict(String dictType) {
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        if(redisHash!=null){
            return redisHash.getAndDeserialize(dictType, (new ArrayList<PoemDictData>() {
            }).getClass());
        }
        return poemDictTypeMapper.selectPoemDict(dictType).getPoemDictDatas();
    }
}
