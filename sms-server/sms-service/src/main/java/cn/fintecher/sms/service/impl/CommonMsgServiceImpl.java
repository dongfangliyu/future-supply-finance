package cn.fintecher.sms.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.fintecher.sms.entity.SmsEntity;
import cn.fintecher.sms.entity.SysSmsContentEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.CommonMsgService;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.service.SysSmsContentService;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.util.CommonSendMessge;
import cn.fintecher.sms.util.HttpSendMessge;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.utils.template.TemplateUtils;
import cn.fintecher.sms.vo.SmsResponse;
@Service("commonMsgService")
public class CommonMsgServiceImpl implements CommonMsgService{
	private final static String SEND_PRIORITY = "sendPriority";
	private final static String SYS_RETRY_NUMBER = "sysRetryNumber";
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgServiceImpl.class);
	
	@Autowired
	private SysSmsRecordService sysSmsRecordService;
	
	@Autowired
	private SysSmsContentService sysSmsContentService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired 
	private HttpSendMessge httpSendMessge;
	
	@Autowired 
	private CommonSendMessge commonSendMessge;
	/**
	 * 发送消息
	 * @param sysSmsRecordEntity
	 * @return
	 * @throws Exception
	 */
	private String send(SysSmsRecordEntity sysSmsRecordEntity) throws Exception {
		SmsResponse smsResponse = new SmsResponse();
		smsResponse = httpSendMessge.sendMsg(sysSmsRecordEntity);
		return smsResponse.getStatusCode();
	}
	
	/**
	 *短信发送数据格式
	 *
	 *
	 * @throws Exception 
	 */
	public String sendMsgDate(SmsEntity smsEntity) {
		String statusCode = "";
		SysSmsRecordEntity sysSmsRecordEntity = dataMsgTransform(smsEntity);
		try{
			sysSmsRecordService.save(sysSmsRecordEntity);
			long startTime = System.currentTimeMillis();
			statusCode = sendCommon(sysSmsRecordEntity);
			long timeConsuming = System.currentTimeMillis() - startTime;
			sysSmsRecordEntity.setTimeConsuming(timeConsuming);
			if(sysSmsRecordEntity.getStatus() ==1) {
				statusCode = Constant.STATUS_SUCCESS;
			}
		}catch(Exception e){
			sysSmsRecordEntity.setReceiveState(e.getMessage());
			//这个字段暂时没使用，暂时用着保存错误信息
			//sysSmsRecordEntity.setSmsState(e.getMessage());
			sysSmsRecordEntity.setStatus(Constant.STATUS_NO);
			LOGGER.error("发送短信出错", e);
			return Constant.STATUS_SYSTEM_ERROR;
		}
		sysSmsRecordService.update(sysSmsRecordEntity);
		return statusCode;
	}
	
	/**
	 * 发送消息
	 * @param sysSmsRecordEntity
	 * @return
	 * @throws Exception
	 */
	private String sendCommon(SysSmsRecordEntity sysSmsRecordEntity) throws Exception {
		SmsResponse smsResponse = new SmsResponse();
		smsResponse = commonSendMessge.sendMsg(sysSmsRecordEntity);
		sysSmsRecordEntity.setRemark(smsResponse.getMessage());
		sysSmsRecordEntity.setSmsState(smsResponse.getSmsState());
		sysSmsRecordEntity.setStatus(smsResponse.getStatus());
		sysSmsRecordEntity.setMsgid(smsResponse.getMsgId());
		return smsResponse.getStatusCode();
	}
	
	/**
	 * 请求参数数据转换
	 * @param shortMessageEntity
	 * @return
	 */
	private SysSmsRecordEntity dataMsgTransform(SmsEntity smsEntity){
		LOGGER.debug("smsEntity: " + smsEntity.toString());
		SysSmsRecordEntity sysSmsRecordEntity = new SysSmsRecordEntity();
		sysSmsRecordEntity.setMobile(smsEntity.getMobile());
		
		String number = smsEntity.getTemplateNo();
		String channel = smsEntity.getChannel();
		sysSmsRecordEntity.setNumber(number);
		sysSmsRecordEntity.setSys_number(channel);
		String params = smsEntity.getParams().toJSONString();
//		JSONObject jsonObject = JSONObject.fromObject(params);
		if(StringUtils.isNotBlank(number)){
			SysSmsContentEntity sysSmsContentEntity = sysSmsContentService.findByNumber(number);//根据NUMBER找到对应的短信内容
			Map<String, Object> mapParams = JSON.parseObject(params, Map.class);
			String content = TemplateUtils.buildContent(mapParams, sysSmsContentEntity.getContent(), number);	//得到短信内容[需要进行参数替换]
			sysSmsRecordEntity.setContent(content);
			sysSmsRecordEntity.setC_id(sysSmsContentEntity.getId());
			sysSmsRecordEntity.setUniversal(sysSmsContentEntity.getUniversal());
		}
		if(StringUtils.isNotBlank(channel)){
			String sendPriority = sysConfigService.getValue(SEND_PRIORITY, Constant.SEND);
			sysSmsRecordEntity.setType(sendPriority);
			if(StringUtils.isBlank(channel)){
				sysSmsRecordEntity.setChannel_code("0");
			}else{
				sysSmsRecordEntity.setChannel_code(channel);
			}
		}else {
			sysSmsRecordEntity.setChannel_code("0");
		}
		sysSmsRecordEntity.setSend_time(Time.getCurrentTime());
		return sysSmsRecordEntity;
	}
	

}
