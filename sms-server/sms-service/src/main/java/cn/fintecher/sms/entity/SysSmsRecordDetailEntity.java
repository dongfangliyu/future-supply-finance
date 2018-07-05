package cn.fintecher.sms.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-06-05 16:10:34
 */
public class SysSmsRecordDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Integer status;
	//
	private String mobile;
	//
	private String sysNumber;
	//
	private String remark;
	//
	private String sendTime;
	//
	private Long cId;
	//
	private String serviceNo;
	//
	private String content;
	//
	private String sendIp;
	//短信服提交返回的msgid，这个工状态报告匹配时使用
	private String msgId;
	//短信通道编码，详见短信相关资料文档；
	private String channelCode;
	//
	private Integer retryNumber;
	//
	private String type;
	//
	private String number;
	//响应状态码
	private String statusCode;
	//响应信息
	private String meseage;
	//
	private Long recordId;
	
	private String universal;
	
	//调用接口耗时
	private Long timeConsuming;
	
	//回调放回状态
	private String receiveState;
	
	//发短信返回状态
	private String smsState;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：
	 */
	public void setSysNumber(String sysNumber) {
		this.sysNumber = sysNumber;
	}
	/**
	 * 获取：
	 */
	public String getSysNumber() {
		return sysNumber;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * 获取：
	 */
	public String getSendTime() {
		return sendTime;
	}
	/**
	 * 设置：
	 */
	public void setCId(Long cId) {
		this.cId = cId;
	}
	/**
	 * 获取：
	 */
	public Long getCId() {
		return cId;
	}
	/**
	 * 设置：
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	/**
	 * 获取：
	 */
	public String getServiceNo() {
		return serviceNo;
	}
	/**
	 * 设置：
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}
	/**
	 * 获取：
	 */
	public String getSendIp() {
		return sendIp;
	}
	/**
	 * 设置：短信服提交返回的msgid，这个工状态报告匹配时使用
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	/**
	 * 获取：短信服提交返回的msgid，这个工状态报告匹配时使用
	 */
	public String getMsgId() {
		return msgId;
	}
	/**
	 * 设置：短信通道编码，详见短信相关资料文档；
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	/**
	 * 获取：短信通道编码，详见短信相关资料文档；
	 */
	public String getChannelCode() {
		return channelCode;
	}
	/**
	 * 设置：
	 */
	public void setRetryNumber(Integer retryNumber) {
		this.retryNumber = retryNumber;
	}
	/**
	 * 获取：
	 */
	public Integer getRetryNumber() {
		return retryNumber;
	}
	/**
	 * 设置：
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：响应状态码
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * 获取：响应状态码
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * 设置：响应信息
	 */
	public void setMeseage(String meseage) {
		this.meseage = meseage;
	}
	/**
	 * 获取：响应信息
	 */
	public String getMeseage() {
		return meseage;
	}
	/**
	 * 设置：
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：
	 */
	public Long getRecordId() {
		return recordId;
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
	public String getReceiveState() {
		return receiveState;
	}
	public void setReceiveState(String receiveState) {
		this.receiveState = receiveState;
	}
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
}
