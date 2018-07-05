package cn.fintecher.sms.service;

import cn.fintecher.sms.entity.SysSmsContentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-28 00:10:02
 */
public interface SysSmsContentService {
	
	SysSmsContentEntity queryObject(Long id);
	
	List<SysSmsContentEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsContentEntity sysSmsContent);
	
	void update(SysSmsContentEntity sysSmsContent);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	SysSmsContentEntity findByNumber(String number);
}
