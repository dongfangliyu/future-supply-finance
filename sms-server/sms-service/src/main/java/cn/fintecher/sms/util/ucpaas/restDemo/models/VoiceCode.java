/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package cn.fintecher.sms.util.ucpaas.restDemo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "voiceCode")
public class VoiceCode {
	
	//应用Id
	private String appId;
	
	//验证码内容[为数字0~9长度为4或6]
	private String captchaCode;
	
	//语音播放次数
	private String playTimes;
	
	//接收号码
	private String to;
	
	//显示的主叫号码
	private String displayNum;
	
	//用户透传数据，语音验证码状态通知中获取该数据
	private String userData;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDisplayNum() {
		return displayNum;
	}
	public void setDisplayNum(String displayNum) {
		this.displayNum = displayNum;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	public String getPlayTimes() {
		return playTimes;
	}
	public void setPlayTimes(String playTimes) {
		this.playTimes = playTimes;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	
}
