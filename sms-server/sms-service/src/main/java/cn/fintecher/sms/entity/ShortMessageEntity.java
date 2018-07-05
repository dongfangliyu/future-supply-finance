package cn.fintecher.sms.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信实体
 * 
 */
public class ShortMessageEntity implements Serializable {

	private static final long serialVersionUID = -6012190313033329895L;

	private String mobile;
	private String number;
	private String sysNumber;
	private JSONObject params;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSysNumber() {
		return sysNumber;
	}

	public void setSysNumber(String sysNumber) {
		this.sysNumber = sysNumber;
	}

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ShortMessageEntity [mobile=" + mobile + ", number=" + number + ", sysNumber=" + sysNumber + ", params="
				+ params + "]";
	}

}
