package com.topnet.jyopenapi;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * 
 * @author rz
 *
 */

public class JyOpenUtils {
	//生成token方法
	public static String GetToken(String appid,String timestamp,String secret) throws UnsupportedEncodingException{
        String base = appid + timestamp+secret;
        //获取加密后的token
        String md5 = DigestUtils.md5Hex(base.getBytes());
        //System.out.println("md5:"+md5.toUpperCase());
		return md5.toUpperCase();
	}
}