package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.SystemContant;
import cn.poem.solon.admin.system.domain.bo.PoemDictBo;
import cn.poem.solon.admin.system.domain.entity.PoemDictData;
import cn.poem.solon.admin.system.mapper.PoemDictDataMapper;
import cn.poem.solon.admin.system.mapper.PoemDictTypeMapper;
import cn.poem.solon.admin.system.service.IPoemDictDataService;
import com.mybatisflex.core.util.SqlUtil;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    @Override
    public boolean save(PoemDictData poemDictData) {
        boolean bool = SqlUtil.toBool(this.getMapper().insert(poemDictData, true));

        if (bool) {
            RedisHash redisHash = redisClient.getHash(SystemContant.DICT_CACHE);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(poemDictData.getDictType(), (new ArrayList<PoemDictData>(){}).getClass());
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
    @Override
    public boolean updateById(PoemDictData poemDictData) {
        boolean bool = this.updateById(poemDictData, true);
        if (bool) {
            RedisHash redisHash = redisClient.getHash(SystemContant.DICT_CACHE);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(poemDictData.getDictType(), (new ArrayList<PoemDictData>(){}).getClass());
            dictList.forEach(item->{
                if (item.getDictCode().equals(poemDictData.getDictCode())){
                    item=poemDictData;
                }
            });
            dictList.sort(Comparator.comparing(PoemDictData::getDictSort));
            redisHash.putAndSerialize(poemDictData.getDictType(),dictList);
        }
        return bool;
    }


    @Override
    public boolean removeById(Serializable id) {
        boolean bool = SqlUtil.toBool(this.getMapper().deleteById(id));
        if(bool){
            PoemDictData poemDictData = mapper.selectOneById(id);
            RedisHash redisHash = redisClient.getHash(SystemContant.DICT_CACHE);
            List<PoemDictData> dictList = redisHash.getAndDeserialize(poemDictData.getDictType(),  (new ArrayList<PoemDictData>(){}).getClass());
            dictList = dictList.stream().filter(item -> !item.getDictCode().equals(id)).collect(Collectors.toList());
            redisHash.putAndSerialize(poemDictData.getDictType(),dictList);

        }
        return bool;
    }

}
