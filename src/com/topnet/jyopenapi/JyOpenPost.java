package com.topnet.jyopenapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;



public class JyOpenPost {

	public static void Post(String apiurl,Map<String, String> hm,String token,String timestamp) throws Exception {
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
	     
	    HttpPost request = new HttpPost(apiurl);
	    request.setHeader("content-type", "application/x-www-form-urlencoded");
	    request.setHeader("charset", "utf-8");
	    request.setHeader("Accept-Encoding", "gzip");
	    request.setHeader("token", token);
	    request.setHeader("timestamp", timestamp);
	    if (hm != null) {
	    	  ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
	          Iterator<?> iterator = hm.entrySet().iterator();
	          while(iterator.hasNext()){
	              Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
	              list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	          }
	          if(list.size() > 0){
	              UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
	              request.setEntity(entity);
	          }
	          System.out.println(list);
        }
	    CloseableHttpResponse response = httpclient.execute(request);
	    InputStream ism = response.getEntity().getContent();
	    BufferedReader in = new BufferedReader(
                new InputStreamReader(ism));
        String line,result="";
        while ((line = in.readLine()) != null) {
            result += line;
        }
        Map obj = (Map)JSON.parse(result);
		System.out.println(obj);
	}
}
