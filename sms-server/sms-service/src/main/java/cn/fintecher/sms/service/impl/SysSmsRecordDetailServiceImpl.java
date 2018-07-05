package cn.fintecher.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.fintecher.sms.dao.SysSmsRecordDetailDao;
import cn.fintecher.sms.entity.SysSmsRecordDetailEntity;
import cn.fintecher.sms.entity.SysSmsRecordEntity;
import cn.fintecher.sms.service.SysSmsRecordDetailService;
import cn.fintecher.sms.util.Time;
import cn.fintecher.sms.vo.SmsResponse;



@Service("sysSmsRecordDetailService")
public class SysSmsRecordDetailServiceImpl implements SysSmsRecordDetailService {
	@Autowired
	private SysSmsRecordDetailDao sysSmsRecordDetailDao;
	
	@Override
	public SysSmsRecordDetailEntity queryObject(Long id){
		return sysSmsRecordDetailDao.queryObject(id);
	}
	
	@Override
	public List<SysSmsRecordDetailEntity> queryList(Map<String, Object> map){
		return sysSmsRecordDetailDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsRecordDetailDao.queryTotal(map);
	}
	
	@Override
	public void save(SysSmsRecordDetailEntity sysSmsRecordDetail){
		sysSmsRecordDetailDao.save(sysSmsRecordDetail);
	}
	
	@Override
	public void update(SysSmsRecordDetailEntity sysSmsRecordDetail){
		sysSmsRecordDetailDao.update(sysSmsRecordDetail);
	}
	
	@Override
	public void delete(Long id){
		sysSmsRecordDetailDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysSmsRecordDetailDao.deleteBatch(ids);
	}
	
	
	/**
	 * 保存详情
	 */
	public void saveSmsRecordDetail(SysSmsRecordEntity sysSmsRecordEntity, SmsResponse smsResponse){
		
		SysSmsRecordDetailEntity sysSmsRecordDetailEntity = new SysSmsRecordDetailEntity();
		sysSmsRecordDetailEntity.setChannelCode(sysSmsRecordEntity.getChannel_code());
		sysSmsRecordDetailEntity.setCId(sysSmsRecordEntity.getC_id());
		sysSmsRecordDetailEntity.setContent(sysSmsRecordEntity.getContent());
		sysSmsRecordDetailEntity.setMobile(sysSmsRecordEntity.getMobile());
		sysSmsRecordDetailEntity.setMsgId(sysSmsRecordEntity.getMsgid());
		sysSmsRecordDetailEntity.setNumber(sysSmsRecordEntity.getNumber());
		sysSmsRecordDetailEntity.setRemark(sysSmsRecordEntity.getRemark());
		sysSmsRecordDetailEntity.setRetryNumber(sysSmsRecordEntity.getRetryNumber());
		sysSmsRecordDetailEntity.setSendIp(sysSmsRecordEntity.getSend_ip());
		sysSmsRecordDetailEntity.setSendTime(Time.getCurrentTime());
		sysSmsRecordDetailEntity.setServiceNo(sysSmsRecordEntity.getService_no());
		sysSmsRecordDetailEntity.setSysNumber(sysSmsRecordEntity.getSys_number());
		sysSmsRecordDetailEntity.setType(sysSmsRecordEntity.getType());
		sysSmsRecordDetailEntity.setStatus(sysSmsRecordEntity.getStatus());
		sysSmsRecordDetailEntity.setUniversal(sysSmsRecordEntity.getUniversal());
		sysSmsRecordDetailEntity.setStatusCode(smsResponse.getStatusCode());
		sysSmsRecordDetailEntity.setMeseage(smsResponse.getMessage());
		sysSmsRecordDetailEntity.setSmsState(smsResponse.getSmsState());
		sysSmsRecordDetailEntity.setRecordId(sysSmsRecordEntity.getId());
		sysSmsRecordDetailEntity.setTimeConsuming(sysSmsRecordEntity.getTimeConsuming());
		sysSmsRecordDetailEntity.setReceiveState(sysSmsRecordEntity.getReceiveState());
		sysSmsRecordDetailDao.save(sysSmsRecordDetailEntity);
		
	}
	
}
