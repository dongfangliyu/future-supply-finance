package cn.fintecher.sms.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.service.SysSmsAccountInfoService;
import cn.fintecher.sms.service.SysSmsRecordDetailService;
import cn.fintecher.sms.util.ucpaas.restDemo.client.JsonReqClient;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.vo.SmsResponse;
import cn.fintecher.sms.vo.SysParamVO;
import net.sf.json.JSONObject;

@Component
public class HttpSendMessge {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private SysSmsAccountInfoService sysSmsAccountInfoService;
	
	@Autowired
	private SysSmsRecordDetailService sysSmsRecordDetailService;
	
	@Value("${yuntongxun.accountid}")
	private String accountid;
	@Value("${yuntongxun.autotoken}")
	private String autotoken;
	@Value("${yuntongxun.applyid}")
	private String applyid;
	@Value("${yuntongxun.displaynum}")
	private String displaynum;
	@Value("${yuntongxun.playTimes}")
	private String playTimes;
	
	
	
	/**
	 * 发送短信接口
	 * @param sysSmsAccountInfoEntity
	 * @param sysSmsRecordEntity
	 * @throws Exception
	 */
	public SmsResponse sendMsg(SysSmsRecordEntity sysSmsRecordEntity) throws Exception {
		SmsResponse smsResponse = new SmsResponse();
		
		String sendPriority = sysSmsRecordEntity.getType();
		if("1".equals(sendPriority)){
			smsResponse = sendVoiceMsg(sysSmsRecordEntity);
		}else{
			smsResponse = retrySend(sysSmsRecordEntity);
			//失败了调用另一个接口
			if(!smsResponse.isSuccess()){
				if(Constant.CL.equals(sendPriority)){
					sysSmsRecordEntity.setType(Constant.QJT);
				}else if(Constant.QJT.equals(sendPriority)){
					sysSmsRecordEntity.setType(Constant.CL);
				}
				sysSmsRecordEntity.setRetryNumber(0);
				smsResponse = retrySend(sysSmsRecordEntity);
			}
		}
		return smsResponse;
	}
	
	/**
	 * 重试
	 * @return
	 * @throws Exception 
	 */
	private SmsResponse retrySend(SysSmsRecordEntity sysSmsRecordEntity) throws Exception{
		String sendPriority = sysSmsRecordEntity.getType();
		SysParamVO sysParamVO = sysConfigService.getConfigObject(sendPriority + "Params", SysParamVO.class);
		SmsResponse smsResponse = new SmsResponse();
		
		String channelCode = sysSmsRecordEntity.getChannel_code();
	    // 若未传入通道值；默认通道【贝尔在线-行业账户】
		if(ChkUtil.isEmpty(channelCode)){
			channelCode = "0";
		}
		Map<String, SysSmsAccountInfoEntity> accountAllMap = sysSmsAccountInfoService.findAllAccountInfo();
		Map<String, SysSmsAccountInfoEntity> accountMap = new HashMap<String, SysSmsAccountInfoEntity>();;
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity1 = accountAllMap.get(channelCode + Constant.FLAT_CL);
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity2 = accountAllMap.get(channelCode + Constant.FLAT_QJT);
		accountMap.put(channelCode + Constant.FLAT_CL, sysSmsAccountInfoEntity1);
		accountMap.put(channelCode + Constant.FLAT_QJT, sysSmsAccountInfoEntity2);
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity = null;
		switch(sendPriority){
			case Constant.CL :
				sysSmsAccountInfoEntity = accountMap.get(channelCode + Constant.FLAT_CL);
				break;
			case Constant.QJT:
				sysSmsAccountInfoEntity = accountMap.get(channelCode + Constant.FLAT_QJT);
				if("-1".equals(sysSmsRecordEntity.getUniversal())){
					smsResponse.setMessage("乾景通不支持催收类的模板");
					smsResponse.setStatus(Constant.STATUS_NO);
					smsResponse.setStatusCode(Constant.STATUS_SEND_ERROR);
					smsResponse.setSuccess(true);
					sysSmsRecordEntity.setStatus(Constant.STATUS_NO);
					sysSmsRecordEntity.setReceiveState("乾景通不支持催收类的模板");
					sysSmsRecordDetailService.saveSmsRecordDetail(sysSmsRecordEntity, smsResponse);
					return smsResponse;
				}
				break;
		}
		smsResponse = httpSendMsg(sysSmsAccountInfoEntity, sysSmsRecordEntity);
		while(!smsResponse.isSuccess() && sysSmsRecordEntity.getRetryNumber() < sysParamVO.getRetryNumber()){
			Thread.sleep(sysParamVO.getIntervalTime());
			sysSmsRecordEntity.setRetryNumber(sysSmsRecordEntity.getRetryNumber() + 1);
			smsResponse = httpSendMsg(sysSmsAccountInfoEntity, sysSmsRecordEntity);
			
		}
		return smsResponse;
	}
	
	
	/**
	 * 调用发送信息接口
	 * @param sysSmsAccountInfoEntity
	 * @param sysSmsRecordEntity
	 * @return
	 * @throws Exception
	 */
	private SmsResponse httpSendMsg(SysSmsAccountInfoEntity sysSmsAccountInfoEntity, SysSmsRecordEntity sysSmsRecordEntity) throws Exception{
		logger.debug("sysSmsAccountInfoEntity:{},sysSmsRecordEntity:{}", sysSmsAccountInfoEntity, sysSmsRecordEntity);
		if(sysSmsAccountInfoEntity == null){
			throw new Exception("发送短信账号为空");
		}
		SmsResponse smsResponse = new SmsResponse();
        // 短信频率控制
//		if(smsFilter.isExceedRate(sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getType(), sysSmsRecordEntity.getChannel_code())){
//			logger.error("短时间发送过于频繁，发送失败，sysSmsRecordEntity={}", sysSmsRecordEntity);
//			smsResponse.setMessage("短时间发送过于频繁，发送失败");
//			smsResponse.setStatus(Constant.STATUS_NO);
//			smsResponse.setStatusCode(Constant.STATUS_FREQUENCY_ERROR);
//			smsResponse.setSuccess(false);
//			return smsResponse;
//		}
		
		long startTime = System.currentTimeMillis();
		GetMethod method = null;
		try{
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			method = findMethod(sysSmsAccountInfoEntity, sysSmsRecordEntity);
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				//添加发送记录
				smsResponse = resultResponse(URLDecoder.decode(baos.toString(), "UTF-8"));
				sysSmsRecordEntity.setStatus(smsResponse.getStatus());
				sysSmsRecordEntity.setMsgid(smsResponse.getMsgId());
				
//				if(smsResponse.isSuccess()){
//					// 添加成功发送次数
//					smsFilter.increaseSuccessCount(sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getType(), sysSmsRecordEntity.getChannel_code());
//				}
			} else {
				smsResponse.setStatusCode(String.valueOf(method.getStatusCode()));
				smsResponse.setStatus(Constant.STATUS_NO);
				smsResponse.setMessage(method.getStatusText());
			}
		} catch(Exception e){
			logger.error("httpSendMsg方法出错。", e);
			smsResponse.setSuccess(false);
			smsResponse.setStatusCode(Constant.STATUS_SYSTEM_ERROR);
			smsResponse.setStatus(Constant.STATUS_NO);
			smsResponse.setMessage(e.getMessage());
		}finally {
			if(method != null){
				method.releaseConnection();
			}
		}
		long timeConsuming = System.currentTimeMillis() - startTime;
		sysSmsRecordEntity.setTimeConsuming(timeConsuming);
		sysSmsRecordDetailService.saveSmsRecordDetail(sysSmsRecordEntity, smsResponse);
		return smsResponse;
	}
	
	/**
	 * 获得创蓝或钱景通method
	 * @param sysSmsAccountInfoEntity
	 * @param sysSmsRecordEntity
	 * @param sendPriority
	 * @return
	 * @throws Exception
	 */
	private GetMethod findMethod(SysSmsAccountInfoEntity sysSmsAccountInfoEntity, SysSmsRecordEntity sysSmsRecordEntity) throws Exception{
		String uri = sysSmsAccountInfoEntity.getUri();
		String account = sysSmsAccountInfoEntity.getAccount();
		String pswd = sysSmsAccountInfoEntity.getPassword();
		String neededReportStatus = sysSmsAccountInfoEntity.getNeededReportStatus();
		String extendedCode = sysSmsAccountInfoEntity.getExtendedCode();
		String mobile = sysSmsRecordEntity.getMobile();
		String content = sysSmsRecordEntity.getContent();
		String sendPriority = sysSmsRecordEntity.getType();
		String channelCode = sysSmsRecordEntity.getChannel_code();
		String product = "";
		if("0".equals(channelCode)){
			product = Constant.QJT_PRODUCT_TEXT;
		}
		GetMethod method = new GetMethod();
		URI base = new URI(uri, false);
		if(Constant.CL.equals(sendPriority)){
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("un", account),
					new NameValuePair("pw", pswd), 
					new NameValuePair("phone", mobile),
					new NameValuePair("rd", neededReportStatus), 
					new NameValuePair("msg", content),
					new NameValuePair("ex", extendedCode), 
				});
		}else{
			boolean needstatus = true;
			if("0".equals(neededReportStatus)){
				needstatus = false;
			}
			sysSmsAccountInfoEntity.setProductCode(Constant.QJT_PRODUCT_TEXT);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
		      method.setQueryString(new NameValuePair[] { 
		        new NameValuePair("account", account), 
		        new NameValuePair("pswd", pswd), 
		        new NameValuePair("mobile", mobile), 
		        new NameValuePair("needstatus", String.valueOf(needstatus)), 
		        new NameValuePair("msg", content), 
		        //短信固定参数
		        new NameValuePair("product", product), 
		        new NameValuePair("extno", extendedCode) 
		      });
		}
		return method;
	}
	
	
	/**
	 * 
	 * 函数功能说明 			发送语音短信	北京 云通讯
	 * panye  2014-11-29
	 * 修改内容 		
	 * @param 			mobile 手机  code 验证码
	 * @return   
	 * @throws 
	 */
	private SmsResponse sendVoiceMsg(SysSmsRecordEntity sysSmsRecordEntity) {
		logger.debug("sysSmsRecordEntity : " + sysSmsRecordEntity.toString());
		long startTime = System.currentTimeMillis();
		SmsResponse smsResponse = new SmsResponse();
		// 短信频率控制
//		if(smsFilter.isExceedRate(sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getType(), sysSmsRecordEntity.getChannel_code())){
//			logger.error("语音短时间发送过于频繁，发送失败，sysSmsRecordEntity={}", sysSmsRecordEntity);
//			smsResponse.setMessage("语音短时间发送过于频繁，发送失败");
//			smsResponse.setStatus(Constant.STATUS_NO);
//			smsResponse.setStatusCode(Constant.STATUS_FREQUENCY_ERROR);
//			smsResponse.setSuccess(false);
//			return smsResponse;
//		}
		JsonReqClient client = new JsonReqClient();
		String str  = client.voiceCode(accountid, this.autotoken, this.applyid, 
						sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getContent(), this.displaynum, this.playTimes);
		logger.debug("sendVoiceMsg result: " + str);
		if(StringUtils.isBlank(str)){
			sysSmsRecordEntity.setStatus(Constant.STATUS_NO);
			smsResponse.setStatusCode(Constant.STATUS_VOICE_SEND_ERROR);
			smsResponse.setMessage("请求服务出错");
		}else{
			JSONObject result = JSONObject.fromObject(str);
			JSONObject jsonObject = (JSONObject) result.get("resp");
			String respCode = (String)jsonObject.get("respCode");
			if("000000".equals(respCode)){
				// 添加成功发送次数
//				smsFilter.increaseSuccessCount(sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getType(), sysSmsRecordEntity.getChannel_code());
				sysSmsRecordEntity.setStatus(Constant.STATUS_YES);
				smsResponse.setSuccess(true);
				smsResponse.setStatusCode(Constant.STATUS_SUCCESS);
			}else{
				sysSmsRecordEntity.setStatus(Constant.STATUS_NO);
				smsResponse.setStatusCode(Constant.STATUS_VOICE_SEND_ERROR);
			}
			smsResponse.setSmsState(respCode);
			if(jsonObject.get("voiceVerify") != null){
				JSONObject voiceVerify = (JSONObject)jsonObject.get("voiceVerify");
				if(voiceVerify.get("callId") != null){
					String callId = (String)voiceVerify.get("callId");
					sysSmsRecordEntity.setMsgid(callId);
				}
			}
		}
		sysSmsRecordEntity.setService_no("0000");
		sysSmsRecordEntity.setRemark("语音短信");
		sysSmsRecordEntity.setC_id(41L);
		long timeConsuming = System.currentTimeMillis() - startTime;
		sysSmsRecordEntity.setTimeConsuming(timeConsuming);
		sysSmsRecordDetailService.saveSmsRecordDetail(sysSmsRecordEntity, smsResponse);
		return smsResponse;
	}
	
	
	
	
	/**
	 * 响应结果
	 * @param result
	 * @return
	 */
	private SmsResponse resultResponse(String result){
		logger.info("result:{}",result);
		SmsResponse smsResponse = new SmsResponse();
		Scanner scn = null;
		if (ChkUtil.isEmpty(result)) {
			return smsResponse;
		}
		scn = new Scanner(result);
		String firstLine = "";
		if(scn.hasNextLine()){
			firstLine = scn.nextLine();//20110725160412,0;响应时间,状态码
		}
		String strs[] = firstLine.split(",");
		String responseCode = strs[1].toString().substring(0, 1);
		if ("0".equals(responseCode)) {
			smsResponse.setStatus(Constant.STATUS_YES);
			smsResponse.setSuccess(true);
			smsResponse.setStatusCode(Constant.STATUS_SUCCESS);
		}else{
			smsResponse.setStatus(Constant.STATUS_NO);
			smsResponse.setStatusCode(Constant.STATUS_SYSTEM_ERROR);
		}
		smsResponse.setSmsState(strs[1]);
		if(scn.hasNextLine()){
			smsResponse.setMsgId(scn.nextLine());
		}
		if(!ChkUtil.isEmpty(scn)){
			scn.close();
		}
		return smsResponse;
	}
	
}
