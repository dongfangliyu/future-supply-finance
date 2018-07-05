package cn.fintecher.sms.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.utils.validator.group.AddGroup;
import cn.fintecher.sms.utils.validator.group.UpdateGroup;



/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-28 00:10:02
 */
public class SysSmsContentEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板内容
	@NotBlank(message="模板内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String content;
	//模板编号
	@NotBlank(message="编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String number;
	//
	private String sendTime;
	//
	private String updateTime;
	//类型
	private Integer type;
	//状态
	private Integer status;
	//
	private String aim;
	//备注
	private String remark;
	
	//适用性
	private String universal;

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
	public void setUpdateTime(String updateTime) {
		if(StringUtils.isBlank(updateTime)){
			this.updateTime = Time.getCurrentTime();
		}else{
			this.updateTime = updateTime;
		}
		
	}
	/**
	 * 获取：
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public Integer getType() {
		return type;
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
	public void setAim(String aim) {
		this.aim = aim;
	}
	/**
	 * 获取：
	 */
	public String getAim() {
		return aim;
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
	public String getUniversal() {
		return universal;
	}
	public void setUniversal(String universal) {
		this.universal = universal;
	}
}
