package com.jianong.util.serializes;

import java.io.IOException;

/**
 * 序列化接口
 */
public interface Serializer {

    public String name();

    /**
     * 序列化数据
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public byte[] serialize(Object obj) throws IOException;

    /**
     * 反序列化
     *
     * @param bs
     * @return
     * @throws IOException
     */
    public Object deSerialize(byte[] bs) throws IOException;
}
