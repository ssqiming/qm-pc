/**
 * 
 */
package com.qm.common.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author lix 2016年4月5日 下午5:46:51
 */
public class EncryptUtil {

	private final static String PRIVATE_KEY = "xJ16[b#83O9c^*&jlf3!6$"; // 私钥串

	public static byte[] decode(byte[] info) {
		byte[] key = PRIVATE_KEY.getBytes();
		int j = 0;
		byte[] result = new byte[info.length];
		for (int i = 0; i < info.length; i++) {
			if (i % (key.length) == 0) {
				j = 0;
			}
			result[i] = (byte) (info[i] ^ key[j]);
			j++;
		}
		return result;
	}

	public static byte[] getFromBase64(String base64String) {
		byte[] b = new byte[0];
		if (base64String != null) {
			b = Base64.decodeBase64(base64String);
		}
		return b;
	}
	
	public static String transferBase64(byte[] textByte) {
		return Base64.encodeBase64String(textByte);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "JN071                4563518300035436643                               201603101855371823920160310185636001            1.00     104650048160022     Q201603101855371823943962                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              测试测试圈存";
		byte[] encode = decode(text.getBytes());
		String base64Text = transferBase64(encode);
		System.out.println(base64Text);
		byte[] bit = getFromBase64(base64Text);
		byte[] decode = decode(bit);
		System.out.println("result: " + new String(decode));
	}

}
