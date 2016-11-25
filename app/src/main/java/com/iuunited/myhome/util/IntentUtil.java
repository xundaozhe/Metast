package com.iuunited.myhome.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.http.message.BasicNameValuePair;


/**
 * Activity
 * @author xundaozhe
 *
 */
public class IntentUtil {
	
	/**
	 *跳转activity
	 * @param activity
	 * @param cls
	 */
	public static void startActivity(Activity activity, Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public static void startActivityAndFinish(Activity activity, Class<?> clz){
		Intent intent=new Intent();
		intent.setClass(activity,clz);
		activity.startActivity(intent);
		activity.finish();
	}
	
	/**
	 * ����Activity ���������
	 * @param activity
	 * @param cls
	 */
	public static void startActivityFromMain(Activity activity, Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		activity.startActivity(intent);
	}
	/**
	 * ����Activity �������
	 * @param activity
	 * @param cls
	 * @param
	 */
	public static void startActivity(Activity activity, Class<?> cls, Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	public static void startActivityFromMain(Activity activity, Class<?> cls, Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public static void startActivityFromMainAndFinish(Activity activity, Class<?> cls, Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.finish();
//		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public static void startActivityFromMain1(Activity activity, Class<?> cls, Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		//activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	/**
	 * ����service
	 * @param activity
	 * @param cls
	 * @param params
	 */
	public static void startService(Activity activity, Class<?> cls, BasicNameValuePair...params){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		for(int i=0;i<params.length;i++){
			intent.putExtra(params[i].getName(), params[i].getValue());
		}
		activity.startService(intent);
	}
}
