package com.jianong.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * 
 * @author wxd
 *
 */
public class PropertiesUtils {
	private static PropertiesUtils propertiesUtils;
	private static Properties properties;
	private static String fileName;

	private PropertiesUtils() {

	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static PropertiesUtils getInstance() {
		if (null == propertiesUtils) {
			propertiesUtils = new PropertiesUtils();
		}
		return propertiesUtils;
	}

	/**
	 * 加载配置文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadProperties(String fileName) {
		properties = new Properties();
		try {
			properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("io错误");
		}
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return properties.getProperty(key);
	}

}
