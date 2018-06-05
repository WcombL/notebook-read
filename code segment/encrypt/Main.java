package com.zrkj.commons.utils.password;

public class Main {

	public static void main(String[] args) {
		String sha1 = ShaUtils.sha1();
		System.out.println("密码：" + sha1);
		String content = "可口可乐";
		System.out.println("原文:" + content);

		String ciphertext = AesUtils.encryptAES(content, sha1).getBase64();

		System.out.println("加密后密文:" + ciphertext);

		System.out.println(AesUtils.decryptAES(Base64Utils.decoder(ciphertext), sha1).getString());

		System.out.println(Md5Util.verify("user", "258b39c7303b22fe87e3d122394b3671262e11c649772e5e"));

		String password = Md5Util.generate("qqWW22@@");
		System.out.println(password);

	}

}
