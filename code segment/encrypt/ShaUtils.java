package com.zrkj.commons.utils.password;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zrkj.commons.exception.ApplicationException;

/**
 * sha工具
 * 
 * @author zx
 *
 */
public class ShaUtils {
	private static final Logger logger = LoggerFactory.getLogger(ShaUtils.class);

	/**
	 * 用UUDID生成SHA1
	 * 
	 * @return
	 */
	public static String sha1() {
		return sha1(UUID.randomUUID().toString());
	}

	/**
	 * 把文本加密生成SHA1
	 * 
	 * @param decript
	 * @return
	 */
	public static String sha1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes("utf-8"));
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ApplicationException(e.getMessage(), e);
		}
	}
}
