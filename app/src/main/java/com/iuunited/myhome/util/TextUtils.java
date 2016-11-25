package com.iuunited.myhome.util;

import android.content.Context;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author youngmac
 * 
 */
public class TextUtils {


	/**
	 * 获取括号中的国家区号
	 * 
	 * @param text
	 *            带有括号的国家区号
	 * @param defaultText
	 *            默认的国家区号(在获取错误时返回该值)
	 * @return
	 */
	public static String getCountryCodeBracketsInfo(String text,
													String defaultText) {
		if (text.contains("(") && text.contains(")")) {
			int leftBrackets = text.indexOf("(");
			int rightBrackets = text.lastIndexOf(")");
			if (leftBrackets < rightBrackets) {
				return "+" + text.substring(leftBrackets + 1, rightBrackets);
			}
		}
		if (defaultText != null) {
			return defaultText;
		} else {
			return text;
		}
	}

	/**
	 * 验证手机号码格式是否正确
	 * @param mobiles
	 * @return
     */
	public static boolean isMobileNO(String mobiles) {
		//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//		String telRegex = "^1[0-9][0-9]{9}$" ;
		String telRegex = "^[1][3,4,5,7,8][0-9]{9}$" ;
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		}
		else {
			return mobiles.matches( telRegex );
		}
	}

	public static boolean isEmail(String email){
		String emailRegex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
		if(TextUtils.isEmpty(email)) {
		    return false;
		}else{
			return email.matches(emailRegex);
		}
	}

	/**
	 * 获取Assets中的json文本
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            文本名称
	 * @return
	 */
	public static String getJson(Context context, String name) {
		if (name != null) {
			String path = "json/" + name;
			InputStream is = null;
			try {
				is = context.getAssets().open(path);
				return readTextFile(is);
			} catch (IOException e) {
				return null;
			} finally {
				try {
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {

				}
			}
		}
		return null;
	}

	/**
	 * 从输入流中获取文本
	 * 
	 * @param inputStream
	 *            文本输入流
	 * @return
	 */
	public static String readTextFile(InputStream inputStream) {
		String readedStr = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				readedStr += tmp;
			}
			br.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return readedStr;
	}

	// 如果是大写字母就转化成小写字母
	public static char upperCaseToLowerCase(char ch) {
		if (ch >= 65 && ch <= 90) {
			ch = (char) (ch + 32);
		}

		return ch;
	}

	// 如果小写转化成大写
	public static char lowerCaseToUpperCase(char ch) {
		if (ch > 90 && ch <= 115) {
			ch = (char) (ch - 32);
		}

		return ch;
	}
	
	public static boolean isLetter(char ch)
	{
		if (ch >= 65 && ch <= 90 || ch >=97 && ch <= 122)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isEmpty(TextView textView)
	{
		return isEmpty(getText(textView));
	}
	
	public static boolean isEmpty(CharSequence content) {
		return content == null || content.length() == 0;
	}

	public static boolean isEmpty(String content) {
		return content == null || content.length() == 0;
	}

	public static String getText(TextView textView) {
		return textView.getText().toString().trim();
	}

	/**
	 * 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
	 * 
	 *            短信内容
	 * @return 截取得到的6位动态密码
	 */
	public static String getDynamicPassword(String smsBody) {
		// 6是验证码的位数一般为六位
		Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{"
				+ 6 + "})(?![0-9])");

		Matcher matcher = continuousNumberPattern.matcher(smsBody);
		String dynamicPassword = "";
		while (matcher.find()) {
			dynamicPassword = matcher.group();
		}

		return dynamicPassword;
	}
	
	public static boolean matchDate(String text)
	{
		if (Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})").matcher(text).matches()) {
			return true;
		}
		
		return false;
	}
	
	public static boolean matchDateTime(String text)
	{
		if (Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})").matcher(text).matches()) {
			return true;
		}
		
		return false;
	}

	public static boolean matchPhone(String text) {
//		if (Pattern.compile("(\\d{11})|(\\+\\d{3,})").matcher(text).matches()) {
//			return true;
//		}
		if (Pattern.compile("(\\d{11})").matcher(text).matches()) {
			return true;
		}
		
		return false;
	}

	public static boolean matchEmail(String text) {
		if (Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text)
				.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean equals(CharSequence src, CharSequence target)
	{
		if (src == null || target == null)
		{
			return src == null && target == null;
		}
		
		return android.text.TextUtils.equals(src, target);
	}
	
	public static boolean contains(String[] containers, String target)
	{
		for(String s : containers)
		{
			if (equals(s, target))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static String getUnreadString(int unreadCount)
	{
		if (unreadCount == 0)
		{
			return "";
		}
		else if (unreadCount <= 99)
		{
			return String.valueOf(unreadCount);
		}
		else
		{
			return "...";
		}
	}
	
	public static int convertToInt(String str)
	{
		if (str == null)
		{
			return 0;
		}
		
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public static long convertToLong(String str)
	{
		if (str == null)
		{
			return 0;
		}
		
		try
		{
			return Long.parseLong(str);
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public static double convertToDouble(String str)
	{
		if (str == null)
		{
			return 0.0;
		}
		
		try
		{
			return Double.parseDouble(str);
		}
		catch(Exception ex)
		{
			return 0.0;
		}
	}

	public static String ListToString(List<?> list) {
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == null || list.get(i) == "") {
					continue;
				}

				sb.append(list.get(i));
			}
		}
		return sb.toString();
	}
}
