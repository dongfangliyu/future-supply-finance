package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.SysSmsRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-31 14:31:05
 */
public interface SysSmsRecordService {
	
	SysSmsRecordEntity queryObject(Long id);
	
	List<SysSmsRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsRecordEntity sysSmsRecord);
	
	void update(SysSmsRecordEntity sysSmsRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	SysSmsRecordEntity findByMsgId(String msgid);
	
	List<SysSmsRecordEntity> queryListByIds(Long[] ids);

}
