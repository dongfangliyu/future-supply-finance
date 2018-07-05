package cn.fintecher.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fintecher.sms.dao.SysSmsAccountInfoDao;
import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;
import cn.fintecher.sms.service.SysSmsAccountInfoService;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.utils.Constant;



@Service("sysSmsAccountInfoService")
public class SysSmsAccountInfoServiceImpl implements SysSmsAccountInfoService {
	
	private Logger logger = LoggerFactory.getLogger(SysSmsAccountInfoServiceImpl.class);
	
	@Autowired
	private SysSmsAccountInfoDao sysSmsAccountInfoDao;
	
	@Override
	public SysSmsAccountInfoEntity queryObject(Integer id){
		return sysSmsAccountInfoDao.queryObject(id);
	}
	
	
	@Override
	public List<SysSmsAccountInfoEntity> findByChannelCode(String channelCode ){
		return sysSmsAccountInfoDao.findByChannelCode(channelCode);
	}
	
	
	@Override
	public List<SysSmsAccountInfoEntity> queryList(Map<String, Object> map){
		return sysSmsAccountInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsAccountInfoDao.queryTotal(map);
	}
	
	@CacheEvict(value = "sms:AllAccountCache", key="'sms:AllAccount'")
	@Override
	public void save(SysSmsAccountInfoEntity sysSmsAccountInfo){
		sysSmsAccountInfo.setCreateTime(Time.getCurrentTime());
		sysSmsAccountInfo.setUpdateTime(Time.getCurrentTime());
		sysSmsAccountInfoDao.save(sysSmsAccountInfo);
	}
	
	@CacheEvict(value = "sms:AllAccountCache", key="'sms:AllAccount'")
	@Override
	public void update(SysSmsAccountInfoEntity sysSmsAccountInfo){
		sysSmsAccountInfo.setUpdateTime(Time.getCurrentTime());
		sysSmsAccountInfoDao.update(sysSmsAccountInfo);
	}
	
	@CacheEvict(value = "sms:AllAccountCache", key="'sms:AllAccount'")
	@Override
	public void delete(Integer id){
		sysSmsAccountInfoDao.delete(id);
	}
	
	@CacheEvict(value = "sms:AllAccountCache", allEntries=true)
	@Override
	public void deleteBatch(Integer[] ids){
		sysSmsAccountInfoDao.deleteBatch(ids);
	}
	
	@Cacheable(value = "sms:AllAccountCache", key="'sms:AllAccount'")
	public Map<String, SysSmsAccountInfoEntity> findAllAccountInfo(){
		Map<String, SysSmsAccountInfoEntity> accountInfoMap = new HashMap<String,SysSmsAccountInfoEntity>();
		List<SysSmsAccountInfoEntity> accountInfos = sysSmsAccountInfoDao.queryList(new HashMap<String, Object>());
		for(SysSmsAccountInfoEntity accountInfo : accountInfos){
			accountInfoMap.put(accountInfo.getChannelCode() + accountInfo.getFlat(), accountInfo);
		}
		return accountInfoMap;
	}
	
	/**
	 * 查找账户
	 * @param channelCode
	 * @return
	 */
	public Map<String, SysSmsAccountInfoEntity> findAccountInfo(String channelCode){
		Map<String, SysSmsAccountInfoEntity> map = new HashMap<String, SysSmsAccountInfoEntity>();
		
		Map<String, SysSmsAccountInfoEntity> allAccount = findAllAccountInfo();
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity1 = allAccount.get(channelCode + Constant.FLAT_CL); 
		SysSmsAccountInfoEntity sysSmsAccountInfoEntity2 = allAccount.get(channelCode + Constant.FLAT_QJT);
		map.put(channelCode + Constant.FLAT_CL, sysSmsAccountInfoEntity1);
		map.put(channelCode + Constant.FLAT_QJT, sysSmsAccountInfoEntity2);

		return map;		
	}
}
