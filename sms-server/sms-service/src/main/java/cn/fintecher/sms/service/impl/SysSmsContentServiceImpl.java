package cn.fintecher.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.fintecher.sms.dao.SysSmsContentDao;
import cn.fintecher.sms.entity.SysSmsContentEntity;
import cn.fintecher.sms.service.SysSmsContentService;



@Service("sysSmsContentService")
public class SysSmsContentServiceImpl implements SysSmsContentService {
	@Autowired
	private SysSmsContentDao sysSmsContentDao;
	
	@Cacheable(value = "sms:templatecache", key="'sms:'+#id")
	@Override
	public SysSmsContentEntity queryObject(Long id){
		return sysSmsContentDao.queryObject(id);
	}
	
	@Override
	public List<SysSmsContentEntity> queryList(Map<String, Object> map){
		return sysSmsContentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsContentDao.queryTotal(map);
	}
	
	@Override
	public void save(SysSmsContentEntity sysSmsContent){
		sysSmsContentDao.save(sysSmsContent);
	}
	
	@CacheEvict(value = "sms:templatecache",allEntries=true)
	@Override
	public void update(SysSmsContentEntity sysSmsContent){
		sysSmsContentDao.update(sysSmsContent);
	}
	
	@CacheEvict(value = "sms:templatecache",allEntries=true)
	@Override
	public void delete(Long id){
		sysSmsContentDao.delete(id);
	}
	
	@CacheEvict(value = "sms:templatecache",allEntries=true)
	@Override
	public void deleteBatch(Long[] ids){
		sysSmsContentDao.deleteBatch(ids);
	}

	@Cacheable(value = "sms:templatecache", key="'sms:'+#number")
	@Override
	public SysSmsContentEntity findByNumber(String number) {
		return sysSmsContentDao.findByNumber(number);
	}
	
	
}
