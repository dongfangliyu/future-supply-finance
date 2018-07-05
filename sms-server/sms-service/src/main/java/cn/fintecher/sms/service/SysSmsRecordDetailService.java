package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.SysSmsRecordDetailEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.vo.SmsResponse;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-06-05 16:10:34
 */
public interface SysSmsRecordDetailService {
	
	SysSmsRecordDetailEntity queryObject(Long id);
	
	List<SysSmsRecordDetailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsRecordDetailEntity sysSmsRecordDetail);
	
	void update(SysSmsRecordDetailEntity sysSmsRecordDetail);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public void saveSmsRecordDetail(SysSmsRecordEntity sysSmsRecordEntity, SmsResponse smsResponse);
}
