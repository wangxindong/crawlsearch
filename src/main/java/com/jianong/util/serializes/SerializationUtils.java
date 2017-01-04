package com.jianong.util.serializes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 序列化工具包
 */
public class SerializationUtils {
    private final  static Logger LOGGER = LoggerFactory.getLogger(SerializationUtils.class);
    private static Serializer serializer;

    /**
     * 创建序列化使用的对象
     */
    static {

    }


    public static byte[] serialize(Object obj) throws IOException {
        return serializer.serialize(obj);
    }

    public static Object deserialize(byte[] bs) throws IOException {
        return serializer.deSerialize(bs);
    }


}
