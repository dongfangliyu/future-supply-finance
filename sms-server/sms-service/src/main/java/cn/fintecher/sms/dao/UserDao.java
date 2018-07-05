package cn.fintecher.sms.dao;

import cn.fintecher.sms.entity.UserEntity;

/**
 * 用户
 * 
 */
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
