package cn.fintecher.sms.vo;

import java.io.Serializable;

public class SmsResponse implements Serializable {

	private static final long serialVersionUID = 9041025160610584440L;
	
	private boolean success;
	
	private int status;
	
	private String message;
	
	private String msgId;
	
	private String statusCode;
	
	private String smsState;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSmsState() {
		return smsState;
	}

	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
	

}
