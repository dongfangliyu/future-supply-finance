package cn.fintecher.sms.vo;

public class SysParamVO {
	
	//开关
	private boolean sendSwitch;
	
	//重试次数
	private int retryNumber;
	
	//休眠时间
	private int intervalTime;

	public boolean isSendSwitch() {
		return sendSwitch;
	}

	public void setSendSwitch(boolean sendSwitch) {
		this.sendSwitch = sendSwitch;
	}

	public int getRetryNumber() {
		return retryNumber;
	}

	public void setRetryNumber(int retryNumber) {
		this.retryNumber = retryNumber;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public String toString() {
		return "SysParamVO [sendSwitch=" + sendSwitch + ", retryNumber=" + retryNumber + ", intervalTime="
				+ intervalTime + "]";
	}
	
}
