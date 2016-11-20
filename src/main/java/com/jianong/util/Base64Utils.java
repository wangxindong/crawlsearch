package com.jianong.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public final class Base64Utils {

	private Base64Utils() {
	}

	/**
	 * base64加密
	 * 
	 * @param input
	 * @return
	 */
	public static String encode(String input) {
		return Base64.encode(input.getBytes());
	}

	/**
	 * 解密
	 * 
	 * @param input
	 * @return
	 */
	public static String decode(String input) {
		return new String(Base64.decode(input));
	}
	
}
