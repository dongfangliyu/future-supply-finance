package cn.fintecher.sms.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信实体
 * 
 */
public class SmsEntity implements Serializable {

	private static final long serialVersionUID = -6012190313033329895L;

	private String mobile;//电话
	private String templateNo;//模版编号
	private String channel;//渠道
	private JSONObject params;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ShortMessageEntity [mobile=" + mobile + ", channel=" + channel + ", templateNo=" + templateNo + ", params="
				+ params + "]";
	}

}
