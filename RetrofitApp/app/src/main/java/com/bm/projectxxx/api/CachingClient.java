/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title CachingClient.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description TODO
 * @author zhaocl  
 * @date 2015年3月24日 下午7:32:25
 * @version V1.0  
 */
package com.bm.projectxxx.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

import com.bm.projectxxx.App;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title CachingClient.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description json缓存
 * @author zhaocl
 * @date 2015年3月24日 下午7:32:25
 * @version V1.0
 * @param <T>
 * @param <T>
 */
public class CachingClient extends OkClient implements Serializable {
	
	private static final long serialVersionUID = 8466483579911794630L;
	
	private String mCharset;
	
	private boolean isCache;
	
	public CachingClient(boolean isCache) {
		this.isCache = isCache;
	}

	@Override
	public Response execute(Request request) {
		String url = request.getUrl();
		Response response = null;
		try {
			response = super.execute(request);
			if (isCache) {
				ResponseBean responseBean = new ResponseBean();
				responseBean.url = url;
				
				String mimeType = response.getBody().mimeType();
				mCharset = mimeType.substring(mimeType.indexOf("=") + 1, mimeType.length());
				InputStream inputStream = response.getBody().in();
				long length = response.getBody().length();
				responseBean.body.in = inputStreamToString(inputStream, mCharset);
				responseBean.body.mimeType = mimeType;
				responseBean.body.length = length;
				
				responseBean.setHeaders(response.getHeaders());
				responseBean.reason = response.getReason();
				responseBean.status = response.getStatus();
				
				App.getInstance().getJsonCache().put(url, responseBean);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (isCache) {
				return createResponse(url);
			}
		}

		if (isCache) {
			return createResponse(url);
		}
		return response;
	}

	/**
	 * @author 赵成龙
	 * @Description 
	 * @return Response
	 * @date 2015年5月4日 下午2:42:33
	 */
	private Response createResponse(String key) {
		ResponseBean bean = (ResponseBean) App.getInstance().getJsonCache().getAsObject(key);
		
		if (bean == null) {
			return null;
		}
		
		Response response = new Response(bean.url, bean.status, bean.reason, bean.getHeaders(), bean.body);
		return response;
	}
	
	private class ResponseBean implements Serializable {

		private static final long serialVersionUID = 1L;

		public String url;
		
		public int status;
		
		public String reason;
		
		public ArrayList<CachaHeader> cachaHeaders = new ArrayList<CachaHeader>();
		
		public CachaTypedInput body = new CachaTypedInput();
		
		/**
		 * 储存头
		 * @param heanders
		 */
		public void setHeaders(List<Header> heanders) {
			for (int i = 0; i < heanders.size(); i++) {
				Header header = heanders.get(i);
				cachaHeaders.add(new CachaHeader(header.getName(),header.getValue()));
			}
		}

		/**
		 * 获取头
		 * @return
		 */
		public ArrayList<Header> getHeaders() {
			ArrayList<Header> headers = new ArrayList<Header>();
			for (int i = 0; i < cachaHeaders.size(); i++) {
				CachaHeader cachaHeader = cachaHeaders.get(i);
				headers.add(new Header(cachaHeader.name,cachaHeader.value));
			}
			return headers;
		}

	}
	
	public class CachaHeader implements Serializable {
		
		private static final long serialVersionUID = -7230303930371526398L;
		
		public String name;
		public String value;
		
		public CachaHeader(String name, String value) {
	        this.name = name;
	        this.value = value;
	    }
	}
	
	public class CachaTypedInput implements TypedInput, Serializable {

		private static final long serialVersionUID = -717706206199343018L;

		public String in = null;
		
		public long length = 0;
		
		public String mimeType = null;
		
		public CachaTypedInput() {
			
		}
		
		public CachaTypedInput(String in, long length, String mimeType) {
			this.in = in;
			this.length = length;
			this.mimeType = mimeType;
		}

		@Override
		public InputStream in() throws IOException {
			return new ByteArrayInputStream(in.getBytes(mCharset));
		}

		@Override
		public long length() {
			return length;
		}

		@Override
		public String mimeType() {
			return mimeType;
		}
		
	}
	
	// 输入流转字符串
	private String inputStreamToString(InputStream in, String charset) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		int n;
		while ((n = in.read(b)) != -1) {
			out.append(new String(b, 0, n, charset));
		}
		return out.toString();
	}
	
	// 输入流转字符串
	private String inputStream2String(InputStream in)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
	
}
