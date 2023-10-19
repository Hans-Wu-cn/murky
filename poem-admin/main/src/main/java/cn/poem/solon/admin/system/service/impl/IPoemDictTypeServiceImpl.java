package cn.poem.solon.admin.system.service.impl;

import cn.poem.solon.admin.system.contant.DictContant;
import cn.poem.solon.admin.system.domain.bo.PoemDictBo;
import cn.poem.solon.admin.system.domain.entity.PoemDictType;
import cn.poem.solon.admin.system.mapper.PoemDictTypeMapper;
import cn.poem.solon.admin.system.service.IPoemDictTypeService;
import com.mybatisflex.solon.service.impl.ServiceImpl;
import org.noear.redisx.RedisClient;
import org.noear.redisx.plus.RedisHash;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * 字典类型service
 *
 * @Author hans
 */
@Component
public class IPoemDictTypeServiceImpl extends ServiceImpl<PoemDictTypeMapper, PoemDictType> implements IPoemDictTypeService {

    @Inject
    private RedisClient redisClient;

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
    }
}
