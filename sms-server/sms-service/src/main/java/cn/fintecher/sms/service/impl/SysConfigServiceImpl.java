package cn.fintecher.sms.service.impl;

import com.alibaba.fastjson.JSON;

import cn.fintecher.sms.dao.SysConfigDao;
import cn.fintecher.sms.entity.SysConfigEntity;
import cn.fintecher.sms.service.SysConfigService;
import cn.fintecher.sms.utils.RRException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;
	
	@Override
	public void save(SysConfigEntity config) {
		sysConfigDao.save(config);
	}

	@CacheEvict(value = "sms:configCache", key="'sms:'+#config.key")
	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	@CacheEvict(value = "sms:configCache", key="'sms:'+#key")
	@Override
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	@CacheEvict(value="sms:configCache",allEntries=true)
	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	@Cacheable(value = "sms:configCache", key="'sms:'+#key")
	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}
	
	@Cacheable(value = "sms:configCache", key="'sms:'+#key")
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return JSON.parseObject(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
