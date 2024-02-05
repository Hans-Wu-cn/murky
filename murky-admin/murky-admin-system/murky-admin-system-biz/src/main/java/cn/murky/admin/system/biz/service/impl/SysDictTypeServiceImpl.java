package cn.murky.admin.system.biz.service.impl;

import cn.murky.admin.system.api.domian.bo.SysDictBO;
import cn.murky.admin.system.api.constant.DictContant;
import cn.murky.admin.system.api.domian.bo.SysDictDataBO;
import cn.murky.admin.system.biz.convert.SysDictConvert;
import cn.murky.admin.system.biz.domain.entity.SysDictData;
import cn.murky.admin.system.biz.domain.entity.SysDictType;
import cn.murky.admin.system.biz.mapper.SysDictTypeMapper;
import cn.murky.admin.system.biz.service.ISysDictDataService;
import cn.murky.admin.system.biz.service.ISysDictTypeService;
import cn.murky.common.utils.CollectionUtils;
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

import static cn.murky.core.constant.ErrorConstant.EDIT_ERROR;

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
    public List<SysDictBO> refreshDict() {
        List<SysDictBO> sysDictBos = mapper.selectSysDict();
        RedisHash redisHash = redisClient.getHash(DictContant.DICT_CACHE_KEY);
        redisHash.clear();
        for (SysDictBO sysDictBo : sysDictBos) {
            sysDictBo.getSysDictDataList().forEach(item->item.setDictType(sysDictBo.getDictType()));
            redisHash.putAndSerialize(sysDictBo.getDictType(), sysDictBo.getSysDictDataList());
        }
        log.info("初始化字典缓存");
        return sysDictBos;
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
            List<SysDictDataBO> dictList = iSysDictDataService.getDict(dictType.getDictType());
            if(CollectionUtils.isNotEmpty(dictList)){
                for (SysDictDataBO sysDictData : dictList) {
                    sysDictData.setDictType(sysDictType.getDictType());
                }
                List<SysDictData> sysDictDataList = SysDictConvert.INSTANCES.toEntity(dictList);
                return iSysDictDataService.updateBatch(sysDictDataList);
            }
            return true;
        }
        throw new ServiceException(EDIT_ERROR);
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
        List<SysDictBO> sysDictBos = mapper.selectSysDict();
        Map<String,String> hashMap = new HashMap<>();
        for (SysDictBO sysDictBo : sysDictBos) {
            sysDictBo.getSysDictDataList().forEach(item->item.setDictType(sysDictBo.getDictType()));
            hashMap.put(sysDictBo.getDictType(), ONode.serialize(sysDictBo.getSysDictDataList()));
        }
        redisHash.putAll(hashMap);
        log.info("初始化字典缓存");
    }
}
