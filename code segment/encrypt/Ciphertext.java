package com.zrkj.commons.utils.password;

import java.util.Base64;

/**
 * 密文
 * 
 * @author zx
 *
 */
public class Ciphertext {
	private byte[] content;

	public Ciphertext(byte[] content) {
		this.content = content;
	}

	public String getHex() {
		return HexUtils.parseByte2HexStr(content);
	}

	public String getBase64() {
		return new String(Base64.getEncoder().encode(content));
	}

	public String getString() {
		return new String(content);
	}
}
