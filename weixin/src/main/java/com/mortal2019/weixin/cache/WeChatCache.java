package com.mortal2019.weixin.cache;


import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 15:36
 */
public final class WeChatCache {


    private final long cacheTime; // 缓存时间
    private final Map<String, CacheObject> cachedData; // 存储缓存的哈希表

    // 缓存对象包含数据和过期时间戳
    private static class CacheObject {
        public Object data;
        public long timestamp;

        private CacheObject(Object data, long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
    }

    public WeChatCache(long cacheTime) {
        this.cacheTime = cacheTime;
        this.cachedData = new HashMap<>();
    }

    // 添加缓存数据
    public synchronized void put(String key, Object data) {
        cachedData.put(key, new CacheObject(data, System.currentTimeMillis() + cacheTime));
    }

    // 获取缓存数据
    public synchronized Object get(String key) {
        CacheObject cacheObject = cachedData.get(key);
        if (cacheObject == null) {
            return null;
        } else if (cacheObject.timestamp < System.currentTimeMillis()) { // 如果缓存过期则移除缓存
            cachedData.remove(key);
            return null;
        } else {
            return cacheObject.data;
        }
    }

    // 移除缓存数据
    public synchronized void remove(String key) {
        cachedData.remove(key);
    }

    // 获取缓存数据项数量
    public synchronized int size() {
        return cachedData.size();
    }

    // 清空缓存
    public synchronized void clear() {
        cachedData.clear();
    }
}
