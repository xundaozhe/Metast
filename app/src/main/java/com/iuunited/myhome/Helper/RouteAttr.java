package com.iuunited.myhome.Helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //类级别
public @interface RouteAttr {

	public enum HOST_TYPE{Default, H5};

	public static int IMAGE_TYPE = 0;

	//短超时
	public static int SHORT_TIME_OUT  	= 8000;
	//长超时
	public static int LONG_TIME_OUT		= 60000;
	//中等超时，20s
	public static int MIDDLE_TIME_OUT	= 20000;
	
	String url() default "";	//服务url地址

	HOST_TYPE hostType() default HOST_TYPE.Default;
	
	//超时时间
	int timeout() default SHORT_TIME_OUT;
	
	//重试次数
	int retries() default 3;
}
