package com.zrkj.commons.utils.password;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zrkj.commons.exception.ApplicationException;
import com.zrkj.commons.exception.DecryptionException;

/**
 * AES工具
 * 
 * @author zx
 *
 */
public class AesUtils {
	private static final Logger logger = LoggerFactory.getLogger(AesUtils.class);

	private static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede

	private static Key getKey(String strKey) {
		try {
			if (strKey == null) {
				strKey = "";
			}
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes("utf-8"));
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException(" 初始化密钥出现异常 ");
		}
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 * @param password
	 * @return 二进制密文
	 */
	public static Ciphertext encryptAES(String content, String password) {
		try {
			SecureRandom sr = new SecureRandom();
			Key secureKey = getKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
			return new Ciphertext(cipher.doFinal(content.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 *            二进制密文
	 * @param password
	 * @return 二进制原文
	 */
	public static Ciphertext decryptAES(byte[] content, String password) {
		try {
			SecureRandom sr = new SecureRandom();
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			Key secureKey = getKey(password);
			cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
			return new Ciphertext(cipher.doFinal(content));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error(e.getMessage(), e);
			throw new DecryptionException(e.getMessage(), e);
		}
	}
}
