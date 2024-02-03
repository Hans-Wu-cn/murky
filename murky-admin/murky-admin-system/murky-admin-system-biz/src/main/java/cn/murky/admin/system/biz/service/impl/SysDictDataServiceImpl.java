package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.biz.mq.DictRedisMqTemplate;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.admin.system.biz.contant.DictContant;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.mapper.SysDictDataMapper;
import cn.murky.admin.system.biz.mapper.SysDictTypeMapper;
import cn.murky.admin.system.biz.service.ISysDictTypeService;
import cn.murky.core.exception.ServiceException;
import com.mybatisflex.core.util.SqlUtil;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static cn.murky.core.constant.ErrorConstant.DELETE_ERROR;

/**
 * 字典数据Service
 *
 * @Author hans
 */
@Component
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
    @Inject
    private RedisClient redisClient;

    @Inject
    private SysDictTypeMapper sysDictTypeMapper;

    @Inject
    private DictRedisMqTemplate dictRedisMqTemplate;

    @Inject
    private ISysDictTypeService iSysDictTypeService;

    /**
     * 重写save方法加入缓存机制
     *
     * @param sysDictData
     * @return
     */
    @Tran
    @Override
    public boolean save(SysDictData sysDictData) {
        boolean bool = SqlUtil.toBool(this.getMapper().insert(sysDictData, true));

        if (bool) {
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<SysDictData> dictList = redisHash.getAndDeserialize(sysDictData.getDictType(), (new ArrayList<SysDictData>(){}).getClass());
            dictList = Optional.ofNullable(dictList).orElseGet(ArrayList::new);
            dictList.add(sysDictData);
            dictList.sort(Comparator.comparing(SysDictData::getDictSort));
            redisHash.putAndSerialize(sysDictData.getDictType(),dictList);
        }
        return bool;
    }

    /**
     * 重写修改方法加入缓存机制
     *
     * @param sysDictData
     * @return
     */
    @Tran
    @Override
    public boolean updateById(SysDictData sysDictData) {
        String dictType = sysDictData.getDictType();
        sysDictData.setDictType(null);
        boolean bool = this.updateById(sysDictData, true);
        if (bool) {
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<SysDictData> dictList = redisHash.getAndDeserialize(dictType, (new ArrayList<SysDictData>(){}).getClass());
            dictList.forEach(item->{
                if (item.getDictCode().equals(sysDictData.getDictCode())){
                    item=sysDictData;
                }
            });
            dictList.sort(Comparator.comparing(SysDictData::getDictSort));
            redisHash.putAndSerialize(dictType,dictList);
        }
        return bool;
    }


    @Tran
    @Override
    public boolean removeById(Serializable id) {
        SysDictData sysDictData = mapper.selectOneById(id);
        boolean bool = SqlUtil.toBool(this.getMapper().deleteById(id));
        if(bool){
            RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
            List<SysDictData> dictList = redisHash.getAndDeserialize(sysDictData.getDictType(),  (new ArrayList<SysDictData>(){}).getClass());
            dictList = dictList.stream().filter(item -> !item.getDictCode().equals(id)).collect(Collectors.toList());
            redisHash.putAndSerialize(sysDictData.getDictType(),dictList);

        }
        return bool;
    }

    @Override
    public List<SysDictData> getI18nDict() {
        return getDict(DictContant.I18N_LANGUAGE_DICT_KEY);
    }

    @Override
    public List<SysDictData> getDict(String dictType) {
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        if(redisHash!=null){
            return redisHash.getAndDeserialize(dictType, (new ArrayList<SysDictData>() {
            }).getClass());
        }
        return sysDictTypeMapper.selectSysDict(dictType).getSysDictDataList();
    }

    @Override
    public List<SysDictData> getAllDict() {
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        List<SysDictData> result=new ArrayList<>();
        if(redisHash!=null){
            for (String key : redisHash.keySet()) {
                List<SysDictData>  sysDictDataList = redisHash.getAndDeserialize(key, (new ArrayList<SysDictData>() {
                }).getClass());
                result.addAll(sysDictDataList);
            }
        }
        return result;
    }

    @Override
    public boolean updateDict(SysDictData sysDictData) {
        boolean b = updateById(sysDictData);
        if(b){
            iSysDictTypeService.refreshDict();
            dictRedisMqTemplate.publish();
        }
        return b;
    }

    @Override
    public boolean addDict(SysDictData sysDictData) {
        boolean b = save(sysDictData);
        if (b) {
            iSysDictTypeService.refreshDict();
            dictRedisMqTemplate.publish();
        }
        return b;
    }

    @Override
    public boolean removeDict(Long id) {
        SysDictData sysDictData = getById(id);
        Optional.ofNullable(sysDictData).orElseThrow(()->new ServiceException(DELETE_ERROR));
        boolean b = removeById(id);
        if (b) {
            iSysDictTypeService.refreshDict();
            dictRedisMqTemplate.publish();
        }
        return b;
    }
}
