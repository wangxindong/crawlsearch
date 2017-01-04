package com.jianong.cache;

/**
 * cache 异常
 */
public class CacheException extends RuntimeException {


    public CacheException(String str){
        super(str);
    }

    public CacheException(String str, Throwable e){
        super(str, e);
    }

    public CacheException(Throwable e){
        super(e);
    }
}
