package com.jianong.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("all")
public final class SynchronizedPageData extends ConcurrentHashMap<String, Object> implements Map<String, Object> {

	private static final long serialVersionUID = 1790064694168093654L;
	private static Map<String, Object> paraMap = null;
	private HttpServletRequest request = null;

	public SynchronizedPageData() {
		paraMap = new ConcurrentHashMap();
	}

	public SynchronizedPageData(HttpServletRequest request) {
		this.request = request;
		// 参数map
		paraMap = request.getParameterMap();
		Map<String, Object> returnMap = new ConcurrentHashMap();
		Iterator entries = paraMap.entrySet().iterator();
		String name = null;
		String value = null;
		Map.Entry entry = null;

		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		paraMap = returnMap;
	}

	/**
	 * 获取键值
	 */
	@Override
	public Object get(Object key) {
		Object obj = null;
		// 判断是否是数组
		if (paraMap.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) paraMap.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = paraMap.get(key);
		}
		return obj;
	}

	/**
	 * 获取string类型的值
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return (String) get(key);
	}

	public Object put(String key, String value) {
		return paraMap.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return paraMap.remove(key);
	}

	@Override
	public void clear() {
		paraMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return paraMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return paraMap.containsValue(value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		paraMap.putAll(m);
	}

	@Override
	public int size() {
		return paraMap.size();
	}

	@Override
	public Collection<Object> values() {
		return paraMap.values();
	}
}
