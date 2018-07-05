package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-06-01 15:12:13
 */
public interface SysSmsAccountInfoService {
	
	SysSmsAccountInfoEntity queryObject(Integer id);
	
	List<SysSmsAccountInfoEntity> queryList(Map<String, Object> map);
	List<SysSmsAccountInfoEntity> findByChannelCode(String channelCode);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsAccountInfoEntity sysSmsAccountInfo);
	
	void update(SysSmsAccountInfoEntity sysSmsAccountInfo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	public Map<String, SysSmsAccountInfoEntity> findAccountInfo(String channelCode);
	
	public Map<String, SysSmsAccountInfoEntity> findAllAccountInfo();
}
