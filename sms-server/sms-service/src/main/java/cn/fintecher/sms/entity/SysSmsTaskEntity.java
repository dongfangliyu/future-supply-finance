package cn.fintecher.sms.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-31 14:31:05
 */
public class SysSmsTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static Integer SEND_NO = 0;
	public final static Integer SEND_YES = 1;
	//
	private Long id;
	//
	private String mobile;
	//
	private String sendTime;
	//
	private Long cId;
	//
	private Integer priority;
	//
	private String content;
	//
	private Integer status;

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
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/**
	 * 获取：
	 */
	public Integer getPriority() {
		return priority;
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
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
}
