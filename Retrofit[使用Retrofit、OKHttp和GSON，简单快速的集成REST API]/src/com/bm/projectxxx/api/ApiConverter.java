/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title ApiConverter.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description TODO
 * @author zhaocl  
 * @date 2015年3月19日 下午2:36:21
 * @version V1.0  
 */
package com.bm.projectxxx.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.bm.projectxxx.bean.BaseData;
import com.google.gson.Gson;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title ApiConverter.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description Json数据转换器
 * @author 赵成龙
 * @date 2015年3月19日 下午2:36:21
 * @version V1.0
 * @param <T>
 * @param <T>
 */
public class ApiConverter implements Converter {
	
	/**
	 * Class type for the response
	 */
	private final Class<?> mClass;
	
	/**
     * Gson parser
     */
    public static Gson mGson = new Gson();
	
	public final static Charset mCharset = Charset.forName("UTF-8");

	/**
	   * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
	   * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	   */
	  public ApiConverter(Class<?> mClass) {
	    this(mGson, mClass);
	  }

	  /**
	   * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
	   * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	   */
	  public ApiConverter(Gson gson, Class<?> mClass) {
	    this(gson, mCharset, mClass);
	  }

	  /**
	   * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
	   * decoding from JSON (when no charset is specified by a header) will use the specified charset.
	   */
	  public ApiConverter(Gson gson, Charset charset, Class<?> mClass) {
		  this.mClass = mClass;
	    if (gson == null) throw new NullPointerException("gson == null");
	    if (charset == null) throw new NullPointerException("charset == null");
	  }

	@Override
	public BaseData<?> fromBody(TypedInput input, Type type)
			throws ConversionException {
		String json;
		BaseData<?> baseData;
		try {
			if (input.in() != null) {
				json = inputStreamToString(input.in());
				
				Type objectType;
			    if (mClass != null) {
			        objectType = type(BaseData.class, mClass);
			    }
			    else {
			        objectType = BaseData.class;
			    }
				
				baseData = mGson.fromJson(json, objectType);
				return baseData;
				
				// 华安基金
//				byte[] desEncryptByte = AESUtils.desEncrypt(inputStreamToByteArray(input.in()),
//						"Hcdjpaq9buQ4iqhuet7M4g==");
//				byte[] uncompressByte = ZipUtil.uncompress(desEncryptByte);
//				json = new String(uncompressByte, "utf-8");
//				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {

			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}

	/**
	 * @Package com.bm.projectxxx.api
	 * @Description
	 * @author 赵成龙
	 * @ChangedBy zhaocl 
	 * @see retrofit.converter.Converter#toBody(java.lang.Object)
	 */
	@Override
	public TypedOutput toBody(Object object) {
		return null;
	}
	
//	// 输入流转字符串
//	private String inputStreamToString(InputStream in) throws IOException {
//		StringBuffer out = new StringBuffer();
//		byte[] b = new byte[4096];
//		int n;
//		while ((n = in.read(b)) != -1) {
//			out.append(new String(b, 0, n, mCharset.name()));
//		}
//		return out.toString();
//	}
	
	// 输入流转字符串
	private String inputStreamToString(InputStream in) throws IOException {
	   StringBuffer out = new StringBuffer();
	   BufferedReader br = new BufferedReader(new InputStreamReader(
	         in, "utf-8"));
	   String data = "";
	   while ((data = br.readLine()) != null) {
	      out.append(data);
	   }
	   return out.toString();
	}
	
	// 输入流转 byte[]
	private byte[] inputStreamToByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}

}
