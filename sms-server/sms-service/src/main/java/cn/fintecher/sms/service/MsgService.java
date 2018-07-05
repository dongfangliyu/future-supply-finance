package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.ShortMessageEntity;
import cn.fintecher.sms.entity.SmsEntity;

public interface MsgService {
	
	public String sendMsg(ShortMessageEntity shortMessageEntity);
	public String sendMsgDate(SmsEntity smsEntity);
	
	public void receiveResponseState(String msgid,String mobile,String status);
	
	public void retryBatchSend(Long[] ids) throws Exception ;

}
