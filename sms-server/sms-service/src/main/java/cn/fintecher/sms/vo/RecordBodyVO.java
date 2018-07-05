package cn.fintecher.sms.vo;

import java.io.Serializable;

/**
 * 短信实体
 * 
 */
public class RecordBodyVO implements Serializable {

	private static final long serialVersionUID = 6983193909146529929L;
	
	private int pageNo;
	
	private int pageSize;
	
	private String telephone;
	
	private String beginTime;
	
	private String endTime;
	
	private String remark;
	
	private String status;
	
	private String sysNumber;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSysNumber() {
		return sysNumber;
	}
	public void setSysNumber(String sysNumber) {
		this.sysNumber = sysNumber;
	}
	@Override
	public String toString() {
		return "RecordBodyVO [pageNo=" + pageNo + ", pageSize=" + pageSize + ", telephone=" + telephone + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", remark=" + remark + ", status=" + status + ", sysNumber="
				+ sysNumber + "]";
	}
}
