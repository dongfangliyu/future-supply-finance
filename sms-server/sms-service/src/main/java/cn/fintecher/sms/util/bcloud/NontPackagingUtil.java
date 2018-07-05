package cn.fintecher.sms.util.bcloud;

import org.apache.log4j.Logger;

import cn.fintecher.sms.util.ChkUtil;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.util.ucpaas.restDemo.SysConfig;

/**
 * 功能说明：该类主要用于对手机用户手机发送验证码（mobile表示手机号码，message表示短信内容）
 * 创建者：乔春锋
 * 创建时间：2015/5/26（下午）
 * @author admin
 * 修改人：暂无
 * 修改时间：暂无
 * 修改原因：暂无
 * 修改内容：暂无
 */
public class NontPackagingUtil {
	private static final Logger logger = Logger.getLogger(NontPackagingUtil.class);
	
	public static boolean isTest = Boolean.parseBoolean(SysConfig.getInstance().getProperty("sms_is_test"));
	/**
	 * 
	 * 功能说明：该方法实现的功能			创蓝发送短信平台 签名贝尔在线
	 * panye  2015-6-5
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：panye
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String sendMessage(String mobile,String message){
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>[贝尔在线]" + message);
			return "OK";
		}
		String result = "";
		String uri = "http://222.73.117.156/msg/";//应用地址
		String account = "vip_zzlcf";//账号
		String pswd = "Tch123456";//密码
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		try {
			
			result = HttpSender.batchSend(uri, account, pswd, mobile, message, needstatus, product, extno);
			
			if(ChkUtil.isEmpty(result)){
				return "error";
			}
			
			String strs [] = result.split(",");
			if(!strs[1].toString().substring(0, 1).equals("0")){
				logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
				return "error";
			}
		} catch (Exception e) {
			logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
//			e.printStackTrace();
			return "error";
		}
		
		return "OK";
	}
	
	/**
	 * 
	 * 功能说明：该方法实现的功能			创蓝发送短信平台	 签名中资联
	 * panye  2015-6-5
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：		2015-08-05新增签名通道接口
	 * 修改人：panye
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String sendMessageByZZL(String mobile,String message){
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>[中资联]"+ message);
			return "OK";
		}
		String result = "";
		String uri = "http://222.73.117.156/msg/";//应用地址
		String account = "vip_zwxx";//账号
		String pswd = "Tch123456";//密码
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		try {
			
			result = HttpSender.batchSend(uri, account, pswd, mobile, message, needstatus, product, extno);
			
			if(ChkUtil.isEmpty(result)){
				return "error";
			}
			
			String strs [] = result.split(",");
			if(!strs[1].toString().substring(0, 1).equals("0")){
				logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
				return "error";
			}
		} catch (Exception e) {
			logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
//			e.printStackTrace();
			return "error";
		}
		
		return "OK";
	}
	
	/**
	 * 
	 * 功能说明：该方法实现的功能			创蓝发送短信平台	 签名中资联
	 * panye  2015-6-5
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：		2015-08-05新增签名通道接口
	 * 修改人：panye
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String sendMessageByKaiBei(String mobile,String message){
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>[开呗]"+ message);
			return "OK";
		}
		String result = "";
		String uri = "http://222.73.117.158/msg/";//应用地址
		String account = "zhiwang88_kaibei";//账号
		String pswd = "Admin888";//密码
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		try {
			
			result = HttpSender.batchSend(uri, account, pswd, mobile, message, needstatus, product, extno);
			
			if(ChkUtil.isEmpty(result)){
				return "error";
			}
			
			String strs [] = result.split(",");
			if(!strs[1].toString().substring(0, 1).equals("0")){
				logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
				return "error";
			}
		} catch (Exception e) {
			logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
//			e.printStackTrace();
			return "error";
		}
		
		return "OK";
	}
	
	
	/**
	 * 
	 * 功能说明：该方法实现的功能			创蓝发送短信平台	 签名中资联
	 * panye  2015-6-5
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：		2015-08-05新增签名通道接口
	 * 修改人：panye
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String sendMessageByRongzi(String mobile,String message){
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>[群泰]"+ message);
			return "OK";
		}
		String result = "";
		String uri = "http://222.73.117.158/msg/";//应用地址
		String account = "zhiwang88_quntai";//账号
		String pswd = "Admin888";//密码
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		try {
			
			result = HttpSender.batchSend(uri, account, pswd, mobile, message, needstatus, product, extno);
			
			if(ChkUtil.isEmpty(result)){
				return "error";
			}
			
			String strs [] = result.split(",");
			if(!strs[1].toString().substring(0, 1).equals("0")){
				logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
				return "error";
			}
		} catch (Exception e) {
			logger.warn(Time.getCurrentTime() + ",发送短信时失败，手机号码:" + mobile + "，信息内容：" + message + "，返回信息：" + result);
//			e.printStackTrace();
			return "error";
		}
		
		return "OK";
	}
	
	public static void main(String[] args) throws Exception {
		String s = sendMessageByZZL("18321972003", "亲爱的用户，您的手机验证码是5999，此验证码5分钟内有效");
		System.out.println(s);
	}
}
