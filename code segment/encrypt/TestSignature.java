package com.zrkj.infrastructure.service.event.audit;

import java.security.GeneralSecurityException;

import org.apache.commons.codec.binary.Base64;

import com.zrkj.commons.codec.KeyUtils;
import com.zrkj.commons.codec.KeyUtils.Base64KeyPair;
import com.zrkj.commons.utils.password.ShaUtils;

/**
 * 
 * 
 * @date 2018年5月7日下午1:46:57
 * @author wujunbo
 *
 */
public class TestSignature {
	public static void main(String[] args) throws GeneralSecurityException {
		String json = "wujunbo";
		String sha1 = ShaUtils.sha1(json);
		System.out.println(sha1);
		System.out.println(Base64.encodeBase64String(sha1.getBytes()));
		
		Base64KeyPair pair = KeyUtils.generateKeyPair();
		
		System.out.println("------------------------------");
		String publicKey = pair.publicKey;
		String privateKey = pair.privateKey;
		System.out.println(privateKey);
		System.out.println(publicKey);
		
		System.out.println("------------------------------");
		byte[] sha_ = KeyUtils.encryptByPrivateKey(sha1.getBytes(), privateKey);
		String value_ = Base64.encodeBase64String(sha_);
		System.out.println(value_);
		
		System.out.println("------------------------------");
		byte[] bs_ = KeyUtils.decryptByPublicKey(value_, publicKey);
		System.out.println(Base64.encodeBase64String(bs_));
		
	}
}
