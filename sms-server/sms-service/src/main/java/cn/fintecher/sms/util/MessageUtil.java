package cn.fintecher.sms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.fintecher.sms.util.ucpaas.restDemo.SysConfig;

/**
 * 
 * 
 * Classname Version 1.2
 * 
 * @author panye 2015-1-9 Copyright notice
 */
public class MessageUtil {
	public static final String USER_NAME = "zzl123cfzx";// 帐号
	public static final String PASSWORD = "12345678";// 密码
	public static final String COMPANY_NAME = "【中资联贝尔在线】";
	public static final String Ext = "002";

	public static boolean isTest = Boolean.parseBoolean(SysConfig.getInstance().getProperty("sms_is_test"));

	/**
	 * 
	 *  函数功能说明   panye  2014-11-29  修改内容   @param  {mobile:手机号码,message:短信内容}
	 *  @return   OK 成功，error 失败  @throws 
	 */
	public static String sendMessage(String mobile, String message)	throws IOException {
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>"
					+ message);
			return "OK";
		}
		try {
			message = URLEncoder.encode(COMPANY_NAME + message, "GBK");
		} catch (UnsupportedEncodingException e1) {
			return "error";
		}

		String theurl = "http://www.ydqxt.com:8080/sendsms.asp?username="
				+ USER_NAME + "&password=" + PASSWORD + "&mobile=" + mobile
				+ "&message=" + message + "&Ext=" + Ext;
		int i_ret = 0;
		BufferedReader reader = null;
		HttpURLConnection httpCon = null;
		try {
			URL url = new URL(theurl);
			// 返回URLConnection子类对象
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.connect();

			// 开始发送请求
			reader = new BufferedReader(new InputStreamReader(
					httpCon.getInputStream()));
			i_ret = httpCon.getResponseCode();// 200表示成功
			if (i_ret == 200) {

				return httpCon.getResponseMessage();
			} else {

				return "error";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			httpCon.disconnect();// 断开
		}
		return "error";
	}

	public static void main(String[] args) throws Exception {
		System.out.println(sendMessage("13179640727","TEST"));
	}
}
