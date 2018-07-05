package cn.fintecher.sms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fintecher.sms.admin.AbstractController;
import cn.fintecher.sms.entity.ShortMessageEntity;
import cn.fintecher.sms.entity.SmsEntity;
import cn.fintecher.sms.service.CommonMsgService;
import cn.fintecher.sms.service.MsgService;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.utils.R;
import cn.fintecher.sms.utils.SignatureUtil;


/**
 * 发送短信
 */
@RestController
public class SendMsgController extends AbstractController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgController.class);
	
	private final static String SIGN_SWITCH = "signSwitch";
	
	@Autowired
	private MsgService msgService;
	
	@Autowired
	CommonMsgService commonMsgService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 发送短信
	 */
	/*@RequestMapping(value ="/sendMsg.html", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String sendMsg(@RequestBody ShortMessageEntity shortMessageEntity, @RequestHeader("authorization") String authorization){
		
		LOGGER.debug("start sendMsg shortMessageEntity: " + shortMessageEntity.toString());
		
		boolean signSwtich = Boolean.parseBoolean(sysConfigService.getValue(SIGN_SWITCH, "true"));
		// 数据验证
		if(signSwtich && !SignatureUtil.chkSignature(authorization)) {
			logger.error("验签失败");
			return Constant.STATUS_SIGN_ERROR;
		}
		String response = msgService.sendMsg(shortMessageEntity);
		
		LOGGER.debug("end sendMsg: " + response);
		return response;
	}*/
	
	/**
	 * 发送短信
	 */
	@RequestMapping(value ="/sendMsg.html", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String sendMsg(@RequestBody SmsEntity smsEntity, @RequestHeader(value = "authorization", required = false, defaultValue = "accept") String authorization){
		
		LOGGER.debug("start sendMsg SmsEntity: " + smsEntity.toString());
		
		//boolean signSwtich = Boolean.parseBoolean(sysConfigService.getValue(SIGN_SWITCH, "true"));
		// 数据验证
		/*if(signSwtich && !SignatureUtil.chkSignature(authorization)) {
			logger.error("验签失败");
			return Constant.STATUS_SIGN_ERROR;
		}*/
		String response = commonMsgService.sendMsgDate(smsEntity);
		
		LOGGER.debug("end sendMsg: " + response);
		return response;
	}
	
	
//	@RequestMapping(value="/receiveState")
//	public void receiveState(String receiver, String pswd, String msgid, String reportTime, String mobile, String status){
//		msgService.receiveResponseState(msgid, mobile, status);
//	}
	
//	@RequestMapping(value="/retryBatchSend")
//	public R retryBatchSend(@RequestBody Long[] ids){
//		try {
//			msgService.retryBatchSend(ids);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("重试出错", e);
//			return R.error("重试出错");
//		}
//		return R.ok();
//	}
	
}
