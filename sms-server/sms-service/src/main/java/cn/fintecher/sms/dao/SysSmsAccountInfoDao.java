package cn.fintecher.sms.dao;

import java.util.List;

import cn.fintecher.sms.entity.SysSmsAccountInfoEntity;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-06-01 15:12:13
 */
public interface SysSmsAccountInfoDao extends BaseDao<SysSmsAccountInfoEntity> {
	
	List<SysSmsAccountInfoEntity> findByChannelCode(String channelCode);
	
}
