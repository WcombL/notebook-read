package com.zrkj.commons.codec;

import com.zrkj.commons.exception.ApplicationException;
import com.zrkj.commons.exception.DecryptionException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtils {
    public static String ENCRYPTION_TYPE = "RSA";

    public static class Base64KeyPair implements Serializable {

        private static final long serialVersionUID = -1331011012431969897L;
        public final String publicKey;
        public final String privateKey;

        public Base64KeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
    }

    /**
     * 用公钥对内容SHA256摘要验签
     *
     * @param base64PublicKey
     * @param signature
     * @param content
     * @return
     * @throws GeneralSecurityException
     * @throws DecoderException
     */
    public static boolean verifySHA256(String base64PublicKey, String signature, byte[] content) throws GeneralSecurityException, DecoderException {
        // 计算摘要
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(content);
        byte[] hash = md.digest();

        // 解密签名
        PublicKey publicKey = decodePublicKey(base64PublicKey);
        byte[] decrypted = decryptByKey(Base64.decodeBase64(signature), publicKey);

        // 验证
        return new String(hash).equals(new String(decrypted));
    }

    /**
     * 用私钥对内容的SHA256摘要签名
     *
     * @param base64PrivateKey 私钥
     * @param content          内容
     * @return
     * @throws GeneralSecurityException
     */
    public static String signSHA256(String base64PrivateKey, byte[] content) throws GeneralSecurityException {
        // 计算摘要
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(content);
        byte[] hash = md.digest();

        // 签名
        PrivateKey privateKey = decodePrivateKey(base64PrivateKey);
        byte[] bytes = encryptByKey(hash, privateKey);
        return new String(Base64.encodeBase64(bytes));
    }

    public static void main(String[] args) throws Exception {
//        String secret = "5saXE88JTEbsNLt3hqS7Yw==\nBQEp4Q/K8JlaJ+bj145SNA==";
//
//        byte[] encrypted = encryptAESCBC(secret, "test".getBytes());
//        System.out.println(new String(encrypted));
//
//        byte[] decrypted = decryptAESCBC(secret, encrypted);
//        System.out.println(new String(decrypted));

//        Base64KeyPair base64KeyPair = generateKeyPair();
//        String sign = signSHA256(base64KeyPair.privateKey, "test".getBytes());
//
//        System.out.println(sign);
//        System.out.println(verifySHA256(base64KeyPair.publicKey, sign, "test".getBytes()));
//        String s = encryptByPublicKey("test".getBytes(),
//                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALVgcC5VzE6pJpmlm6qot2e9xftMrOJ+y3rbXySHXHoMolGsKsxzxEu7ticeLyz5MSpl6bXRIaICpo2+x8mdYc0CAwEAAQ==");
//        System.out.println(new String(s));
        System.out.println(generateAESCBCSecret());
    }

    /**
     * 生成公私钥密码对并用 Base64 encode
     *
     * @return Base64KeyPair 公私钥密码对
     * @throws GeneralSecurityException
     */
    public static Base64KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ENCRYPTION_TYPE);
            keyGen.initialize(512);
            KeyPair genKeyPair = keyGen.genKeyPair();
            PublicKey publicKey = genKeyPair.getPublic();
            PrivateKey privateKey = genKeyPair.getPrivate();
            String base64PublicKey = Base64.encodeBase64String(publicKey.getEncoded());
            String base64PrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
            Base64KeyPair result = new Base64KeyPair(base64PublicKey, base64PrivateKey);
            return result;
        } catch (GeneralSecurityException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }

    /**
     * Base64 decode 公钥
     *
     * @param base64String
     * @return
     * @throws GeneralSecurityException
     */
    public static PublicKey decodePublicKey(String base64String) throws GeneralSecurityException {
        byte[] keyBytes = Base64.decodeBase64(base64String);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_TYPE);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 将一个公钥或者私钥用base64 encode 并返回字符串表示形式
     *
     * @param key
     * @return
     */
    public static String base64EncodeKey(Key key) {
        return new String(Base64.encodeBase64(key.getEncoded()));
    }

    /**
     * Base64 decode 私钥
     *
     * @param base64String
     * @return
     * @throws GeneralSecurityException
     */
    public static PrivateKey decodePrivateKey(String base64String) throws GeneralSecurityException {
        byte[] keyBytes = Base64.decodeBase64(base64String);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_TYPE);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  公钥/私钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptByKey(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param base64PublicKey 公钥 Base64 字符串
     * @return
     * @throws GeneralSecurityException
     */
    public static String encryptByPublicKey(byte[] data, String base64PublicKey) {
        try {
            PublicKey key = decodePublicKey(base64PublicKey);
            byte[] encrypt = encryptByKey(data, key);
            return Base64.encodeBase64String(encrypt);
        } catch (GeneralSecurityException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }

    /**
     * 加密
     *
     * @param data             待加密数据
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptByPrivateKey(byte[] data, String base64PrivateKey) throws GeneralSecurityException {
        PrivateKey key = decodePrivateKey(base64PrivateKey);
        return encryptByKey(data, key);
    }

    /**
     * 解密
     *
     * @param data             加密字符串
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptByPrivateKey(String data, String base64PrivateKey) throws GeneralSecurityException {
        PrivateKey key = decodePrivateKey(base64PrivateKey);
        return decryptByKey(Base64.decodeBase64(data), key);
    }
    /**
     * 解密
     *
     * @param data            加密字符串
     * @param base64PublicKey 私钥 Base64 字符串
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptByPublicKey(String data, String base64PublicKey) throws GeneralSecurityException {
        PublicKey key = decodePublicKey(base64PublicKey);
        return decryptByKey(Base64.decodeBase64(data), key);
    }

    /**
     * 解密
     *
     * @param data 加密字符串
     * @param key  私钥/公钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptByKey(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * @param base64Secret 一个Base64的key和矩阵
     * @param data         被加密的数据
     * @return
     */
    public static byte[] encryptAESCBC(String base64Secret, byte[] data) throws Exception {
        byte[] key = Base64.decodeBase64(base64Secret.split("\n")[0]);
        byte[] initVector = Base64.decodeBase64(base64Secret.split("\n")[1]);

        System.out.println(key.length);

        IvParameterSpec iv = new IvParameterSpec(initVector);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(data);
        return encrypted;
    }

    /**
     * @param base64Secret 一个Base64的key和矩阵
     * @param data         要解密的数据
     * @return
     */
    public static byte[] decryptAESCBC(String base64Secret, byte[] data){
    	try{
    		byte[] key = Base64.decodeBase64(base64Secret.split("\n")[0]);
            byte[] initVector = Base64.decodeBase64(base64Secret.split("\n")[1]);

            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(data);
            return original;
    	}catch(NoSuchPaddingException |NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException |InvalidAlgorithmParameterException| BadPaddingException ex){
    		throw new DecryptionException(ex.getMessage(), ex);
    	}
    }

    public static String generateAESCBCSecret() {
        String key = RandomStringUtils.randomAlphanumeric(16);
        String iv = RandomStringUtils.randomAlphanumeric(16);
        try {
			return new String(Base64.encodeBase64(key.getBytes())) + "\n" + new String(Base64.encodeBase64(iv.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
