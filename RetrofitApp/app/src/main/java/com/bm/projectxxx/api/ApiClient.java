package com.bm.projectxxx.api;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bm.projectxxx.App;
import com.bm.projectxxx.R;
import com.bm.projectxxx.bean.BaseData;
import com.bm.projectxxx.utils.constant.URLs;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title ApiClient.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description 通用API接口
 * @author 赵成龙  
 * @date 2015年3月24日 上午11:57:51
 * @version V1.0
 */
public class ApiClient<T> {
	
	private static CustomApiInterface customApiInterface;
	
	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private static final Handler errorHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Context context = App.getInstance();
			int errorOrdinal = ((RetrofitError) msg.obj).getKind().ordinal();
			if (errorOrdinal == Kind.NETWORK.ordinal()) {
				Toast.makeText(context, R.string.general_network_error, 300).show();
			} else if (errorOrdinal == Kind.HTTP.ordinal()) {
				Toast.makeText(context, R.string.general_server_down, 300).show();
			} else if (errorOrdinal == Kind.UNEXPECTED.ordinal()) {
				Toast.makeText(context, R.string.general_error, 300).show();
			}
		}

	};
	
	private static final RetrofitErrorHandler retrofitErrorHandler = new RetrofitErrorHandler(errorHandler);

	private static final RequestInterceptor requestInterceptor = new RequestInterceptor() {
		@Override
		public void intercept(RequestFacade request) {
//			request.addHeader("content-type", "application/json");
//			request.addHeader("accept-encoding", "gzip"); // Here is the problem
//			request.addHeader("Cache-Control", "public, max-age=600");
			// 华安基金
//			request.addHeader("HUAAN_CLIENT_TYPE", "lanse_android");
//			request.addHeader("HUAAN_CLIENT_VERSION", "0.9");
//			try {
//				request.addHeader(
//						"HUAAN_CLIENT_PARAM",
//						AESUtils.encrypt(
//								"00" + "08ha_lanse" + "14"
//										+ TimestampUtils.getCurrentTimestamp(),
//								"Hcdjpaq9buQ4iqhuet7M4g==").replace("\n", " "));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	};

	public static CustomApiInterface getCustomApiClient(Class<?> mClass) {
			RestAdapter restAdapter = new RestAdapter.Builder()
					// 设置错误处理Handler
					.setErrorHandler(retrofitErrorHandler)
					// 设置远程地址
					.setEndpoint(URLs.ROOT_URL) // 设置远程地址
//					.setEndpoint("http://222.66.40.26/mobile-gateway")
					// 设置请求拦截器
					.setRequestInterceptor(requestInterceptor)
					// 设置数据解析器
					.setConverter(new ApiConverter(mClass))
					.build();
			customApiInterface = restAdapter.create(CustomApiInterface.class);

		return customApiInterface;
	}
	
	public static CustomApiInterface getCustomApiClient(Class<?> mClass, boolean isCache) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setErrorHandler(retrofitErrorHandler)
				.setEndpoint(URLs.ROOT_URL)
				.setRequestInterceptor(requestInterceptor)
				.setConverter(new ApiConverter(mClass))
				// 是否缓存本次请求
				.setClient(new CachingClient(isCache)) 
				.build();

		customApiInterface = restAdapter.create(CustomApiInterface.class);
		return customApiInterface;
	}

	public interface CustomApiInterface {
		
		// 首页六个随机标签
		@POST("/app/randomLabel.htm")
		<T> void getLabelList(Callback<BaseData<T>> callback);

		// 用户登录
		@FormUrlEncoded
		@POST("/app/checkMobileLogin.htm")
		<T> void getUserInfo(@FieldMap HashMap<String, String> params,
							 Callback<BaseData<T>> callback);

		// 更换头像
		@Multipart
		@POST("/app/updateMemberName.htm")
		<T> void uploadHead(@Part("memberId") String memberId,
							@Part("head") TypedFile file,
							Callback<BaseData<T>> callback);
		
		// 多图片上传(方式一)
//		@Multipart
//		@POST("/app/sendFocus.htm")
//		<T> void uploadImages(@Part("memberId") String memberId,
//				@Part("content") String content,
//				@PartMap Map<String, TypedFile> files,
//				Callback<BaseData<T>> callback);
		
		// 多图片上传(方式二)
		@Multipart
		@POST("/app/sendFocus.htm")
		<T> void uploadImages(@PartMap HashMap<String, String> params,
							  @PartMap Map<String, TypedFile> files,
							  Callback<BaseData<T>> callback);
		
		// 复杂接口的定义(自定义body等)
		@POST("/FundApiServlet2.servlet")
		<T> void huaanFundTest(@Body TypedInput body, Callback<T> callback);
		
		// 通用get方式请求
		@GET("/{url}")
		void customGetMethod(@Path(value = "url", encode = false) String url);

		// 通用post方式请求
		@FormUrlEncoded
		@POST("/{url}")
		<T> void customPostMethod(
				@Path(value = "url", encode = false) String url,
				@FieldMap HashMap<String, String> params,
				Callback<BaseData<T>> callback);
		
		/**
		 * 
		 * @author 赵成龙
		 * @Description 通用多文件上传(单个文件亦可)
		 * @param url 地址
		 * @param params 普通参数（非必须）
		 * @param files 文件表单
		 * @param callback 回调
		 * @return void 
		 * @date 2015年4月17日 下午5:34:29
		 */
		@Multipart
		@POST("/{url}")
		<T> void customMultiFileUpload(
				@Path(value = "url", encode = false) String url,
				@PartMap HashMap<String, String> params,
				@PartMap Map<String, TypedFile> files,
				Callback<BaseData<T>> callback);

	}
	
}
