package cn.fintecher.sms.dao;

import java.util.List;

import cn.fintecher.sms.entity.SysSmsRecordEntity;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-31 14:31:05
 */
public interface SysSmsRecordDao extends BaseDao<SysSmsRecordEntity> {
	
	public SysSmsRecordEntity findByMsgId(String msgId);
	
	public List<SysSmsRecordEntity> queryListByIds(Long[] ids);
}	
