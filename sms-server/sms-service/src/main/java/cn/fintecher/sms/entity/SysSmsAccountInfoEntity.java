package cn.fintecher.sms.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import cn.fintecher.sms.utils.validator.group.AddGroup;
import cn.fintecher.sms.utils.validator.group.UpdateGroup;



/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-06-01 15:12:13
 */
public class SysSmsAccountInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//整型主键递增
	private Integer id;
	
	//短信请求应用地址
	@NotBlank(message="请求应用地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String uri;
	
	//应用账号
	@NotBlank(message="应用账号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String account;
	
	//应用密码
	@NotBlank(message="应用密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String password;
	
	//短信通道编码，详见短信相关资料文档；
	@NotBlank(message="短信通道编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String channelCode;
	
	//通道备注信息
	private String channelRemark;
	
	//是否需要状态报告：1：需要true；0：不需要false；
	private String neededReportStatus = "1";
	
	//产品编码
	private String productCode;
	
	//扩展码
	private String extendedCode;
	
	//启用状态；1：启用；2：停用；
	private String status;
	
	//创建时间
	private String createTime;
	
	//更新时间
	private String updateTime;
	
	//消息类型 1:应用类消息；2:营销类消息；
	@NotBlank(message="消息类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String type;
	
	//【短信平台 】1: 创蓝平台；2:可扩展字段
	@NotBlank(message="短信平台不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String flat;

	/**
	 * 设置：整型主键递增
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：整型主键递增
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：短信请求应用地址
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * 获取：短信请求应用地址
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * 设置：应用账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：应用账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：应用密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：应用密码
	 */
	public String getPassword() {
		return password;
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
	 * 设置：通道备注信息
	 */
	public void setChannelRemark(String channelRemark) {
		this.channelRemark = channelRemark;
	}
	/**
	 * 获取：通道备注信息
	 */
	public String getChannelRemark() {
		return channelRemark;
	}
	/**
	 * 设置：是否需要状态报告：
1：需要true；0：不需要false；
	 */
	public void setNeededReportStatus(String neededReportStatus) {
		this.neededReportStatus = neededReportStatus;
	}
	/**
	 * 获取：是否需要状态报告：
1：需要true；0：不需要false；
	 */
	public String getNeededReportStatus() {
		return neededReportStatus;
	}
	/**
	 * 设置：产品编码
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 获取：产品编码
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * 设置：扩展码
	 */
	public void setExtendedCode(String extendedCode) {
		this.extendedCode = extendedCode;
	}
	/**
	 * 获取：扩展码
	 */
	public String getExtendedCode() {
		return extendedCode;
	}
	/**
	 * 设置：启用状态；1：启用；2：停用；
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：启用状态；1：启用；2：停用；
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	@Override
	public String toString() {
		return "SysSmsAccountInfoEntity [id=" + id + ", uri=" + uri + ", account=" + account + ", password=" + password
				+ ", channelCode=" + channelCode + ", channelRemark=" + channelRemark + ", neededReportStatus="
				+ neededReportStatus + ", productCode=" + productCode + ", extendedCode=" + extendedCode + ", status="
				+ status + ", createTime=" + createTime + ", updateTime=" + updateTime + ", type=" + type + ", flat="
				+ flat + "]";
	}
}
