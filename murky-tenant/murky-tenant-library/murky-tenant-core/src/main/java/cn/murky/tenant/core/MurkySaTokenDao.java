package cn.murky.tenant.core;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.dao.SaTokenDao;

import cn.dev33.satoken.util.SaFoxUtil;
import cn.murky.tenant.core.utils.RedisUtils;
import org.noear.redisx.plus.RedisBucket;
import org.noear.solon.annotation.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.murky.tenant.core.constant.Constants.TENANT_ID_HEADER;

@Component
public class MurkySaTokenDao implements SaTokenDao {

    private ScopedValue<Long> scopedTanentId = ScopedValue.newInstance();

    /**
     * 获取Value，如无返空
     */
    @Override
    public String get(String key) {

        return getRedisBucket().get(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout > 0 || timeout == SaTokenDao.NEVER_EXPIRE) {
            getRedisBucket().store(key, value, (int) timeout);
        }
    }

    /**
     * 修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     */
    @Override
    public void delete(String key) {
        getRedisBucket().remove(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getTimeout(String key) {
        return getRedisBucket().ttl(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        RedisBucket redisBucket = getRedisBucket();
        if (redisBucket.exists(key)) {
            redisBucket.delay(key, (int) timeout);
        }
    }

    /**
     * 获取Object，如无返空
     */
    @Override
    public Object getObject(String key) {
        return getRedisBucket().getAndDeserialize(key, Object.class);
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout > 0 || timeout == SaTokenDao.NEVER_EXPIRE) {
            getRedisBucket().storeAndSerialize(key, object, (int) timeout);
        }
    }

    /**
     * 更新Object (过期时间不变)
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     */
    @Override
    public void deleteObject(String key) {
        getRedisBucket().remove(key);
    }


    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getObjectTimeout(String key) {
        return getRedisBucket().ttl(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        RedisBucket redisBucket = getRedisBucket();
        if (redisBucket.exists(key)) {
            redisBucket.delay(key, (int) timeout);
        }
    }

    /**
     * 搜索数据
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        Set<String> keys = getRedisBucket().keys(prefix + "*" + keyword + "*");
        List<String> list = new ArrayList<String>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }

    private RedisBucket getRedisBucket() {
        return RedisUtils.get().getBucket();
    }

}
