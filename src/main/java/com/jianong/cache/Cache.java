package com.jianong.cache;

import java.util.List;

/**
 * 缓存接口
 */
public interface Cache {

    /**
     * 获取缓存的对象
     * @return
     * @throws CacheException
     */
    public Object get(Object key) throws CacheException;

    public void put(Object key, Object value) throws CacheException;

    public void update(Object key, Object value) throws CacheException;

    /**
     * 获取key值
     * @return
     * @throws CacheException
     */
    public List keys() throws CacheException;

    /**
     * 批量移除缓存
     * @param keys
     * @throws CacheException
     */
    public void evict(List keys) throws CacheException;

    /**
     * 清理缓存
     * @throws CacheException
     */
    public void clear() throws CacheException;

    public void destory() throws CacheException;



}
