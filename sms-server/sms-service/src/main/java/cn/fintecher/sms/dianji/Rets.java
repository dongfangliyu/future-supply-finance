package cn.fintecher.sms.dianji;

public class Rets {
	private Integer Rspcode;
	private String Msg_Id;
	private String Mobile;
	private Integer Fee;
	
	public Integer getRspcode() {
		return Rspcode;
	}
	public void setRspcode(Integer rspcode) {
		Rspcode = rspcode;
	}
	public String getMsg_Id() {
		return Msg_Id;
	}
	public void setMsg_Id(String msg_Id) {
		Msg_Id = msg_Id;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public Integer getFee() {
		return Fee;
	}
	public void setFee(Integer fee) {
		Fee = fee;
	}
	
	@Override
	public String toString() {
		return "Rets [Rspcode=" + Rspcode + ", Msg_Id=" + Msg_Id + ", Mobile=" + Mobile + ", Fee="
				+ Fee + "]";
	}

}
