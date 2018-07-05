package cn.fintecher.sms.util;


import org.slf4j.Logger;

import cn.fintecher.sms.util.ucpaas.restDemo.SysConfig;
import cn.fintecher.sms.util.ucpaas.restDemo.client.JsonReqClient;
import net.sf.json.JSONObject;


public class VoiceMessageUtil {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(VoiceMessageUtil.class);
	
	public final static String ACCOUNTID = "560f12323352f2e49230f3d8273314eb";
	public final static String AUTOTOKEN = "2f13254db878f2069fd1f8a6f5d2c279";
	public final static String APPLYID = "89eb3bb1e1d44015a5ea62db9bfc3931";
	public final static String PLAY_TIMES = "3";
	
	public static boolean isTest = Boolean.parseBoolean(SysConfig.getInstance().getProperty("sms_is_test"));
	
	/**
	 * 
	 * 函数功能说明 			发送语音短信	北京 云通讯
	 * panye  2014-11-29
	 * 修改内容 		
	 * @param 			mobile 手机  code 验证码
	 * @return   
	 * @throws 
	 */
	public static boolean sendVoiceMsg(String mobile,String code) {
		if (isTest) {
			System.out.println("=========================>>>>>>>>>>>>>>"+ code);
			return true;
		}
		
		JsonReqClient client = new JsonReqClient();
		try {
			String str  = client.voiceCode(ACCOUNTID, AUTOTOKEN, APPLYID, mobile, code, "4000686600", PLAY_TIMES);
			JSONObject result = JSONObject.fromObject(str);
			JSONObject jsonObject = (JSONObject) result.get("resp");
//			System.out.println(jsonObject);
			if("000000".equals(jsonObject.get("respCode"))){
				return true;
			}else{
				//异常返回输出错误码和错误信息
				logger.warn(Time.getCurrentTime() + "，手机号:"+ mobile +"错误码=" + result.get("respCode") +" 错误信息= "+result.get("voiceCode"));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage(),e);
			logger.warn(Time.getCurrentTime() + "，发送语音验证码时出现异常。手机号码："+ mobile);
			return false;
		}
	}
	
}
