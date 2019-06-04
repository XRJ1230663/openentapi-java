package com.topnet.jyopenapi;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Described 剑鱼数据开放平台接口调用样例
 * @author rz
 * @date 2018-02-09
 * @version v1.0
 */
public class JyOpenApi  {
	
	/**
	 * 调用样例
	 * 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String appid="jye_test";
		String secret="test";
		String apiurl="https://apiv2.jianyu360.com/ent/getdata";
		String keyword ="万达信息股份有限公司";

		String starttime = "20170217";
		String endtime = "20170718";
		String timestamp = new Date().getTime()/1000+"";
		//System.out.println(new Date().getTime()/1000+"");

		Map<String, String> parameters = new HashMap<String, String>();
		// 请求参数
	    parameters.put("appid", appid);
	    parameters.put("keyword", keyword);
	    parameters.put("starttime", starttime);
	    parameters.put("endtime", endtime);
  
	    //参数签名
	    try {
	    	String token=JyOpenUtils.GetToken(appid,timestamp, secret);
	    	//JyOpenPost.Post(apiurl,parameters,token,timestamp);
	    	JyOpenGet.Get(apiurl, parameters, token, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
