package com.iuunited.myhome.Helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.entity.BaseEntity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.net.AsyncHttpClient;
import com.iuunited.myhome.net.JsonHttpResponseHandler;
import com.iuunited.myhome.net.RequestParams;
import com.iuunited.myhome.net.ResponseHandlerInterface;
import com.iuunited.myhome.util.DefaultShared;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ServiceClient
{
//	public static final String HOST_IP = "120.76.220.252";//测试域名
//	public static final String HOST_IP = "112.74.132.18";//正式测名
	public static final String HOST_IP = "www.iuunited.com";//正式测名
	public static final int HOST_PORT = 9000;
	public static final String BASE_H5_URL = "http://yt.watchvips.com:80";//接入H5的url
//	http://www.iuunited.com:9000/
	private static final String BASE_HOST = "http://" + HOST_IP;

//	private static final String BASE_USER_URL    = BASE_HOST+":"+HOST_PORT;
	private static final String BASE_USER_URL    = "https://www.iuunited.com/myhome/";

	//用户昵称
	private static String UserNickName = "";

	//用户ID
	private static String UserID;
	private static String SessionID;
	
	private static AsyncHttpClient HttpClient;

	private static Map<String, SoftReference<List<Field>>> ClassFields = new ConcurrentHashMap<String, SoftReference<List<Field>>>();

	//防止用户多次点击按钮这类操作，保存当前操作的ui id
	private static HashSet<Integer> RequestingUIResource = new HashSet<Integer>();
	
	public static  boolean IsTest = true;
	


	public static String getUrl(String relativeUri)
	{
		return BASE_HOST + ":" + HOST_PORT + relativeUri;
	}
		
	
	public static void setUserNickName(String nickName)
	{
		UserNickName = nickName;
		
	}
	
	public static Map<String, String> getHttpParameters()
	{
		Map<String, String> httpParameters = new HashMap<String, String>();
		//添加需要的id值
		return httpParameters;
	}
	
	public static void cancelRequests(Context context, boolean mayInterruptIfRunning)
	{
		if (context != null)
		{
			HttpClient.cancelRequests(context, mayInterruptIfRunning);
		}
		else
		{
			HttpClient.cancelAllRequests(mayInterruptIfRunning);
		}
	}

	/**
	 * 获取dto对象的值并调用post方法
	 * @param requestDto
	 * @param responseHandler
	 */
	public static <T extends BaseEntity, U extends BaseEntity> void post(Context context, List<String> fieldsName, T requestDto, ResponseHandlerInterface responseHandler)
	{
		post(context, fieldsName, requestDto, responseHandler, false);
	}

	/**
	 * 获取dto对象的值并调用post方法
	 * @param context
	 * @param requestDto
	 * @param responseHandler
	 * @param putNullableField:是否传null的值
	 */
	public static <T extends BaseEntity, U extends BaseEntity> void post(Context context, List<String> fieldsName, T requestDto, ResponseHandlerInterface responseHandler, boolean putNullableField)
	{
		Class<? extends BaseEntity> dtoClass = requestDto.getClass();
		DtoInfo dtoInfo = getDtoInfo(dtoClass);

		RequestParams params = getRequestParams(dtoInfo, fieldsName, requestDto, dtoClass, putNullableField);

		HttpClient.addHeader(AsyncHttpClient.HEADER_CONTENT_TYPE, "application/json;charset=utf-8");
		String sessionId = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_SESSIONID, "");
		if(!com.iuunited.myhome.util.TextUtils.isEmpty(sessionId)) {
			HttpClient.addHeader(AsyncHttpClient.HEADER_SESSION_ID, sessionId);
		}
        int userId = DefaultShared.getIntValue(MyApplication.getContext(), Config.CONFIG_USERID, 0);
        if(userId!=0) {
			HttpClient.addHeader(AsyncHttpClient.HEADER_USER_ID, String.valueOf(userId));
		}

		HttpClient.post(context, getAbsoluteUrl(dtoInfo), params, responseHandler);
	}
	
	/**
	 * 获取dto对象的值并调用get方法
	 * @param requestDto
	 * @param responseHandler
	 */	
	public static <T extends BaseEntity, U extends BaseEntity> void get(Context context, List<String> fieldsName, T requestDto, ResponseHandlerInterface responseHandler)
	{
		Class<? extends BaseEntity> dtoClass = requestDto.getClass();
		
		DtoInfo dtoInfo = getDtoInfo(dtoClass);

		RequestParams params = getRequestParams(dtoInfo, fieldsName, requestDto, dtoClass, true);		
		
		HttpClient.get(context, getAbsoluteUrl(dtoInfo), params, responseHandler);
		
	}
	
	private static String getAbsoluteUrl(DtoInfo dtoInfo)
	{
		if (dtoInfo.hostType == RouteAttr.HOST_TYPE.Default)
		{
			return BASE_USER_URL + dtoInfo.url;
		}
		else
		{
			return BASE_H5_URL + dtoInfo.url;
		}
	}
		
	private static DtoInfo getDtoInfo(Class<? extends BaseEntity> dtoClass) {
		RouteAttr routeAttr = (RouteAttr)dtoClass.getAnnotation(RouteAttr.class);
		if (routeAttr != null)
		{
			DtoInfo dtoInfo = new DtoInfo();
			
			dtoInfo.url 		= routeAttr.url();
			dtoInfo.hostType 	= routeAttr.hostType();
			dtoInfo.timeout 	= routeAttr.timeout();
			dtoInfo.retries		= routeAttr.retries();
			
			return dtoInfo;
		}
		
		return null;
	}

	private static <T extends BaseEntity> RequestParams getRequestParams(DtoInfo dtoInfo, List<String> fieldsName, T requestDto, Class<? extends BaseEntity> dtoClass, boolean putNullableField)
	{	
		//确保sessionid有值，如果无则从本地存储恢复
		//在某些进程被应用杀掉的情况下这种做法很重要
		RequestParams params = new RequestParams();

		params.setHttpEntityIsRepeatable(true);
		params.setUseJsonStreamer(true);
		
		boolean hasFile = false;
		List<Field> classFields = getClassFields(dtoClass);
		
		if (fieldsName != null && fieldsName.size() > 0)
		{
			HashMap<String, Field> fieldsMap = new HashMap<String, Field>();
			for(Field field : classFields)
			{
				fieldsMap.put(field.getName().toLowerCase(), field);
			}
			
			for(String fieldName : fieldsName)
			{
				if (fieldsMap.containsKey(fieldName.toLowerCase()))
				{
					Field field = fieldsMap.get(fieldName.toLowerCase());
					field.setAccessible(true);
					
					try 
					{
						
						Object value = field.get(requestDto);
						
						if (value != null)
						{
							Class<?> clazz = value.getClass();
							if (JSONHelper.shouldSerialize(clazz))
							{
								JSONStringer fieldValue = JSONHelper.toJSON(value);
							
								params.put(field.getName(), fieldValue);
								//params.put(field.getName(), value);
							}
							else
							{
								params.add(field.getName(), value);
							}
							
						}
						else if (putNullableField)
						{
							params.add(field.getName(), "");
						}
					} 
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					
				}
				
			}
		}
		else
		{
			for(Field field : classFields)
			{
				try 
				{
					field.setAccessible(true);
					
					Object value = field.get(requestDto);
					if (value != null)
					{
						//上传图片
						if (field.getType().getSimpleName().equalsIgnoreCase("File"))
						{
							try 
							{
								params.put(field.getName(), (File)value, "application/octet-stream");
								hasFile = true;
							}
							catch (FileNotFoundException e)
							{
								e.printStackTrace();
							} 
						}
						else if (field.getType().getSimpleName().equalsIgnoreCase("byte[]"))
						{
							if (dtoInfo.mediaType == RouteAttr.IMAGE_TYPE)
							{
								params.put(field.getName(), new ByteArrayInputStream((byte[])value), field.getName()+".jpg", "image/jpg");
							}
							else
							{
								params.put(field.getName(), new ByteArrayInputStream((byte[])value), field.getName()+".amr", "application/octet-stream");
							}
							
							hasFile = true;
						}
						else
						{
							Class<?> clazz = value.getClass();
							if (JSONHelper.shouldSerialize(clazz))
							{
								//params.put(field.getName(), value);
								//String fieldValue = JSONHelper.toJSONString(value);
								JSONStringer fieldValue = JSONHelper.toJSON(value);
								
								params.put(field.getName(), fieldValue);
								
								//params.add(field.getName(), fieldValue);
							}
							else
							{
								params.add(field.getName(), value);
							}
						}
					}
					else
					{
						if (putNullableField)
						{
							params.add(field.getName(), "");
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		
		//如果请求字段带有byte[]二进制字段，或者请求元数据强制长请求，则设置长请求
		if (hasFile)
		{
			HttpClient.setMaxRetriesAndTimeout(dtoInfo.retries, RouteAttr.LONG_TIME_OUT);
		}
		else
		{
			HttpClient.setMaxRetriesAndTimeout(dtoInfo.retries, dtoInfo.timeout);
		}
		
		return params;
	}
	
	/**
	 * 设置HttpClient的header信息，全局统一
	 * @param context
	 */
	public static void initialize(Context context)
	{
	    try
	    {
	    	HttpClient = new AsyncHttpClient();
	    	//超时允许重试
	    	//HttpClient.allowRetryExceptionClass(SocketTimeoutException.class);

	    	PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

	    	TelephonyManager phoneManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

	    	//支持各种设备类型的另一种方法是使用getDeviceID（）API和ro.serialno的组合
			String clientID = phoneManager.getDeviceId();//imei 设备唯一ID 手机卡ID: getSubscriberId()
			
			if (clientID == null)
			{
		    	Class<?> c = Class.forName("android.os.SystemProperties");
		    	Method getMethod = c.getMethod("get", String.class, String.class );
		    	clientID = (String)getMethod.invoke(c, "ro.serialno", "unknown");
			}

//			final String appName = "scripture";
//			final String appVersion = ""+packageInfo.versionCode;
//			//final String appVersionName = packageInfo.versionName;
//			final String clientModel = Build.MODEL; //手机型号
//			final String clientOS = "Android";
//			final String clientVersion = Build.VERSION.RELEASE;

			//String userAgent = String.format("UserID=%d;SessionID=%s", ServiceClient.UserID, ServiceClient.SessionID);//String.format("%s/%s/%s/%s/%s/%s/%s", appName, appVersion, clientModel, clientOS, clientVersion, clientID, PreferencesManager.getInstance().getSessionID());
			//HttpClient.setUserAgent(userAgent);
			
			//HttpClient.setMaxRetriesAndTimeout(0, 10000);
	    }
	    catch (NameNotFoundException e) {
		      e.printStackTrace();
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * 获取dto对象对应的缓存字段信息，如果没有则获取并加入缓存
	 * @param dtoClass
	 * @return
	 */
	private static List<Field> getClassFields(Class<? extends BaseEntity> dtoClass)
	{
		String className = dtoClass.getName();
		if (ClassFields.containsKey(className))
		{
			List<Field> fields = ClassFields.get(className).get();
			
			if (fields == null || fields.size() == 0)
			{
				ClassFields.remove(className);
			}
			else
			{
				return fields;
			}
		}
		
		List<Field> listFields = JSONHelper.getClassDeclaredFields(dtoClass);
		
		ClassFields.put(className, new SoftReference<List<Field>>(listFields));
		
		return listFields;
	}
	
	public static  <T extends BaseEntity> String getErrorString(T request, int statusCode, Header[] headers, String errorMessage)
	{
		return statusCode + ":" +errorMessage;
	}

//	public static <T extends BaseEntity, U extends BaseEntity> void requestServerList(boolean pushNullable, final IServerRequestable requestable, final String loadingMessage, final T request, final Class<U> responseDtoClass, final OnSimpleListActionListener<U> onSimpleListener)
//	{
//		post(null, request, new ServiceClient.ScriptureHttpResponseListHandler<U>(responseDtoClass)
//		{
//			@Override
//			public void onStart()
//			{
//				onSimpleListener.onStart();
//				
//				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
//				{
//					requestable.showLoadingDialog(loadingMessage);
//				}
//			}
//			
//			@Override
//			public void onFinish()
//			{
//				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
//				{
//					requestable.dismissLoadingDialog();
//				}
//				
//				onSimpleListener.onFinish();
//			}
//			
//			@Override
//			public void onProgress(int bytesWritten, int totalSize) {
//				super.onProgress(bytesWritten, totalSize);
//				
//				//处理进度
//				onSimpleListener.onProgress(bytesWritten, totalSize);
//			}
//			
//			@Override
//			public void onSuccess(Header[] headers, List<U> responseDto) {
//
//				if (requestable != null)
//				{
//					requestable.setSuccessful(true);
//				}
//				
//				onSimpleListener.onSuccess(responseDto);
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					String errorMessage) {
//
//				if (requestable != null)
//				{
//					requestable.setSuccessful(false);
//				}
//				
//				errorMessage = getErrorString(request, statusCode, headers, errorMessage);
//				
//				if (!onSimpleListener.onFailure(errorMessage))
//				{
//					if (errorMessage != null && requestable != null)
//					{
//						requestable.showCustomToast(errorMessage);
//					}
//				}
//			}
//		
//		}, pushNullable);		
//	}
	
//	public  static <T extends BaseEntity, U extends BaseEntity> void requestServerList(final IServerRequestable requestable, final String loadingMessage, final T request, final Class<U> responseDtoClass, final OnSimpleListActionListener<U> onSimpleListener)
//	{
//		requestServerList(false, requestable, loadingMessage, request, responseDtoClass, onSimpleListener);
//	}

	public static <T extends BaseEntity, U extends BaseEntity> void getServerList(final int resourceId, boolean pushNullable, final IServerRequestable requestable, final String loadingMessage, List<String> fieldsName, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<List<U>> onSimpleListener)
	{
		if (resourceId > 0)
		{
			if (RequestingUIResource.contains(resourceId))
			{
				//如果已经有这个id，不响应
				return;
			}

			RequestingUIResource.add(resourceId);
		}

		get(null, fieldsName, request, new ScriptureHttpListResponseHandler<U>(responseDtoClass)
		{
			@Override
			public void onStart()
			{
				onSimpleListener.onStart();

				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.showLoadingDialog(loadingMessage);
				}
			}

			@Override
			public void onFinish()
			{
				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.dismissLoadingDialog();
				}

				onSimpleListener.onFinish();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize)
			{
				super.onProgress(bytesWritten, totalSize);

				//处理进度
				onSimpleListener.onProgress(bytesWritten, totalSize);
			}

			@Override
			public void onSuccess(Header[] headers,
								  List<U> responseDto) {

				if (requestable != null)
				{
					requestable.setSuccessful(true);
				}

				onSimpleListener.onSuccess(responseDto);

				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String errorMessage) {

				//Class<? extends BaseEntity> dtoClass = request.getClass();
				//MobclickAgent.reportError(BaseApplication.getInstance(), "request:"+dtoClass.getName()+", statusCode:" + statusCode + ", error:" + errorMessage + ", agent:" + HttpClient.getUserAgent());

				if (requestable != null)
				{
					requestable.setSuccessful(false);
				}

				errorMessage = getErrorString(request, statusCode, headers, errorMessage);

				if (!onSimpleListener.onFailure(errorMessage))
				{
					if (errorMessage != null && requestable != null)
					{
						requestable.showCustomToast(errorMessage);
					}
				}

				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}
		});
	}

	public static <T extends BaseEntity, U extends BaseEntity> void getServer(final int resourceId, boolean pushNullable, final IServerRequestable requestable, final String loadingMessage, List<String> fieldsName, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<U> onSimpleListener)
	{
		if (resourceId > 0)
		{
			if (RequestingUIResource.contains(resourceId))
			{
				//如果已经有这个id，不响应
				return;
			}

			RequestingUIResource.add(resourceId);
		}

		get(null, fieldsName, request, new ScriptureHttpResponseHandler<U>(responseDtoClass)
		{
			@Override
			public void onStart()
			{
				onSimpleListener.onStart();

				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.showLoadingDialog(loadingMessage);
				}
			}

			@Override
			public void onFinish()
			{
				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.dismissLoadingDialog();
				}

				onSimpleListener.onFinish();
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize)
			{
				super.onProgress(bytesWritten, totalSize);

				//处理进度
				onSimpleListener.onProgress(bytesWritten, totalSize);
			}

			@Override
			public void onSuccess(Header[] headers,
								  U responseDto) {

				if (requestable != null)
				{
					requestable.setSuccessful(true);
				}

				onSimpleListener.onSuccess(responseDto);

				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String errorMessage) {

				//Class<? extends BaseEntity> dtoClass = request.getClass();
				//MobclickAgent.reportError(BaseApplication.getInstance(), "request:"+dtoClass.getName()+", statusCode:" + statusCode + ", error:" + errorMessage + ", agent:" + HttpClient.getUserAgent());

				if (requestable != null)
				{
					requestable.setSuccessful(false);
				}

				errorMessage = getErrorString(request, statusCode, headers, errorMessage);

				if (!onSimpleListener.onFailure(errorMessage))
				{
					if (errorMessage != null && requestable != null)
					{
						requestable.showCustomToast(errorMessage);
					}
				}

				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}
		});
	}

	public static <T extends BaseEntity, U extends BaseEntity> void requestServer(final int resourceId, boolean pushNullable, final IServerRequestable requestable, final String loadingMessage, List<String> fieldsName, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<U> onSimpleListener)
	{
		if (resourceId > 0)
		{
			if (RequestingUIResource.contains(resourceId))
			{
				//如果已经有这个id，不响应
				return;
			}
			
			RequestingUIResource.add(resourceId);
		}

		post(null, fieldsName, request, new ScriptureHttpResponseHandler<U>(responseDtoClass)
		{
			@Override
			public void onStart()
			{
				onSimpleListener.onStart();
				
				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.showLoadingDialog(loadingMessage);
				}
			}
			
			@Override
			public void onFinish()
			{
				if (!TextUtils.isEmpty(loadingMessage) && requestable != null)
				{
					requestable.dismissLoadingDialog();
				}
				
				onSimpleListener.onFinish();
			}
			
			@Override
			public void onProgress(int bytesWritten, int totalSize) 
			{
				super.onProgress(bytesWritten, totalSize);
				
				//处理进度
				onSimpleListener.onProgress(bytesWritten, totalSize);
			}
			
			@Override
			public void onSuccess(Header[] headers,
					U responseDto) {
				
				if (requestable != null)
				{
					requestable.setSuccessful(true);
				}
				
				onSimpleListener.onSuccess(responseDto);

				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String errorMessage) {

				//Class<? extends BaseEntity> dtoClass = request.getClass();
				//MobclickAgent.reportError(BaseApplication.getInstance(), "request:"+dtoClass.getName()+", statusCode:" + statusCode + ", error:" + errorMessage + ", agent:" + HttpClient.getUserAgent());
		        
				if (requestable != null)
				{
					requestable.setSuccessful(false);
				}
				
				errorMessage = getErrorString(request, statusCode, headers, errorMessage);
				
				if (!onSimpleListener.onFailure(errorMessage))
				{
					if (errorMessage != null && requestable != null)
					{
						requestable.showCustomToast(errorMessage);
					}
				}
				
				if (resourceId > 0)
				{
					RequestingUIResource.remove(resourceId);
				}
			}
		}, pushNullable);
	}
	
	public static <T extends BaseEntity, U extends BaseEntity> void requestServer(boolean pushNullable, final IServerRequestable requestable, final String loadingMessage, List<String> fieldsName, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<U> onSimpleListener)
	{
		requestServer(-1, pushNullable, requestable, loadingMessage, fieldsName, request, responseDtoClass, onSimpleListener);		
	}
	
	public static <T extends BaseEntity, U extends BaseEntity> void requestServer(final IServerRequestable requestable, final String loadingMessage, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<U> onSimpleListener)
	{
		requestServer(-1, false, requestable, loadingMessage, new ArrayList<String>(), request, responseDtoClass, onSimpleListener);
	}

	public static <T extends BaseEntity, U extends BaseEntity> void getServer(final IServerRequestable requestable, final String loadingMessage, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<U> onSimpleListener)
	{
		getServer(-1, false, requestable, loadingMessage, new ArrayList<String>(), request, responseDtoClass, onSimpleListener);
	}

	public static <T extends BaseEntity, U extends BaseEntity> void getServerList(final IServerRequestable requestable, final String loadingMessage, final T request, final Class<U> responseDtoClass, final OnSimpleActionListener<List<U>> onSimpleListener)
	{
		getServerList(-1, false, requestable, loadingMessage, new ArrayList<String>(), request, responseDtoClass, onSimpleListener);
	}

	private static class DtoInfo
	{
		public String url;
		
		public RouteAttr.HOST_TYPE hostType;
		
		public int mediaType;
		
		public int timeout;
		
		public int retries;
	}

	public static abstract class ScriptureHttpResponseHandler<U extends BaseEntity> extends JsonHttpResponseHandler
	{
		private Class<U> mResponDtoClass;
		public ScriptureHttpResponseHandler(Class<U> responseDtoClass)
		{
			this.mResponDtoClass = responseDtoClass;
		}
		
		public abstract void onSuccess(Header[] headers, U responseDto);
		
		public abstract void onFailure(int statusCode, Header[] headers, String errorMessage);

		@Override
	    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

			try {

				Log.i("ServiceClient", "success:" + response.toString());
				
//				String retCode = response.getString("result");
//				if (!TextUtils.equals(retCode, "0"))
//				{
//					//token过期、登录超时
//					if (TextUtils.equals("-301", retCode) || TextUtils.equals("-500", retCode))
//					{
//						//需要重新登录
//						LoginUtils.jumpLogin(true);
//					}
//					
//					onFailure(Integer.parseInt(retCode), headers, response.toString());
//				}
//				else
				{
					try
					{
						JSONObject data = response;
							
						U responseDto = JSONHelper.parseObject(data, mResponDtoClass);
						
						onSuccess(headers, responseDto);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						onFailure(50000000, headers, e.getMessage());
					}
				}
			
				//onFailure(50000000, headers, "返回数据缺少result字段，信息错误！");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				onFailure(50000000, headers, e.getMessage());
			}
	    }

		@Override
	    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
		{
			Log.i("ServiceClient", "onFailure:" + errorResponse);
			
			onFailure(statusCode, headers, throwable != null ? throwable.toString() : "");
	    }
		
		@Override
	    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			
			Log.i("ServiceClient", "onFailure:" + responseString);
			onFailure(statusCode, headers, throwable != null ? throwable.toString() : "");
			
	    }
	}

	public static abstract class ScriptureHttpListResponseHandler<U extends BaseEntity> extends JsonHttpResponseHandler
	{
		private Class<U> mResponDtoClass;
		public ScriptureHttpListResponseHandler(Class<U> responseDtoClass)
		{
			this.mResponDtoClass = responseDtoClass;
		}

		public abstract void onSuccess(Header[] headers, List<U> responseDto);

		public abstract void onFailure(int statusCode, Header[] headers, String errorMessage);

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

			try
			{
				List<U> list = new ArrayList<U>();

				int length = response.length();
				for (int i = 0; i < length; ++i) {
					JSONObject obj = (JSONObject)response.get(i);

					U responseDto = JSONHelper.parseObject(obj, mResponDtoClass);

					list.add(responseDto);
				}
				//

				onSuccess(headers, list);

			}
			catch(Exception e)
			{
				e.printStackTrace();
				onFailure(50000000, headers, e.getMessage());
			}

		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response){};

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
		{
			Log.i("ServiceClient", "onFailure:" + errorResponse);

			onFailure(statusCode, headers, throwable != null ? throwable.toString() : "");
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

			onFailure(statusCode, headers, throwable != null ? throwable.toString() : "");

		}
	}

	public static interface IServerRequestable
	{
		public void showLoadingDialog(final String text);
		public void dismissLoadingDialog();
		public void showCustomToast(final String text);
		public boolean getSuccessful();
		public void setSuccessful(boolean isSuccessful);
	}

	public static abstract class OnSimpleActionListener<T>
	{
		public abstract void onSuccess(T responseDto);
		
		public abstract boolean onFailure(String errorMessage);
		
		public void onProgress(int bytesWritten, int totalSize) {}
		
		public void onStart(){}
		
		public void onFinish(){}
	}
	
	public static abstract class OnSimpleListActionListener<T>
	{
		public abstract void onSuccess(List<T> responseDto);
		
		public abstract boolean onFailure(String errorMessage);
		
		public void onProgress(int bytesWritten, int totalSize){}
		
		public void onStart(){}
		
		public void onFinish(){}
	}
}
