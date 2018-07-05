package cn.fintecher.sms.dao;

import cn.fintecher.sms.entity.SysSmsContentEntity;

/**
 * 
 * 
 * @author integration
 * @email integration@fintecher.cn
 * @date 2017-05-28 00:10:02
 */
public interface SysSmsContentDao extends BaseDao<SysSmsContentEntity> {
	
	SysSmsContentEntity findByNumber(String number);
	
}
