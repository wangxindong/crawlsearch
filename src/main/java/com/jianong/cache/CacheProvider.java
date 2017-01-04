package com.jianong.cache;

import java.util.Properties;

/**
 * 使用此接口提供缓存
 */
public interface CacheProvider {

    /**
     * 获取缓存的标识名称
     *
     * @return
     */
    public String name();

    /**
     * 创建缓存
     *
     * @param region
     * @param autoCreate
     * @param listener
     * @return
     * @throws CacheException
     */
    public Cache buildCache(String region, boolean autoCreate, CacheExpiredListener listener) throws CacheException;

    /**
     * 启动缓存
     *
     * @param prop
     */
    public void start(Properties prop);

    public void stop() throws CacheException;


}
