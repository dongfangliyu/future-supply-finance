package cn.fintecher.sms.dao;

import java.util.List;
import java.util.Map;

import cn.fintecher.sms.entity.SysSmsContentEntity;

/**
 * 
 * 
 * @author 
 * @email 
 * @date 2017-05-28 00:10:02
 */
public interface SmsRecordDao extends BaseDao<SysSmsContentEntity> {
	
	List<SysSmsContentEntity> findAllSpe(Map<String, Object> map);
	
}
