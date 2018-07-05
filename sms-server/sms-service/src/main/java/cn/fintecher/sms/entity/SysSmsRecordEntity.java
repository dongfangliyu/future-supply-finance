package cn.fintecher.sms.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author integration
 * @email 
 * @date 2017-05-31 14:31:05
 */
public class SysSmsRecordEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final static Integer STATUS_NO = 0;
	
	public final static Integer STATUS_YES = 1;
	//
	private Long id;
	//
	private Integer status;
	//
	private String mobile;
	//
	private String sys_number;
	//
	private String remark;
	//
	private String send_time;
	//
	private Long c_id;
	//
	private String service_no;
	//
	private String content;
	//
	private String send_ip;
	
	private String msgid;
	
	private String channel_code;
	
	private Integer retryNumber = 0;
	
	private String type;
	
	private String number;
	
	private String universal;
	
	//调用接口耗时
	private Long timeConsuming;
	
	//发送消息后，创蓝或乾景通返回状态
	private String smsState;
	
	//回调返回状态
	private String receiveState;
	
	//修改时间
	private String updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSys_number() {
		return sys_number;
	}
	public void setSys_number(String sys_number) {
		this.sys_number = sys_number;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	public String getService_no() {
		return service_no;
	}
	public void setService_no(String service_no) {
		this.service_no = service_no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_ip() {
		return send_ip;
	}
	public void setSend_ip(String send_ip) {
		this.send_ip = send_ip;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public Integer getRetryNumber() {
		return retryNumber;
	}
	public void setRetryNumber(Integer retryNumber) {
		this.retryNumber = retryNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUniversal() {
		return universal;
	}
	public void setUniversal(String universal) {
		this.universal = universal;
	}
	public Long getTimeConsuming() {
		return timeConsuming;
	}
	public void setTimeConsuming(Long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
	
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
	public String getReceiveState() {
		return receiveState;
	}
	public void setReceiveState(String receiveState) {
		this.receiveState = receiveState;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "SysSmsRecordEntity [id=" + id + ", status=" + status + ", mobile=" + mobile + ", sys_number="
				+ sys_number + ", remark=" + remark + ", send_time=" + send_time + ", c_id=" + c_id + ", service_no="
				+ service_no + ", content=" + content + ", send_ip=" + send_ip + ", msgid=" + msgid + ", channel_code="
				+ channel_code + ", retryNumber=" + retryNumber + ", type=" + type + ", number=" + number
				+ ", universal=" + universal + ", timeConsuming=" + timeConsuming + ", smsState=" + smsState
				+ ", receiveState=" + receiveState + ", updateTime=" + updateTime + "]";
	}
	
	
}
