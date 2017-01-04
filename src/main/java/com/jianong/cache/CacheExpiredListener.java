package com.jianong.cache;

/**
 * 用于监听缓存中对象超时
 */
public interface CacheExpiredListener {

    /**
     * 当清除超时的对象使用
     *
     * @param region
     * @param key
     */
    public void notifyElementExpired(String region, Object key);
}
