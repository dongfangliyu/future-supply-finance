package cn.fintecher.sms.dao;

import java.util.List;
import java.util.Map;

import cn.fintecher.sms.entity.SysSmsTaskEntity;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-31 14:31:05
 */
public interface SysSmsTaskDao extends BaseDao<SysSmsTaskEntity> {
	
	List<SysSmsTaskEntity> getMobiles(Map<String, Object> map);
}
