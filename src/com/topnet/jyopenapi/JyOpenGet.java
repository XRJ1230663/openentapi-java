package com.topnet.jyopenapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

public class JyOpenGet {
	public static void Get(String apiurl,Map<String, String> hm,String token,String timestamp) throws Exception {
		BufferedReader in = null;
		String param = "";    
		try {
        		// Create a trust manager that does not validate certificate chains
    	    	TrustManager[] trustAllCerts = new TrustManager[] {
    		        new X509TrustManager() {
    					@Override
    					public X509Certificate[] getAcceptedIssuers() {
    						// TODO Auto-generated method stub
    						return null;
    					}
    					
    					@Override
    					public void checkServerTrusted(X509Certificate[] arg0, String arg1)
    							throws CertificateException {
    						// TODO Auto-generated method stub
    						
    					}
    					
    					@Override
    					public void checkClientTrusted(X509Certificate[] arg0, String arg1)
    							throws CertificateException {
    						// TODO Auto-generated method stub
    						
    					}
    				}
    		    };
    		     
    		    SSLContext ctx = SSLContext.getInstance("TLS");
    		    ctx.init(null, trustAllCerts, null);
    		     
    		    LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
    		     
    		    CloseableHttpClient httpclient = HttpClients.custom()
    		            .setSSLSocketFactory(sslSocketFactory)
    		            .build();
        	if (hm != null) {
        		Iterator<?> iterator = hm.entrySet().iterator();
        		while(iterator.hasNext()){
        			Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
        			if (param!=""){
        				param +="&";
        			}
        			param += elem.getKey()+"="+elem.getValue();
        		}
        			System.out.println(param);
        	}
        	
            String urlNameString = apiurl+"?"+param;
    	    HttpGet request = new HttpGet(urlNameString);
    	    request.setHeader("content-type", "application/x-www-form-urlencoded");
    	    request.setHeader("charset", "utf-8");
    	    request.setHeader("Accept-Encoding", "gzip");
    	    request.setHeader("token", token);
    	    request.setHeader("timestamp", timestamp);
    	    CloseableHttpResponse response = httpclient.execute(request);
    	    InputStream ism = response.getEntity().getContent();
    	    in = new BufferedReader(
                    new InputStreamReader(ism));
            String line,result="";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Map obj = (Map)JSON.parse(result);
    		System.out.println(obj);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}
}
