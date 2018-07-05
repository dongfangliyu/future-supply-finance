package cn.fintecher.sms.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fintecher.sms.entity.SysSmsContentEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.entity.SysSmsTaskEntity;
import cn.fintecher.sms.service.SysSmsContentService;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.service.SysSmsTaskService;
import cn.fintecher.sms.util.PubVariable;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.util.bcloud.NontPackagingUtil;
import cn.fintecher.sms.utils.Query;

/**
 * 
 * 
 * Classname  		定时处理一些任务
 * Version	  1.2
 * @author panye
 * 2015-2-5
 * Copyright notice
 */
public class SysTask {

	@Resource
	private SysSmsTaskService sysSmsTaskService; 
	
	
	@Resource
	private SysSmsRecordService sysSmsRecordService;
	
	@Resource
	private SysSmsContentService sysSmsContentService;
	
//	private List<String> messageNumbers;
	
	private Logger logger = LoggerFactory.getLogger(SysTask.class);
	
	/**
	 * 				
	 * 函数功能说明 		处理一些未及时发送的短信	（每5分钟执行一次）		
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public void overdueTask(){
		try {
			sysSmsTaskService.overdueTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 函数功能说明 				节假日短信定时发	(元旦、春节、中秋节、端午节、国庆节) 每天12点检查一下然后再确定执行
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public void holidayTask(){
		//只需要判断该服务发送的次数是否超过 每天限制即可，其它不用理会
		
		//元旦
//		if(Time.getCurrentMonth() == 1 && Time.getCurrentDay() == 1){
//			sendHolidayMsg("021");
//			return;
//		}
//		//国庆
//		if(Time.getCurrentMonth() == 10 && Time.getCurrentDay() == 1){
//			sendHolidayMsg("024");
//			return;
//		}
//		//得到 农历日期
//		LunarCalendar lunarCalendar = new LunarCalendar();
//		int month = lunarCalendar.getMonth();
//		int day = lunarCalendar.getDay();
//		//春节
//		if(month == 1 && day == 1){
//			sendHolidayMsg("022");			
//			return;
//		}
//		//端午节
//		if(month == 5 && day == 5){
//			sendHolidayMsg("023");
//			return;
//		}
//		//中秋节
//		if(month == 8 && day == 15){
//			sendHolidayMsg("039");
//			return;
//		}
		
	}
	
	
//===================================================节假日短信===============================================================
	
	
	
	/**
	 * 
	 * 函数功能说明 				传入短信内容编号 执行发送短信
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private void sendHolidayMsg(String number){
		int pageNo = 1;
		SysSmsContentEntity sysSmsContentEntity = sysSmsContentService.findByNumber(number);
		try {
			for(int i = 0; i< 100000; i ++){
				System.out.println("开始发送短信------------------");
				List<SysSmsTaskEntity> SysSmsTasks = getMobiles(pageNo);
				if(SysSmsTasks == null || SysSmsTasks.size() == 0 || SysSmsTasks.get(0) == null){
					System.out.println("结束发送短信------------------");
					break;
				}
				
				cycleSend(SysSmsTasks, sysSmsContentEntity);
				pageNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * 函数功能说明 				循环发送短信
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private void cycleSend(List<SysSmsTaskEntity> sysSmsTaskEntitys,SysSmsContentEntity sysSmsContentEntity)throws Exception{
				
		for (SysSmsTaskEntity m : sysSmsTaskEntitys) {
			//找出短时间内某一手机号码发送的短信记录
			//查询列表数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", m.getMobile());
			params.put("beginTime", Time.getBeforMinData(PubVariable.LIMIT_MINTUE));
			params.put("endTime", Time.getCurrentTime());
			params.put("status", SysSmsRecordEntity.STATUS_YES);
			Query query = new Query(params);
			List<SysSmsRecordEntity> resultList = sysSmsRecordService.queryList(query);
			try {
				if(resultList == null || resultList.size() <= 8){
					sendMsg(m.getMobile(),sysSmsContentEntity.getContent(), "", sysSmsContentEntity.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn(e.getMessage(),e);
				logger.warn(Time.getCurrentTime()+"，发送节日短信失败，号码:"+m);
			}
		}
	}
	
	/**
	 * 
	 * 函数功能说明 			获得手机号码   采取分别抓取	每次100个	
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private List<SysSmsTaskEntity> getMobiles(int pageNo)throws Exception{
		/*SysSmsTaskSearchForm taskForm = new SysSmsTaskSearchForm();
		taskForm.setPage(pageNo);
		taskForm.setRows(100);
		//实例化分页数据
		PageView<SysSmsTaskEntity> page = new PageView<SysSmsTaskEntity>(taskForm.getRows(), taskForm.getPage());
		
		taskForm.setFirstResult(page.getFirstResult());
		taskForm.setMaxResult(page.getMaxresult());*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("rows", 100);
		map.put("firstResult", 0);
		map.put("maxResult", 10);
 		//得到需要发送的号码
		List<SysSmsTaskEntity> SysSmsTasks = sysSmsTaskService.getMobiles(map);
		return SysSmsTasks;
	}
	
	
//===========================================================================================================================
	
	/**
	 * 
	 * 函数功能说明 			发送短信
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private String sendMsg(String mobile,String content,String serviceNo,long cid)throws Exception{
		String sendResult = NontPackagingUtil.sendMessage(mobile, content);
		if("OK".equals(sendResult)){
			addMsgRecord(content,mobile,serviceNo,"短信系统","",SysSmsRecordEntity.STATUS_NO,cid);
			logger.warn(Time.getCurrentTime() + "，发送短信成功，号码："+mobile + ",内容:" + content);
			return "200";
		}else{
			addMsgRecord(content,mobile,serviceNo,"短信系统","",SysSmsRecordEntity.STATUS_NO,cid);
			logger.warn(Time.getCurrentTime() + "，发送短信失败，号码："+mobile + ",内容:" + content);
			return "2006";
		}
	}
	
	/**
	 * 
	 * 函数功能说明 			新添短信记录		
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private void addMsgRecord(String content,String mobile,String serviceNo,String sysNumber,String remark,int status,long cid)throws Exception{
		SysSmsRecordEntity record = new SysSmsRecordEntity();
		record.setContent(content);
		record.setMobile(mobile);
		record.setService_no(serviceNo);
		record.setSys_number(sysNumber);
		record.setRemark("");
		record.setSend_time(Time.getCurrentTime());
		record.setStatus(status);
		record.setC_id(cid);
		record.setSend_ip("auto_holiday");
		sysSmsRecordService.save(record);
	}
}
