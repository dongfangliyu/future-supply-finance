package cn.fintecher.sms.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fintecher.sms.dao.SysSmsRecordDao;
import cn.fintecher.sms.dao.SysSmsTaskDao;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.entity.SysSmsTaskEntity;
import cn.fintecher.sms.service.SysSmsTaskService;
import cn.fintecher.sms.util.MessageUtil;
import cn.fintecher.sms.util.PubVariable;
import cn.fintecher.sms.util.Time;



@Service("sysSmsTaskService")
public class SysSmsTaskServiceImpl implements SysSmsTaskService {
	
	private Logger logger = LoggerFactory.getLogger(SysSmsTaskServiceImpl.class);
	@Autowired
	private SysSmsTaskDao sysSmsTaskDao;
	
	@Autowired
	private SysSmsRecordDao sysSmsRecordDao;
	
	@Override
	public SysSmsTaskEntity queryObject(Long id){
		return sysSmsTaskDao.queryObject(id);
	}
	
	@Override
	public List<SysSmsTaskEntity> queryList(Map<String, Object> map){
		return sysSmsTaskDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsTaskDao.queryTotal(map);
	}
	
	@Override
	public void save(SysSmsTaskEntity sysSmsTask){
		sysSmsTaskDao.save(sysSmsTask);
	}
	
	@Override
	public void update(SysSmsTaskEntity sysSmsTask){
		sysSmsTaskDao.update(sysSmsTask);
	}
	
	@Override
	public void delete(Long id){
		sysSmsTaskDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysSmsTaskDao.deleteBatch(ids);
	}
	
	/**
	 * 
	 * 函数功能说明 			处理定时发送任务（几点发送）
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public void overdueTask() throws Exception {
		logger.warn(Time.getCurrentTime() + "，开始执行发送过期短信。");
		Map<String, Object> map = new HashMap<String, Object>();
		//排序
		map.put("priority", "asc");
		map.put("sendTime", "desc");
		
		map.put("beforeTime", Time.getCurrentTime());
		map.put("endTime", Time.getCurrentTime(Time.STYLE_2) + " 00:00:00");
		map.put("status", SysSmsTaskEntity.SEND_NO);
		map.put("firstResult", 0);
		map.put("maxResult", 50);
		
		//得到要发送的短信
		List<SysSmsTaskEntity> smsTasks = sysSmsTaskDao.queryList(map);
		String mobile = "";
		String content = "";
//		List<String> numbers = null;
		for (SysSmsTaskEntity smsTask : smsTasks) {
			//重新实例化
//			numbers = new ArrayList<String>();
//			numbers.addAll(serviceNumbers);
			
			mobile = smsTask.getMobile();
			content = smsTask.getContent();
			
			//执行判断  是否符合要求，符合就发送 否则继续等待，更改时间
			if(isThrough(mobile,content,smsTask.getCId())){
				smsTask.setStatus(SysSmsTaskEntity.SEND_YES);
				sysSmsTaskDao.update(smsTask);	
			}else{
				//发送时间延迟20分钟
				smsTask.setSendTime(Time.getAfterTime(20));
				sysSmsTaskDao.update(smsTask);
			}
		}
		
		logger.warn(Time.getCurrentTime() + "，结束执行发送过期短信。");
	}
	
	/**
	 * 
	 * 函数功能说明 				是否符合发送条件
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	private boolean isThrough(String mobile,String content,long cid)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("beginTime", Time.getBeforMinData(PubVariable.LIMIT_MINTUE));
		map.put("endTime", Time.getCurrentTime());
		map.put("status", SysSmsRecordEntity.STATUS_YES);
		List<SysSmsRecordEntity> resultList = sysSmsRecordDao.queryList(map);
		
		String sendResult ;
		if(resultList == null || resultList.size() < 8){
			sendResult = MessageUtil.sendMessage(mobile, content);
			//添加发送记录
			if("OK".equals(sendResult)){
				addMsgRecord(content,mobile,"","短信系统","",SysSmsRecordEntity.STATUS_YES,cid);
				logger.warn(Time.getCurrentTime() + "，定时任务短信发送成功，号码："+mobile + ",内容:" + content);
				return true;
			}else{
				addMsgRecord(content,mobile,"","短信系统","",SysSmsRecordEntity.STATUS_NO,cid);
				logger.warn(Time.getCurrentTime() + "，定时任务短信发送失败，号码："+mobile + ",内容:" + content);
				return false;						//短信通道发送失败
			}
		}
		return false;
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
	private void addMsgRecord(String content,String mobile,String serviceNo,String sysNumber,String remark,int status,long c_id)throws Exception{
		SysSmsRecordEntity record = new SysSmsRecordEntity();
		record.setContent(content);
		record.setMobile(mobile);
		record.setService_no(serviceNo);
		record.setSys_number(sysNumber);
		record.setRemark("");
		record.setSend_time(Time.getCurrentTime());
		record.setStatus(status);
		record.setC_id(c_id);
		sysSmsRecordDao.save(record);
	}
	
	/**
	 * 
	 * 函数功能说明 			得到贝格上面发送节日祝福的手机号码
	 * panye  2014-11-29
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public List<SysSmsTaskEntity> getMobiles(Map<String, Object> map) throws Exception {
		
		return sysSmsTaskDao.getMobiles(map);
	}
}
