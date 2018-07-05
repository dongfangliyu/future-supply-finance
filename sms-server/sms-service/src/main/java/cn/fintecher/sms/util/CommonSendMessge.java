package cn.fintecher.sms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.fintecher.sms.dianji.Rets;
import cn.fintecher.sms.dianji.SmsClientSend;
import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.service.SysSmsAccountInfoService;
import cn.fintecher.sms.service.SysSmsRecordDetailService;
import cn.fintecher.sms.utils.Constant;
import cn.fintecher.sms.vo.SmsResponse;

@Component
public class CommonSendMessge {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private SysSmsAccountInfoService sysSmsAccountInfoService;
	
	
	
	
	
	/**
	 * 发送短信接口
	 * @param sysSmsAccountInfoEntity
	 * @param sysSmsRecordEntity
	 * @throws Exception
	 */
	public SmsResponse sendMsg(SysSmsRecordEntity sysSmsRecordEntity) throws Exception {
		SmsResponse smsResponse = new SmsResponse();
		List<SysSmsAccountInfoEntity> list= sysSmsAccountInfoService.findByChannelCode(sysSmsRecordEntity.getSys_number());
		for (SysSmsAccountInfoEntity sysSmsAccountInfoEntity : list) {
			String response =null;
			if(Constant.DJ.equals(sysSmsAccountInfoEntity.getFlat())) {
				//点集
				 response = SmsClientSend.sendSms(sysSmsAccountInfoEntity.getUri(), sysSmsAccountInfoEntity.getAccount(), sysSmsAccountInfoEntity.getPassword(), sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getContent());
				 SmsResponse re= result(response);
				 re.setMessage(response);
				 return re;
				
			}else {
				 response = SmsClientSend.sendSms(sysSmsAccountInfoEntity.getUri(), sysSmsAccountInfoEntity.getAccount(), sysSmsAccountInfoEntity.getPassword(), sysSmsRecordEntity.getMobile(), sysSmsRecordEntity.getContent());
				 SmsResponse re= result(response);
				 return re;
			}
		}
		return smsResponse;
	}
	
	
	/**
	 * 响应结果
	 * @param result
	 * @return
	 */
	private SmsResponse result(String resultResponse){
		logger.info("result:{}",resultResponse);
		SmsResponse smsResponse = new SmsResponse();
//		Object object = JSONObject.parse(resultResponse);
		JSONObject jsonObject= JSONObject.parseObject(resultResponse);
		if (jsonObject != null) {
				if (jsonObject.containsKey("Rets")) {
					JSONArray results = jsonObject.getJSONArray("Rets");// rDetailVOs字符串数组
					for (int i = 0; i < results.size(); i++) {
						JSONObject result = results.getJSONObject(i);
						if (result.containsKey("Rspcode")) {
							Integer code = result.getInteger("Rspcode");
							if(result.containsKey("Msg_Id")) {
								smsResponse.setMsgId(result.getString("Msg_Id"));;
							}
							if(code ==0 ) {
								smsResponse.setStatus(Constant.STATUS_YES);
								smsResponse.setSmsState(code.toString());
								smsResponse.setSuccess(true);
								smsResponse.setStatusCode(Constant.STATUS_SUCCESS);
								return smsResponse;
							}else if(code ==12) {
								smsResponse.setStatus(Constant.STATUS_NO);
								smsResponse.setSmsState(code.toString());
								smsResponse.setStatusCode(Constant.STATUS_FREQUENCY_ERROR);
								return smsResponse;
							}else if(code ==99) {
								smsResponse.setStatus(Constant.STATUS_NO);
								smsResponse.setSmsState(code.toString());
								smsResponse.setStatusCode(Constant.STATUS_SYSTEM_ERROR);
								return smsResponse;
							}else {
								smsResponse.setStatus(Constant.STATUS_NO);
								smsResponse.setSmsState(code.toString());
								smsResponse.setStatusCode(Constant.STATUS_SYSTEM_ERROR);
								return smsResponse;
							}
						}
					}
				}
			}
			smsResponse.setStatus(Constant.STATUS_NO);
			smsResponse.setStatusCode(Constant.STATUS_SYSTEM_ERROR);
			return smsResponse;
	}
	
	public static <T> Map<String, Object> beanToMap(T bean) {
	    Map<String, Object> beanMap = new HashMap<>(16);
	    if (bean != null) {
	        BeanMap tempMap = BeanMap.create(bean);
	        for (Object key : tempMap.keySet()) {
	            String putKey = String.valueOf(key);
	            Object putValue = tempMap.get(key);
	            beanMap.put(putKey, putValue);
	        }
	    }
	    return beanMap;
	}
	
	public static <T> T mapToBean(Map<String, Object> beanMap, T bean) {
	    BeanMap tempMap = BeanMap.create(bean);
	    tempMap.putAll(beanMap);
	    return bean;
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
