package com.framework.monitor.httpclient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.framework.monitor.httpclient.exception.IllegalUrlException;

public class PoolingHttpClientConnectionManagerDemo {

	private int maxTotalPool = 100;
	private int maxConPerRoute = 20;
	
	private int connectionRequestTimeout = 1000;
	private int connectTimeout = 1000;
	private int socketTimeout = 1000;

	private PoolingHttpClientConnectionManager poolConnManager;

	public PoolingHttpClientConnectionManagerDemo() {
		try {
			init();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	void init() throws GeneralSecurityException {
		
		SSLContext sslContext;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		} catch (KeyManagementException | NoSuchAlgorithmException
				| KeyStoreException e) {
			throw e;
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http",PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslsf).build();

		poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolConnManager.setMaxTotal(maxTotalPool);
		poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
		
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
		poolConnManager.setDefaultSocketConfig(socketConfig);
	}

	private CloseableHttpClient getConnection() {
		RequestConfig reqConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager).setDefaultRequestConfig(reqConfig).build();
		if(poolConnManager != null && poolConnManager.getTotalStats() != null){
			System.out.println("now client pool "+poolConnManager.getTotalStats().toString());
		}
		return httpClient;
	}

	public void doPost(String url) {
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalUrlException(
					"HttpUrl can't be N/A, eg. <http(s)://www.hostname.com/index>.");
		}

		HttpGet httpPost = new HttpGet(url);
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		nvps.add(new BasicNameValuePair("s", "JAVA"));
//		try {
//			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

		CloseableHttpClient httpClient = getConnection();
		System.out.println(httpClient);
		CloseableHttpResponse httpResp = null;
		try {
			httpResp = httpClient.execute(httpPost);
			System.out.println(httpResp.getStatusLine());
//			Header[] headers = httpResp.getAllHeaders();
//			for(Header header : headers){
//				System.out.println(header.getName() + " -- " + header.getValue());
//			}
			
			HttpEntity respEntity = httpResp.getEntity();
			EntityUtils.consume(respEntity);
			/*String result = *//*EntityUtils.toString(respEntity, Charset.forName("utf-8"));*/
//			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "http://www.baidu.com";/*url = null;*/
		PoolingHttpClientConnectionManagerDemo httpPool = new PoolingHttpClientConnectionManagerDemo();
		for(int index = 0;index < 100; index++){
			httpPool.doPost(url);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
