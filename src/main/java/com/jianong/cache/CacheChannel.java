package com.jianong.cache;

import java.util.List;

/**
 * 缓存的通道类
 */
public interface CacheChannel {

    public static final byte LEVEL_1 = 1;
    public static final byte LEVEL_2 = 2;

    /**
     * 获取指定缓存空间的缓存
     * @param region
     * @param key
     * @return
     */
    public CacheObject get(String region, Object key);

    /**
     * 设置缓存
     * @param region
     * @param key
     * @param value
     */
    public void set(String region, Object key, Object value);

    /**
     * 删除缓存
     * @param region
     * @param key
     */
    public void evict(String region, Object key);

    /**
     * 批量移除缓存
     * @param region
     * @param keys
     */
    public void betchEvict(String region, List keys);

    /**
     * 获取缓存中的所有的箭
     * @param region
     * @return
     */
    public List keys(String region);


    public void clear(String region) throws CacheException;

    public void close() throws CacheException;



}
