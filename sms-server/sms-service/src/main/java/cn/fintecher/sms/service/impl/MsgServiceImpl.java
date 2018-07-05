package cn.fintecher.sms.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.fintecher.sms.entity.ShortMessageEntity;
import cn.fintecher.sms.entity.SmsEntity;
import cn.fintecher.sms.entity.SysSmsContentEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.MsgService;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.service.SysSmsContentService;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.util.ChkUtil;
import cn.fintecher.sms.util.CommonSendMessge;
import cn.fintecher.sms.util.HttpSendMessge;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.utils.template.TemplateUtils;
import cn.fintecher.sms.vo.SmsResponse;
import net.sf.json.JSONObject;

@Service("msgService")
public class MsgServiceImpl implements MsgService {
	
	private final static String SEND_PRIORITY = "sendPriority";
	private final static String SYS_RETRY_NUMBER = "sysRetryNumber";
	private final static String IS_TEST = "isTest";
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
				return Constant.STATUS_SUCCESS;
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
	
	
	/**
	 * 
	 * 函数功能说明 		发送短信			处理发送短信的一些业务逻辑	规则 ：1分钟内10条
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 *mobile：手机号码，
	 *number:短信编号，
	 *sysNumber：系统编号，
	 *params：参数  	用于扩展的{date:日期，verification_code : 验证码，money : 金额 ，project_number ： 项目编号，
	 *bank_no － 银行卡号，文本内容 － text，task － 是否加入定时任务(值1表示 如果发送失败会加入到任务中，一段时间后再次发送)，
	 *type ：短信类型(0普通短信 1 语音短信 ，默认普通短信)
	 *ip:发送的IP地址,channel:短信通道（0 贝尔在线 1 中资联） 默认 0}
	 * @return   
	 * @throws 
	 * @throws Exception 
	 */
	public String sendMsg(ShortMessageEntity shortMessageEntity) {
		String statusCode = "";
		SysSmsRecordEntity sysSmsRecordEntity = dataTransform(shortMessageEntity);
		// 判断是否要发送短信，配置参数：isTest
		if (Boolean.parseBoolean(sysConfigService.getValue(IS_TEST, "false"))) {
			sysSmsRecordEntity.setStatus(Constant.STATUS_YES);
			sysSmsRecordEntity.setReceiveState("测试环境不发送短信，默认成功");
			sysSmsRecordService.save(sysSmsRecordEntity);//发送记录
			LOGGER.info("测试环境不发送短信，sysSmsRecordEntity={}", sysSmsRecordEntity);
			return Constant.STATUS_SUCCESS;
		}
		try{
			sysSmsRecordService.save(sysSmsRecordEntity);
			long startTime = System.currentTimeMillis();
//			if(sysSmsRecordEntity.getType() ! =) {
//				
//			}
			statusCode = send(sysSmsRecordEntity);
			
			long timeConsuming = System.currentTimeMillis() - startTime;
			sysSmsRecordEntity.setTimeConsuming(timeConsuming);
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
	 * 请求参数数据转换
	 * @param shortMessageEntity
	 * @return
	 */
	private SysSmsRecordEntity dataTransform(ShortMessageEntity shortMessageEntity){
		LOGGER.debug("shortMessageEntity: " + shortMessageEntity.toString());
		SysSmsRecordEntity sysSmsRecordEntity = new SysSmsRecordEntity();
		sysSmsRecordEntity.setMobile(shortMessageEntity.getMobile());
		sysSmsRecordEntity.setSys_number(shortMessageEntity.getSysNumber());
		sysSmsRecordEntity.setNumber(shortMessageEntity.getNumber());
		
		String params = shortMessageEntity.getParams().toJSONString();
		JSONObject jsonObject = JSONObject.fromObject(params);
		String ip = (String)jsonObject.get("ip");
		if(ChkUtil.isEmpty(ip)){
			ip = shortMessageEntity.getSysNumber();
		}
		String type = (String)jsonObject.get("type");
		String channel = (String)jsonObject.get("channel");
		String remark = (String)jsonObject.get("remark");
		String verificationCode = (String)jsonObject.get("verification_code");
		String number = sysSmsRecordEntity.getNumber();
		if(!"1".equals(type) && StringUtils.isNotBlank(number)){
			SysSmsContentEntity sysSmsContentEntity = sysSmsContentService.findByNumber(number);//根据NUMBER找到对应的短信内容
			Map<String, Object> mapParams = JSON.parseObject(params, Map.class);
			String content = TemplateUtils.buildContent(mapParams, sysSmsContentEntity.getContent(), number);	//得到短信内容[需要进行参数替换]
			sysSmsRecordEntity.setContent(content);
			sysSmsRecordEntity.setC_id(sysSmsContentEntity.getId());
			sysSmsRecordEntity.setUniversal(sysSmsContentEntity.getUniversal());
		}else{
			sysSmsRecordEntity.setContent(verificationCode);
		}
		sysSmsRecordEntity.setSend_ip(ip);
		if(StringUtils.isNotBlank(type) && "1".equals(type)){
			sysSmsRecordEntity.setType(type);
		}else{
			String sendPriority = sysConfigService.getValue(SEND_PRIORITY, Constant.DJ);
			sysSmsRecordEntity.setType(sendPriority);
			if(StringUtils.isBlank(channel)){
				sysSmsRecordEntity.setChannel_code("0");
			}else{
				sysSmsRecordEntity.setChannel_code(channel);
			}
		}
		sysSmsRecordEntity.setRemark(remark);
		
		sysSmsRecordEntity.setSend_time(Time.getCurrentTime());
		return sysSmsRecordEntity;
	}
	
	/**
	 * 根据状态查询数据重发
	 */
	public void receiveResponseState(String msgid,String mobile,String status) {
		//找出	1分钟内某一手机号码发送的短信记录不能超过 10 条
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgId", msgid);
		List <SysSmsRecordEntity> resultList = new ArrayList<SysSmsRecordEntity>();
		resultList = sysSmsRecordDao.queryList(map);
		if(!ChkUtil.isEmpty(resultList) && resultList.size() > 0){
			SysSmsRecordEntity smsRecord = resultList.get(0);
			sendMsg(smsRecord);
		}*/
		SysSmsRecordEntity sysSmsRecordEntity = sysSmsRecordService.findByMsgId(msgid);
		if(sysSmsRecordEntity == null){
			return;
		}
		try{
			if(!ChkUtil.isEmpty(status) && "UNDELIV".equals(status)){
				String smsValue = sysConfigService.getValue(SYS_RETRY_NUMBER, "2");
				if(StringUtils.isBlank(smsValue)){
					smsValue = "2";
				}
				int retryNumber = Integer.valueOf(smsValue);
				int recordStatus = 0;
				if(sysSmsRecordEntity.getStatus() != null){
					recordStatus = sysSmsRecordEntity.getStatus();
				}
				while("0".equals(recordStatus + "") && sysSmsRecordEntity.getRetryNumber() < retryNumber){
					sysSmsRecordEntity.setRetryNumber(sysSmsRecordEntity.getRetryNumber() + 1);
					String sendPriority = sysSmsRecordEntity.getType();
					//创蓝发短信失败使用乾景通，乾景通失败使用创蓝
					if(Constant.CL.equals(sendPriority)){
						sysSmsRecordEntity.setType(Constant.QJT);
					}else if(Constant.QJT.equals(sendPriority)){
						sysSmsRecordEntity.setType(Constant.CL);
					}
					send(sysSmsRecordEntity);
				}
			}
			sysSmsRecordEntity.setReceiveState(status);
		}catch(Exception e){
			LOGGER.error("重发数据出错！", e);
			sysSmsRecordEntity.setReceiveState(e.getMessage());
		}
		sysSmsRecordService.update(sysSmsRecordEntity);
	}
	
	
	/**
	 * 根据状态查询数据重发
	 * @throws Exception 
	 */
	public void retryBatchSend(Long[] ids) throws Exception {
		if(ids == null || ids.length == 0){
			throw new Exception("未选中重发数据");
		}
		List<SysSmsRecordEntity> sysSmsRecordEntityList = sysSmsRecordService.queryListByIds(ids);
		if(sysSmsRecordEntityList == null || sysSmsRecordEntityList.isEmpty()){
			return;
		}
		for(SysSmsRecordEntity sysSmsRecordEntity : sysSmsRecordEntityList){
			String smsValue = sysConfigService.getValue(SYS_RETRY_NUMBER, "2");
			if(StringUtils.isBlank(smsValue)){
				smsValue = "2";
			}
			int retryNumber = Integer.valueOf(smsValue);
			//重试次数最大时候，改变优先级
			if(sysSmsRecordEntity.getRetryNumber() == retryNumber && !"1".equals(sysSmsRecordEntity.getType())){
				String sendPriority = sysSmsRecordEntity.getType();
				if(Constant.CL.equals(sendPriority)){
					sysSmsRecordEntity.setType(Constant.QJT);
				}else if(Constant.QJT.equals(sendPriority)){
					sysSmsRecordEntity.setType(Constant.CL);
				}
				sysSmsRecordEntity.setRetryNumber(0);
			}
			//成功了也可以重发，但不能超过最大次数
			if(sysSmsRecordEntity.getStatus() == 0 || sysSmsRecordEntity.getRetryNumber() < retryNumber){
				sysSmsRecordEntity.setReceiveState("重发");
				sysSmsRecordEntity.setRetryNumber(sysSmsRecordEntity.getRetryNumber() + 1);
				SmsResponse smsResponse = httpSendMessge.sendMsg(sysSmsRecordEntity);
				sysSmsRecordEntity.setMsgid(smsResponse.getMsgId());
			}else{
				sysSmsRecordEntity.setReceiveState("超过重发次数" + retryNumber);
			}
			sysSmsRecordService.update(sysSmsRecordEntity);
		}
	}
	
}
