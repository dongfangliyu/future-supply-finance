package cn.fintecher.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.fintecher.sms.dao.SysSmsRecordDao;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysSmsRecordService;
import cn.fintecher.sms.util.Time;



@Service("sysSmsRecordService")
public class SysSmsRecordServiceImpl implements SysSmsRecordService {
	@Autowired
	private SysSmsRecordDao sysSmsRecordDao;
	
	@Override
	public SysSmsRecordEntity queryObject(Long id){
		return sysSmsRecordDao.queryObject(id);
	}
	
	@Override
	public List<SysSmsRecordEntity> queryList(Map<String, Object> map){
		return sysSmsRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(SysSmsRecordEntity sysSmsRecord){
		sysSmsRecordDao.save(sysSmsRecord);
	}
	
	@Override
	public void update(SysSmsRecordEntity sysSmsRecord){
		sysSmsRecord.setUpdateTime(Time.getCurrentTime());
		sysSmsRecordDao.update(sysSmsRecord);
	}
	
	@Override
	public void delete(Long id){
		sysSmsRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysSmsRecordDao.deleteBatch(ids);
	}

	@Override
	public SysSmsRecordEntity findByMsgId(String msgid) {
		return sysSmsRecordDao.findByMsgId(msgid);
	}

	@Override
	public List<SysSmsRecordEntity> queryListByIds(Long[] ids) {
		return sysSmsRecordDao.queryListByIds(ids);
	}
	
}
