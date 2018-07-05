package cn.fintecher.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import net.sf.json.JSONObject;




/**
 * 
 * Classname  		数据连接与访问的加密验证处理
 * Version	  1.2
 * @author panye
 * 2015-4-17
 * Copyright notice
 */
public class SignatureUtil {
	
	private final static String echostr = "zzl_apt";		//随机签名字符串

	/**
	 * 
	 * 函数功能说明 				加密算法(统一字符串：AccountId：time：token)SHa1加密生成十六进制字符串
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 			applyId  申请ID（用户名）		token 标识 随机产生	time时间戳（YYMMDD HHmmss）
	 * @return   
	 * @throws 
	 */
	public static String getSignature(String AccountId,String time){
		String str = echostr + ":" + AccountId + ":" + time ;
		return new String(new SHA1().getDigestOfString(str.getBytes()));
	}
	
	
	/**
	 * 
	 * 函数功能说明 			验证加密算法	(zzl_apt:bfb84f59580194903632eb0766ed4b649680b46c:20150420225059)
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static boolean chkSignature(String AccountId,String time,String signature){
		String str = echostr + ":" + AccountId + ":" + time;
		String encryStr = new String(new SHA1().getDigestOfString(str.getBytes()));
		if(signature.equals(encryStr))
			return true;
		
		return false;
	}
	
	/**
	 * 验证
	 * @param signStr
	 * @return
	 */
	public static boolean chkSignature(String signStr) {
		
		try {
			
			JSONObject json = JSONObject.fromObject(signStr);
			String timestamp = json.getString("timestamp");
			String accountId = json.getString("accountId");
			String sign = json.getString("sign");
			
			String shaSignStr = getSHA1(echostr, timestamp, accountId);
			return sign.equals(shaSignStr);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	private static String getSHA1(String token, String timestamp, String accountId) throws NoSuchAlgorithmException  {
        String[] array = new String[] { token, timestamp, accountId };
        StringBuffer sb = new StringBuffer();
        // 字符串排序
        Arrays.sort(array);
        for (int i = 0; i < 3; i++) {
            sb.append(array[i]);
        }
        String str = sb.toString();
        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
	}
	
	
	
	
}
