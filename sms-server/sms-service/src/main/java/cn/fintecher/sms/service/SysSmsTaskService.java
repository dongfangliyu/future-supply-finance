package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.SysSmsTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-31 14:31:05
 */
public interface SysSmsTaskService {
	
	SysSmsTaskEntity queryObject(Long id);
	
	List<SysSmsTaskEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsTaskEntity sysSmsTask);
	
	void update(SysSmsTaskEntity sysSmsTask);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public void overdueTask()throws Exception;
	
	public List<SysSmsTaskEntity> getMobiles(Map<String, Object> map)throws Exception;
}
