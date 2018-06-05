package com.zrkj.commons.utils.password;

import org.apache.commons.codec.binary.Base64;

/**
 * BASE64工具
 * 
 * @author zx
 *
 */
public class Base64Utils {
	public static byte[] encoder(byte[] content) {
		return Base64.encodeBase64(content);
	}

	public static String encodeToString(byte[] content) {
		return Base64.encodeBase64String(content);
	}

	public static byte[] decoder(byte[] content) {
		return Base64.decodeBase64(content);
	}

	public static byte[] decoder(String content) {
		return Base64.decodeBase64(content);
	}

	public static String decodeToString(byte[] content) {
		return new String(Base64.decodeBase64(content));
	}
}
